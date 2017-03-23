package com.ray.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ray.game.AndroidInterfaces;
import com.ray.game.JumpGame;

public class DesktopLauncher implements AndroidInterfaces {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = JumpGame.WIDTH;
		config.height = JumpGame.HEIGHT;

		new LwjglApplication(new JumpGame(new AndroidInterfaces() {
			@Override
			public void toast(String t) {
				Gdx.app.log("Toast Console", t);
			}
		}), config);
	}

	@Override
	public void toast(String t) {
		Gdx.app.log("Desktop", t);
	}
}
