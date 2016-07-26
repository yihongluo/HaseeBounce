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

/**
 * Created by hihihong on 2016-07-14.
 */
public class MainMenuState extends State {
    private Texture bg;
    private TextButton startButton;
    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    private Pixmap pixmap;


    public MainMenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        generateButton("Start game", 220, 420);

    }

    @Override
    public void handleInput() {
        if (startButton.isPressed())
        {
            gsm.set(new PlayState(gsm));
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
        stage.draw();
        sb.end();
    }

    @Override
    public void dispose()
    {
        bg.dispose();
        font.dispose();
        skin.dispose();
    }

    private void generateButton(String txt, int x, int y)
    {
        createBasicSkin();

        TextButton button = new TextButton(txt, skin);
        button.setPosition(x, y);

        this.startButton = button;

        stage.addActor(button);
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
