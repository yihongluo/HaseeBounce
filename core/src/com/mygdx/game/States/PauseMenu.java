package com.mygdx.game.States;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by hihihong on 2016-07-26.
 */
public class PauseMenu implements Screen
{
    private Texture bg;
    private TextButton resumeButton;
    private TextButton endButton;
    private TextButton quitButton;
    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    private int score;
    final GSM gsm;
    private Screen playScreen;

    public PauseMenu(GSM gsm, int score, Screen playScreen)
    {
        //super(gsm);
        this.gsm = gsm;
        this.score = score;
        this.playScreen = playScreen;
        this.stage = new Stage();

        this.bg = new Texture("bg.png");

        this.resumeButton = generateButton("Resume", 220, 600);
        this.endButton = generateButton("End Game", 220, 400);
        this.quitButton = generateButton("Quit", 220, 200);

        this.stage.addActor(resumeButton);
        this.stage.addActor(endButton);
        this.stage.addActor(quitButton);
    }

    public void handleInput()
    {
        if (resumeButton.isPressed())
        {
            gsm.setScreen(playScreen);
        }
        if (endButton.isPressed())
        {
            gsm.setScreen(new GameOverState(gsm,score));
        }
        if (quitButton.isPressed())
        {
            Gdx.app.exit();
            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt)
    {
        handleInput();

        gsm.batch.begin();
        gsm.batch.draw(bg, 0, 0);
        gsm.batch.end();
        gsm.batch.begin();
        stage.draw();
        gsm.batch.end();
    }

    @Override
    public void dispose()
    {

    }

    private TextButton generateButton(String txt, int x, int y)
    {
        createBasicSkin();

        TextButton button = new TextButton(txt, skin);
        button.setPosition(x, y);

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
