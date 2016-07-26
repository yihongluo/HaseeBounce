package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

/**
 * Created by hihihong on 2016-07-14.
 */
public class GameStateManager {
    private Stack<State> states;

    public GameStateManager()
    {
        states = new Stack<State>();
    }

    public void pop()
    {
        states.pop();
    }

    public void push(State state)
    {
        states.push(state);
    }

    public void set(State state)
    {
        states.pop();
        states.push(state);
    }

    public void update(float dt)
    {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }
}
