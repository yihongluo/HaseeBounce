package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

/**
 * Created by hihihong on 2016-07-14.
 */
public class Player {
    private Texture appearance;
    private Boolean up;
    private Boolean left;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bond;

    final static int realHeight = Gdx.graphics.getHeight();
    final static int realWidth = Gdx.graphics.getWidth();
    final static float virtualHeight = 800f;
    final static float virtualWidth = 480f;
    final static float heightScale = realHeight/virtualHeight;
    final static float widthScale = realWidth/virtualWidth;

    private int gravity = (int)(15 * heightScale);

    public static final int TOP = (int)(680 * heightScale);
    public static final int BOTTOM = (int)(100 * heightScale);
    public static final int LEFT = (int)(80 * widthScale);
    public static final int RIGHT = (int)(400 * widthScale);
    public static final int LEFTLEFTMARGIN = (int)(50 * widthScale);
    public static final int LEFTRIGHTMARGIN = (int)(190 * widthScale);
    public static final int RIGHTLEFTMARGIN = (int)(290 * widthScale);
    public static final int RIGHTRIGHTMARGIN = (int)(430 * widthScale);

    public Player(String filePath, Boolean up, Boolean left)
    {
        this.appearance = new Texture(filePath);
        this.up = up;
        this.left = left;
        this.velocity = new Vector3(0,0,0);

        if (this.up)
        {
            this.position = new Vector3(LEFT, TOP, 0);
            this.gravity = -this.gravity;
        }
        else
        {
            this.position = new Vector3(RIGHT, BOTTOM, 0);
        }

        this.bond = new Rectangle();
    }

    public void update(float dt)
    {
        // If player is initially down and going >= 700
        if (!this.up && this.position.y >= TOP)
        {
            this.position.y = TOP;
            this.velocity.y = 0;
            return;
        }
        // If player is initially up and going <= 100
        else if (this.up && this.position.y <= BOTTOM)
        {
            this.position.y = BOTTOM;
            this.velocity.y = 0;
            return;
        }
        else
        {
            this.velocity.add(0, this.gravity, 0);
            this.velocity.scl(dt);
            this.position.add(0, velocity.y, 0);
            this.velocity.scl(1 / dt);
        }

        // Set the bond where the player is
        this.bond.set((int)(this.position.x), (int)(this.position.y), (int)(this.appearance.getWidth() * widthScale), (int)(this.appearance.getHeight() * heightScale));
    }

    public void jump()
    {
        this.up = !this.up;
        this.gravity = -this.gravity;
    }

    public void moveX(int deltaX)
    {
        if (this.left)
        {
            if (this.getPosition().x >= LEFTLEFTMARGIN && this.getPosition().x <= (LEFTRIGHTMARGIN-this.appearance.getWidth()))
            {
                this.position.x = this.position.x + deltaX;
            }
            else if (this.getPosition().x < LEFTLEFTMARGIN)
            {
                this.position.x = LEFTLEFTMARGIN;
            }
            else
            {
                this.position.x = LEFTRIGHTMARGIN - this.appearance.getWidth();
            }
        }
        else
        {
            if (this.getPosition().x >= RIGHTLEFTMARGIN && this.getPosition().x <= (RIGHTRIGHTMARGIN-this.appearance.getWidth()))
            {
                this.position.x = this.position.x + deltaX;
            }
            else if (this.getPosition().x < RIGHTLEFTMARGIN)
            {
                this.position.x = RIGHTLEFTMARGIN;
            }
            else
            {
                this.position.x = RIGHTRIGHTMARGIN - this.appearance.getWidth();
            }
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBond() {
        return bond;
    }

    public Texture getAppearance() {
        return appearance;
    }

    public void dispose()
    {
        appearance.dispose();
    }
}
