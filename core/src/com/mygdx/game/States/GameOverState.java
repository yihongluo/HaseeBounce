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

    final static int realHeight = Gdx.graphics.getHeight();
    final static int realWidth = Gdx.graphics.getWidth();
    final static float virtualHeight = 800f;
    final static float virtualWidth = 480f;
    final static float heightScale = realHeight/virtualHeight;
    final static float widthScale = realWidth/virtualWidth;

    public GameOverState(GSM gsm, int score)
    {
        //super(gsm);
        this.gsm = gsm;

        stage = new Stage();

        this.bg = new Texture("bg.png");

        this.scoreDisplay = "Total points: " + score;

        this.font = new BitmapFont();
        this.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.font.getData().setScale(widthScale, heightScale);

        this.restartButton = generateButton("Restart", (int)(220 * widthScale), (int)(420 * heightScale));
        this.stage.addActor(restartButton);
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

        font.draw(gsm.batch, scoreDisplay, (int)(100 * widthScale), (int)(750 * heightScale));
        gsm.batch.draw(bg, 0, 0, realWidth, realHeight);
        gsm.batch.end();

        gsm.batch.begin();
        stage.draw();
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
        buttonFont = new BitmapFont();
        skin = new Skin();
        skin.add("default", buttonFont);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
}
