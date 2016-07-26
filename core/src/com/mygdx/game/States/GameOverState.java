package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by hihihong on 2016-07-24.
 */
public class GameOverState extends State {
    private Texture bg;
    private TextButton restartButton;
    private BitmapFont font;
    private BitmapFont buttonFont;
    private Skin skin;
    private Stage stage;
    private Pixmap pixmap;
    private String scoreDisplay;

    public GameOverState(GameStateManager gsm, int score)
    {
        super(gsm);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.bg = new Texture("gameoverBG.png");

        this.scoreDisplay = "Total points: " + score;

        font = new BitmapFont();

        generateButton("Restart", 120, 420);
    }

    @Override
    public void handleInput()
    {
        if (restartButton.isPressed())
        {
            gsm.set(new MainMenuState(gsm));
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(bg, 0, 0);
        sb.end();
        sb.begin();

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, 480, 800);
        stage.getBatch().end();

        stage.draw();
        sb.end();

        sb.begin();

        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(sb, scoreDisplay, 100, 750);

        sb.end();
    }

    public void dispose()
    {}

    private void generateButton(String txt, int x, int y)
    {
        createBasicSkin();

        TextButton button = new TextButton(txt, skin);
        button.setPosition(x, y);

        this.restartButton = button;

        stage.addActor(button);
    }

    private void createBasicSkin(){
        //Create a font
        buttonFont = new BitmapFont();
        skin = new Skin();
        skin.add("default", buttonFont);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
}
