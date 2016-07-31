package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.States.GSM;
import com.mygdx.game.States.MainMenuState;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HaseeBounce extends ApplicationAdapter {
	GSM gsm;
	//OrthographicCamera camera;
	//Viewport viewport;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Hasee Bounce";
	
	@Override
	public void create () {
		this.gsm = new GSM();
		//camera = new OrthographicCamera();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.setScreen(new MainMenuState(gsm));
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.render();

	}
	
	@Override
	public void dispose () {
	}
}
