package com.mygdx.game.States;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.sprites.Player;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.sprites.Donut;
import com.mygdx.game.sprites.donutPool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Screen;


/**
 * Created by hihihong on 2016-07-14.
 */
public class PlayState implements Screen
{
    private Texture bg;
    private Player left;
    private Player right;
    private Array<Donut> activeDonuts;
    private donutPool activeDonutPool;
    private int score;
    private String scoreDisplay;
    private BitmapFont bmFont;
    private BitmapFont font;
    private Skin skin;
    private boolean ateDonut;
    private String ateDisplay;
    private float displayTimer;
    private float timer;
    private String timerDisplay;
    private String countdownString;
    private float countdownDisplayTimer;
    private boolean isCountingDown;
    private TextButton pauseButton;
    private Stage stage;
    final GSM gsm;

    final int totalTime = 30;

    final int realHeight = Gdx.graphics.getHeight();
    final int realWidth = Gdx.graphics.getWidth();
    final float virtualHeight = 800f;
    final float virtualWidth = 480f;
    final float heightScale = realHeight/virtualHeight;
    final float widthScale = realWidth/virtualWidth;

    public PlayState(GSM gsm)
    {
        this.gsm = gsm;
        this.bmFont = new BitmapFont();
        this.bmFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.bmFont.getData().setScale(widthScale, heightScale);

        this.bg = new Texture("bg.png");

        this.left = new Player("leftPlayer.png", true, true);
        this.right = new Player("rightPlayer.png", false, false);

        this.activeDonuts = new Array<Donut>();
        this.activeDonutPool = new donutPool();

        this.score = 0;
        this.scoreDisplay = "Wohoo! You have " + this.score + " points!";

        this.ateDonut = false;
        this.displayTimer = 0;

        this.timer = 0;
        this.timerDisplay = "Time left: " + (totalTime - (int)(this.timer));

        this.countdownDisplayTimer = 0;
        this.isCountingDown = false;

        this.pauseButton = generateButton("Pause", (int)(380 * widthScale), (int)(700 * heightScale));
        pauseButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            };
        });

        this.stage = new Stage();

        stage.addActor(pauseButton);

        int normalRange = 3;
        int normalInterval = (int)(Math.random() * normalRange) + 1;

        // Schedule a lot of peasant class donuts
        Timer.schedule(new Task() {
            @Override
            public void run() {
                generateDonuts();
            }
        }, normalInterval, 3);

        normalInterval = (int)(Math.random() * normalRange) + 1;

        Timer.schedule(new Task() {
            @Override
            public void run() {
                generateDonuts();
            }
        }, normalInterval, 3);
    }

    public void handleInput()
    {
        // Pause button
        if (pauseButton.isPressed())
        {
            hide();
            gsm.setScreen(new PauseMenu(gsm, score, this));
        }

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

    public void update(float dt)
    {
        handleInput();
        for (Donut donut:activeDonuts)
        {
            // Stop game if time limit is reached
            if (timer >= 30)
            {
                gsm.setScreen(new GameOverState(gsm, score));
            }

            // Move donut
            donut.update(dt);

            // If donut goes out of frame, recycle it
            if (donut.getPosition().x > (int)(480 * widthScale) || donut.getPosition().x + donut.getWidth() < 0)
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

        // Increment general timer
        timer += dt;
        timerDisplay = "Time left: " + (totalTime - (int)(this.timer));

        // Increment display score timer
        if (displayTimer <= 1)
        {
            displayTimer += Gdx.graphics.getDeltaTime();
        }

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
    public void render(float dt)
    {
        update(dt);
        //initViewport(800, 480, 4.0f/3.0f);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.batch.begin();
        gsm.batch.draw(bg, 0 , 0, realWidth, realHeight);

        // Draw players
        gsm.batch.draw(left.getAppearance(), left.getPosition().x, left.getPosition().y, (int) (left.getAppearance().getWidth() * widthScale), (int)(left.getAppearance().getHeight() * heightScale));
        gsm.batch.draw(right.getAppearance(), right.getPosition().x, right.getPosition().y, (int) (right.getAppearance().getWidth() * widthScale), (int)(right.getAppearance().getHeight() * heightScale));

        // Draw donuts
        for (Donut donut:activeDonuts)
        {
            gsm.batch.draw(donut.getAppearance(), donut.getPosition().x, donut.getPosition().y, (int)(donut.getAppearance().getWidth() * widthScale), (int)(donut.getAppearance().getHeight() * heightScale));
        }

        // Display score text
        bmFont.draw(gsm.batch, scoreDisplay, (int)(25 * widthScale), (int)(750 * heightScale));

        // Display timer
        bmFont.draw(gsm.batch, timerDisplay, (int)(220 * widthScale), (int)(750 * heightScale));

        // Display how many points player got for 1 sec
        if (ateDonut && displayTimer <= 1)
        {
            bmFont.draw(gsm.batch, ateDisplay, (int)(170 * widthScale), (int)(400 * heightScale));
        }
        else if (displayTimer > 1)
        {
            displayTimer = 0;
            ateDonut = false;
        }

        // Display countdown
        if (isCountingDown && countdownDisplayTimer <= 1)
        {
            bmFont.draw(gsm.batch, countdownString, (int)(220 * widthScale), (int)(350 * heightScale));
        }
        else if (countdownDisplayTimer > 1)
        {
            countdownDisplayTimer = 0;
            isCountingDown = false;
        }

        gsm.batch.end();
        gsm.batch.begin();
        stage.draw();
        gsm.batch.end();
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

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public void generateDonuts()
    {
        Donut donut = activeDonutPool.obtain();

        int pos = (int)(Math.random() * 2);
        int y = (int)(Math.random() * (int)(300 * widthScale)) + (int)(300 * widthScale);

        int speed = (int)(Math.random() * (int)(10 * widthScale)) + (int)(2 * widthScale);

        int[] probability = {1,1,1,1,1,1,2,2,2,5,5,10};

        int odds = (int)(Math.random() * probability.length);

        int score = probability[odds];
        String donutImage;

        switch(score) {
            case 1:
                donutImage = "1donut.png";
                break;
            case 2:
                donutImage = "2donut.png";
                break;
            case 5:
                donutImage = "5donut.png";
                break;
            case 10:
                donutImage = "10donut.png";
                break;
            default:
                donutImage = "1donut.png";
                break;
        }
        donut.setDonut(speed, y, donutImage, pos, score);

        activeDonuts.add(donut);
    }

    public boolean updateCountdownString()
    {
        if ((int)timer == 20)
        {
            countdownString = "10 seconds left!";
            return true;
        }
        else if ((int)timer == 25)
        {
            countdownString = "5 seconds left!";
            return true;
        }
        else if ((int)timer == 27)
        {
            countdownString = "3 ...";
            return true;
        }
        else if ((int)timer == 28)
        {
            countdownString = "2 ...";
            return true;
        }
        else if ((int)timer == 29)
        {
            countdownString = "1 ...";
            return true;
        }
        else if ((int)timer == 30)
        {
            countdownString = "FINISH !";
            return true;
        }
        return false;
    }

    private TextButton generateButton(String txt, int x, int y)
    {
        createBasicSkin();

        TextButton button = new TextButton(txt, skin);
        button.setPosition(x, y);
        button.setTransform(true);
        button.scaleBy(widthScale, heightScale);

        return button;
    }

    private void createBasicSkin(){
        //Create a font
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
}

