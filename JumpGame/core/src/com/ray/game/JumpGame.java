package com.ray.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * An ApplicationListener that delegates to a Screen. This allows an application to easily have multiple screens.
 * Screens are not disposed automatically. You must handle whether you want to keep screens around or dispose of them when another
 * screen is set.
 **/

public class JumpGame extends Game {

	AndroidInterfaces inter;

	public JumpGame(AndroidInterfaces inter){
		this.inter = inter;
	}

	//Game drawn to this resolution and scaled up to user's device
	public static final int WIDTH = 300;
	public static final int HEIGHT = 480;
	public static final int GROUND_LEVEL = 19;
    public static final int CENTER_X = WIDTH/2;
    public static final int CENTER_Y = HEIGHT/2;

	//When we create our game, we load our assets and also our preferences(score)
	//The screen is set to start on our MainMenu
	@Override
	public void create () {
		Assets.load();
		SavedDataManager.getInstance().load();
		setScreen(new MainMenuScreen(this));
		inter.toast("Welcome to the game!"); //Welcome message toast
	}

	//When activity is paused we just save our preferences
	@Override
	public void pause() {
		super.pause();
		SavedDataManager.getInstance().save();
	}

	//Dispose our assets when game activity is disposed
	@Override
	public void dispose () {
		Assets.dispose();
	}
}
