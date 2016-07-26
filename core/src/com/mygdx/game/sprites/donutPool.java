package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.sprites.Donut;
/**
 * Created by hihihong on 2016-07-19.
 */
public class donutPool extends Pool<Donut>
{
    public donutPool()
    {
        super();
    }

    protected Donut newObject()
    {
        return new Donut();
    }
}
