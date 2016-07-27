package com.mygdx.game.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by hihihong on 2016-07-26.
 */
public class GSM extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public GSM()
    {
        this.batch = new SpriteBatch();
    }

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        //this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
