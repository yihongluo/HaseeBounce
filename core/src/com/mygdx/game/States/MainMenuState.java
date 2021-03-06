package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by hihihong on 2016-07-14.
 */
public class MainMenuState implements Screen {
    private Texture bg;
    private TextButton startButton;
    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    private Pixmap pixmap;
    final GSM gsm;

    final static int realHeight = Gdx.graphics.getHeight();
    final static int realWidth = Gdx.graphics.getWidth();
    final static float virtualHeight = 800f;
    final static float virtualWidth = 480f;
    final static float heightScale = realHeight/virtualHeight;
    final static float widthScale = realWidth/virtualWidth;

    public MainMenuState(GSM gsm) {
        this.gsm = gsm;
        this.bg = new Texture("bg.png");

        this.stage = new Stage();

        System.out.println(widthScale);
        System.out.println(heightScale);
        System.out.println(realHeight);
        this.startButton = generateButton("Start game", (int)(220 * widthScale), (int)(420 * heightScale));
        stage.addActor(startButton);

    }

    public void handleInput() {
        if (startButton.isPressed())
        {
            Gdx.input.setInputProcessor(null);
            gsm.setScreen(new PlayState(gsm));
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
    public void render(float dt)
    {
        handleInput();

        //initViewport(800, 480, 4.0f/3.0f);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.batch.begin();
        gsm.batch.draw(bg, 0, 0, realWidth, realHeight);
        gsm.batch.end();
        gsm.batch.begin();
        //stage.getViewport().apply();
        stage.draw();
        gsm.batch.end();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose()
    {
        bg.dispose();
        font.dispose();
        skin.dispose();
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
