package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.HaseeBounce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = HaseeBounce.HEIGHT;
		config.width = HaseeBounce.WIDTH;
		config.title = HaseeBounce.TITLE;
		new LwjglApplication(new HaseeBounce(), config);
	}
}
