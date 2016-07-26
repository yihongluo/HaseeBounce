package com.mygdx.game.States;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Player;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.sprites.Donut;
import com.mygdx.game.sprites.donutPool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Created by hihihong on 2016-07-14.
 */
public class PlayState extends State
{
    private Texture bg;
    private Player left;
    private Player right;
    private Array<Donut> activeDonuts;
    private donutPool activeDonutPool;
    private int score;
    private String scoreDisplay;
    private BitmapFont bmFont;
    private boolean ateDonut;
    private String ateDisplay;
    private float displayTimer;
    private float timer;
    private String timerDisplay;
    private String countdownString;
    private float countdownDisplayTimer;
    private boolean isCountingDown;

    final int totalTime = 30;

    public PlayState(GameStateManager gsm)
    {

        super(gsm);
        this.bg = new Texture("bg.png");
        this.left = new Player("leftPlayer.png", true, true);
        this.right = new Player("rightPlayer.png", false, false);
        this.activeDonuts = new Array<Donut>();
        this.activeDonutPool = new donutPool();
        this.score = 0;
        this.scoreDisplay = "Wohoo! You have " + this.score + " points!";
        this.bmFont = new BitmapFont();
        this.ateDonut = false;
        this.displayTimer = 0;
        this.timer = 0;
        this.countdownDisplayTimer = 0;
        this.isCountingDown = false;
        this.timerDisplay = "Time left: " + (totalTime - (int)(this.timer));

        int range = (3-1) + 1;
        int interval = (int)(Math.random() * range) + 1;
        Timer.schedule(new Task() {
            @Override
            public void run() {
                generateDonuts();
            }
        }, interval, 3);


    }

    @Override
    public void handleInput()
    {
        // Move players vertically
        if (Gdx.input.justTouched())
        {
            if ((this.left.getPosition().y == Player.TOP && this.right.getPosition().y == Player.BOTTOM) || (this.right.getPosition().y == Player.TOP && this.left.getPosition().y == Player.BOTTOM))
            {
                this.left.jump();
                this.right.jump();
            }
        }

        // Move players horizontally
        int deltaX = Gdx.input.getDeltaX();
        if (deltaX != 0)
        {
            this.left.moveX(deltaX);
            this.right.moveX(deltaX);
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
        for (Donut donut:activeDonuts)
        {
            // Stop game if time limit is reached
            if (timer >= 30)
            {
                gsm.set(new GameOverState(gsm, score));
            }

            // Move donut
            donut.update(dt);
            // If donut goes out of frame, recycle it
            if (donut.getPosition().x > 480 || donut.getPosition().x + donut.getWidth() < 0)
            {
                activeDonuts.removeValue(donut, true);
                activeDonutPool.free(donut);
            }
            // If donut collides with a player, recycle it
            if (donut.isCollided(this.left.getBond()) != -1 || donut.isCollided(this.right.getBond()) != -1)
            {
                ateDonut = true;
                ateDisplay = "+ " + donut.getScore() + "points";
                score += donut.getScore();
                scoreDisplay = "Wohoo! You got " + this.score + " points!";

                activeDonuts.removeValue(donut, true);
                activeDonutPool.free(donut);
            }
        }

        // Move players
        this.left.update(dt);
        this.right.update(dt);

        // Increment display score timer
        if (displayTimer <= 1)
        {
            displayTimer += Gdx.graphics.getDeltaTime();
        }

        // Increment general timer
        timer += dt;
        timerDisplay = "Time left: " + (totalTime - (int)(this.timer));

        // Check if should be counting down
        if (updateCountdownString())
        {
            isCountingDown = true;
        }

        // Increment counting down timer
        if (countdownDisplayTimer <= 1)
        {
            countdownDisplayTimer += Gdx.graphics.getDeltaTime();
        }

    }

    @Override
    public void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(bg, 0 , 0);

        // Draw players
        sb.draw(left.getAppearance(), left.getPosition().x, left.getPosition().y);
        sb.draw(right.getAppearance(), right.getPosition().x, right.getPosition().y);

        // Draw donuts
        for (Donut donut:activeDonuts)
        {
            sb.draw(donut.getAppearance(), donut.getPosition().x, donut.getPosition().y);
        }

        // Display score text
        bmFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bmFont.draw(sb, scoreDisplay, 25, 750);

        // Display timer
        bmFont.draw(sb, timerDisplay, 220, 750);

        // Display how many points player got for 1 sec
        if (ateDonut && displayTimer <= 1)
        {
            bmFont.draw(sb, ateDisplay, 170, 400);
        }
        else if (displayTimer > 1)
        {
            displayTimer = 0;
            ateDonut = false;
        }

        // Display countdown
        if (isCountingDown && countdownDisplayTimer <= 1)
        {
            bmFont.draw(sb, countdownString, 220, 350);
        }
        else if (countdownDisplayTimer > 1)
        {
            countdownDisplayTimer = 0;
            isCountingDown = false;
        }


        sb.end();
    }

    @Override
    public void dispose()
    {
        bg.dispose();
        left.dispose();
        right.dispose();

        for (Donut donut: activeDonuts)
        {
            donut.dispose();
        }

        bmFont.dispose();
    }

    public void generateDonuts()
    {
        Donut donut = activeDonutPool.obtain();

        int pos = (int)(Math.random() * 2);
        int y = (int)(Math.random() * 300) + 300;

        int speed = (int)(Math.random() * 10) + 2;

        donut.setDonut(speed, y, "donut.png", pos, 10);

        activeDonuts.add(donut);
    }

    public boolean updateCountdownString()
    {
        if (timer == 20)
        {
            countdownString = "10 seconds left!";
            return true;
        }
        else if (timer == 25)
        {
            countdownString = "5 seconds left!";
            return true;
        }
        else if (timer == 27)
        {
            countdownString = "3 ...";
            return true;
        }
        else if (timer == 28)
        {
            countdownString = "2 ...";
            return true;
        }
        else if (timer == 29)
        {
            countdownString = "1 ...";
            return true;
        }
        else if (timer == 30)
        {
            countdownString = "FINISH !";
            return true;
        }
        return false;
    }
}

