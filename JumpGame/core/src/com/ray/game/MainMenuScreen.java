package com.ray.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Ray on 21/12/2016.
 */

/*This screen is what the user will see when the app opens up*/

public class MainMenuScreen extends ScreenAdapter {

    private Stage stage;
    private JumpGame game;

    private Image title;
    private Image background;
    private Button playButton;
    private Button resetButton;
    private Button scoreButton;
    private Image logo;

    private AndroidInterfaces inter;

    public MainMenuScreen(JumpGame game){
        this.game = game;

        /*Set the stage for our actors*/
        stage = new Stage(new StretchViewport(JumpGame.WIDTH, JumpGame.HEIGHT));

        /*Our JumpGame title*/
        title = new Image(Assets.title);
        title.setSize(JumpGame.WIDTH, JumpGame.HEIGHT *.2f);
        title.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .8f, Align.center);
        title.setOrigin(Align.center);
        title.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 40f, .7f), Actions.moveBy(0, -40f, .7f))));

        /*The background is a coloured image*/
        background = new Image(Assets.mainBg);

        /*EraticNinja logo set to the bottom left of the screen*/
        logo = new Image(Assets.logo);
        logo.setSize(100, 100);
        logo.setPosition(0, 0, Align.bottomLeft);

        /*Load up the 3 buttons used*/
        initPlayButton();
        initScoreButton();
        initResetButton();

        /*Add the actors to the stage*/
        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(playButton);
        stage.addActor(scoreButton);
        stage.addActor(resetButton);
        stage.addActor(logo);

        /*Set the input processors to take inputs for this stage*/
        Gdx.input.setInputProcessor(stage);

        /*Play the background music*/
        Assets.playThemeMusic();
    }

    private void initPlayButton() {
        playButton = new Button(new TextureRegionDrawable(Assets.buttonUp), new TextureRegionDrawable(Assets.buttonDown)); //Two textures used for this button for when it is pressed vs not pressed
        playButton.setSize(100f, 40f); //Set button size
        playButton.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .6f, Align.center); //Set button position
        playButton.addListener(new ClickListener(){


            @Override
            public void clicked(InputEvent event, float x, float y) { //OnClickListener for when the button is touched
                game.setScreen(new gamePlayScreen(game)); //Change the game screen of this game to gamePlayScreen
                Assets.playPressSound(); //Sound for when pressed
            }
        });
    }

    private void initScoreButton() {
        scoreButton = new Button(new TextureRegionDrawable(Assets.highScore), new TextureRegionDrawable(Assets.highScore2));
        scoreButton.setSize(100f, 40f);
        scoreButton.setPosition(JumpGame.WIDTH / 2, JumpGame.HEIGHT * .5f, Align.center);
        scoreButton.addListener(new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.playPressSound();
                game.inter.toast("Current score is: " + SavedDataManager.getInstance().getHighScore()); //Start a toast to show highscore from preferences
            }
        });
    }

    private void initResetButton() {
        resetButton = new Button(new TextureRegionDrawable(Assets.resetScore), new TextureRegionDrawable(Assets.resetScore2));
        resetButton.setSize(100f, 40f);
        resetButton.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .4f, Align.center);
        resetButton.addListener(new ClickListener(){


            @Override
            public void clicked(InputEvent event, float x, float y) {
                SavedDataManager.getInstance().resetHighscore(); //Reset highscore
                Assets.playPressSound();
                game.inter.toast("Score has been reset");   //Toast
            }
        });
    }

    @Override
    public void render(float delta) {
        //Clear the GL to prevent errors
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Render the stage
        stage.act();
        stage.draw();
    }

    @Override
    public void show() { //Show the buttons
        playButton.clearActions();
        playButton.addAction(Actions.sequence(Actions.moveBy(0, -300f),Actions.moveBy(0, 350f, .7f), Actions.moveBy(0, -50f, .1f)));

        resetButton.clearActions();
        resetButton.addAction(Actions.sequence(Actions.moveBy(0, -300f),Actions.moveBy(0, 350f, .7f), Actions.moveBy(0, -50f, .1f)));

        scoreButton.clearActions();
        scoreButton.addAction(Actions.sequence(Actions.moveBy(0, -300f),Actions.moveBy(0, 350f, .7f), Actions.moveBy(0, -50f, .1f)));
    }

    @Override
    public void dispose() {
        stage.dispose();
    } //Dispose this stage when app finishes
}
