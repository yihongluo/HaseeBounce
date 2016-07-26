package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MainMenuState;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class HaseeBounce extends ApplicationAdapter {
	SpriteBatch batch;
	GameStateManager gsm;
	OrthographicCamera camera;
	Viewport viewport;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Hasee Bounce";
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		camera = new OrthographicCamera();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MainMenuState(gsm));

//		float aspectRatio = (float) Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
//		viewport = new FitViewport(WIDTH * aspectRatio, HEIGHT, camera);
//		viewport.apply();
//		camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);

	}
//
//	public void resize (int width, int height)
//	{
//		viewport.update(width, height);
//		camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2, 0);
//	}

	@Override
	public void render () {
//		camera.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());

//		batch.setProjectionMatrix(camera.combined);

		gsm.render(batch);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
