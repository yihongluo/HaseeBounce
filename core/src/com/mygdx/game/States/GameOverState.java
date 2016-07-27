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
import com.badlogic.gdx.Screen;

/**
 * Created by hihihong on 2016-07-24.
 */
public class GameOverState implements Screen{
    private Texture bg;
    private TextButton restartButton;
    private BitmapFont font;
    private BitmapFont buttonFont;
    private Skin skin;
    private Stage stage;
    private Pixmap pixmap;
    private String scoreDisplay;
    final GSM gsm;

    public GameOverState(GSM gsm, int score)
    {
        //super(gsm);
        this.gsm = gsm;

        stage = new Stage();

        this.bg = new Texture("gameoverBG.png");

        this.scoreDisplay = "Total points: " + score;

        font = new BitmapFont();

        generateButton("Restart", 120, 420);
    }

    public void handleInput()
    {
        if (restartButton.isPressed())
        {
            Gdx.input.setInputProcessor(null);
            gsm.setScreen(new MainMenuState(gsm));
        }
    }

    @Override
    public void render(float dt)
    {
        handleInput();

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.batch.begin();
        gsm.batch.draw(bg, 0, 0);
        gsm.batch.end();
        gsm.batch.begin();

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, 480, 800);
        stage.getBatch().end();

        stage.draw();
        gsm.batch.end();

        gsm.batch.begin();

        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(gsm.batch, scoreDisplay, 100, 750);

        gsm.batch.end();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

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
