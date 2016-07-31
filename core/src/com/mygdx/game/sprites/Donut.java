package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by hihihong on 2016-07-19.
 */
public class Donut
{
    private int speed;
    private Vector3 position;
    private Texture appearance;
    private int left;
    private Rectangle bond;
    private int score;

    final static int realHeight = Gdx.graphics.getHeight();
    final static int realWidth = Gdx.graphics.getWidth();
    final static float virtualHeight = 800f;
    final static float virtualWidth = 480f;
    final static float heightScale = realHeight/virtualHeight;
    final static float widthScale = realWidth/virtualWidth;

    public Donut()
    {
        this.bond = new Rectangle();
    }


    public void update(float dt)
    {
        if (this.left == 1)
        {
            this.position.x = this.position.x + this.speed;
        }
        else
        {
            this.position.x = this.position.x - speed;
        }

        this.bond.set((int)(this.position.x), (int)(this.position.y), (int)(this.appearance.getWidth() * widthScale), (int)(this.appearance.getHeight() * heightScale));
    }

    public void setDonut(int speed, int y, String path, int left, int score)
    {
        this.speed = speed;
        this.left = left;

        if (this.left == 1)
        {
            this.position = new Vector3(0, y, 0);
        }
        else
        {
            this.position = new Vector3((int)(480 * widthScale), y, 0);
        }

        this.appearance = new Texture(path);
        this.bond.set(this.position.x, this.position.y, this.appearance.getWidth(), this.appearance.getHeight());
        this.score = score;
    }

    public int isCollided (Rectangle player)
    {
        if (this.bond.overlaps(player))
        {
            return this.score;
        }
        else
        {
            return -1;
        }
    }



    public Rectangle getBond() { return bond; }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getAppearance() {
        return appearance;
    }

    public int getScore() { return score; }

    public int getWidth()
    {
        return appearance.getWidth();
    }

    public void dispose()
    {
        appearance.dispose();
    }
}
