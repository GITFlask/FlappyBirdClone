package com.ray.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ray.game.JumpGame;
/**
 * Created by Ray on 15/12/2016.
 */

/*This is our class for our game play screen. This class has extended libgdx's screen adapter class which implements libgdx's screen interface
* This screen interface represents one of many application screens, such as a main menu, a settings menu, the game screen and so on.
* The screen adapter makes it easy for us to create many screens*/

public class gamePlayScreen extends ScreenAdapter {

    //Variables for our tube objects.
    public static final float TUBE_SPACING = 200f; //Set how far apart the tubes are from one another.
    public static final int TUBE_SETS = 3; //Set the amount sets of tubes in game.

    //This is for our application listener
    protected JumpGame game;

    //The orthographic camera allows for orthographic projections
    protected OrthographicCamera camera;

    //The stage classes implements an input adapter and allows our application to read inputs (ie button presses)
    //gamePlayStage is for when the game starts
    private Stage gamePlayStage;
    //The uiStage is for our labels, score, tap to retry, tap to flap, and highscore.
    private Stage uiStage;

    //These are the labels we have used in game and they are considered as actors which we can set messages and manipulate(move)
    private Label scoreLabel;
    private Label tapRetry;
    private Label best;
    private Label tapToFlap;

    //This integer keeps our score
    public int score;

    //We call our jumper class to make an object(aka our plane)
    private Jumper jumper;

    //We store our tubes in an array
    private Array<tubePair> tubePairs;

    private Image background;

    //We make our ground images for the game
    private Ground ground1;
    private Ground ground2;
    private groundPair GroundPair;

    //Boolean value for when our screen in pressed
    private boolean justTouched;

    //We set our beginning state when we get to the game play screen as pregame
    private State screenState = State.PREGAME;

    //We don't allow restart for before we start our game
    private boolean allowRestart = false;

    //Our state enumerators contain the different states for our switch condition below
    private enum State { PREGAME, PLAYING, DYING, DEAD };

    //Our gamePlayScreen Object takes a game parameter
    public gamePlayScreen(JumpGame game) {
        this.game = game;

        //We set the camera to the size of our game
        camera = new OrthographicCamera(JumpGame.WIDTH, JumpGame.HEIGHT);

        //The stage are stretched to a stretched viewport, this allows our in game images to stretch along with the screen of the device
        gamePlayStage = new Stage(new StretchViewport(JumpGame.WIDTH, JumpGame.HEIGHT, camera));
        uiStage = new Stage(new StretchViewport(JumpGame.WIDTH, JumpGame.HEIGHT));

        //Our character position is set here, during the pregame it will float around as action defined in Utils.java
        jumper = new Jumper();
        jumper.setPosition(JumpGame.WIDTH * .25f, JumpGame.HEIGHT /2, Align.center);
        jumper.addAction(Utils.getFloatyAction());
        //Initial state
        jumper.setState(Jumper.State.PREGAME);

        //The score label is defined here are added to our uiStage Screen
        scoreLabel = new Label("Score: 0", new Label.LabelStyle(Assets.font, Color.WHITE));
        scoreLabel.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .9f, Align.center);
        uiStage.addActor(scoreLabel);

        //The tap to retry label is defined here, set position and set to our ui screen.
        tapRetry = new Label("Tap to Retry!", new Label.LabelStyle(Assets.font, Color.WHITE));
        tapRetry.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .6f, Align.center);
        uiStage.addActor(tapRetry);

        //The label for best is set here
        best = new Label("Best: ", new Label.LabelStyle(Assets.font, Color.WHITE));
        best.setPosition(JumpGame.WIDTH/2, 0, Align.top);
        uiStage.addActor(best);

        tapToFlap = new Label("Tap to Start!", new Label.LabelStyle(Assets.font, Color.WHITE));
        tapToFlap.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT, Align.bottom);
        uiStage.addActor(tapToFlap);

        //Our tubes are in an array of tubepairs
        tubePairs = new Array<tubePair>();

        background = new Image(Assets.background);

        //Our grounds are defined here and these objects are parameters for our groundpair class which updates the positions for then they move off the left of screen
        ground1 = new Ground();
        ground2 = new Ground();
        ground2.setPosition(JumpGame.WIDTH, 0);
        GroundPair = new groundPair(ground1, ground2);

        //The order the actors are added determines the order they are drawn so we need to make sure that the background is first.
        gamePlayStage.addActor(background);
        gamePlayStage.addActor(jumper);
        addPairsToStage(gamePlayStage);
        gamePlayStage.addActor(ground1);
        gamePlayStage.addActor(ground2);

        //Setup the input processor.
        initInputProcessor();
    }

    /*The for loop in this methods adds our tubes and coin objects to TubePair array*/
    private void addPairsToStage(Stage gamePlayStage) {
        for(int i = 0; i < tubePairs.size; i++){

            tubePair pair = tubePairs.get(i);
            gamePlayStage.addActor(pair.getBotTube());
            gamePlayStage.addActor(pair.getTopTube());
            gamePlayStage.addActor(pair.getCoin());
        }
    }

    //These method initialised our 3 sets of tubes
    private void  initFirstSetOfPipes(){
        Tubes toptube = new Tubes();
        Tubes bottube = new Tubes();
        //The top tube is fliped 180 degrees to turn upside down
        toptube.setRotation(180f);
        tubePair pair = new tubePair(toptube, bottube);
        pair.initFirst();

        //Adds pair to list, the tubepairs.java contains the gapsize between our obstacles to makesure they are not drawn overlapped
        tubePairs.add(pair);
    }

    private void  initSecondSetOfPipes(){
        Tubes toptube = new Tubes();
        Tubes bottube = new Tubes();
        toptube.setRotation(180f);
        tubePair pair = new tubePair(toptube, bottube);
        pair.initSecond();

        //Adds pair to list
        tubePairs.add(pair);
    }

    private void  initThirdSetOfPipes(){
        Tubes toptube = new Tubes();
        Tubes bottube = new Tubes();
        toptube.setRotation(180f);
        tubePair pair = new tubePair(toptube, bottube);
        pair.initThird();

        //Adds pair to list
        tubePairs.add(pair);
    }

    /*Tells libgdx to listen for inputs coming from the InputAdaptor we give it*/
    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            //We only need care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                switch(screenState){

                    case DYING:
                        justTouched = true;
                        break;

                    case DEAD:
                        if(allowRestart){
                            game.setScreen(new gamePlayScreen(game)); //Tap to retry, resets the screen to gamePlayScreen again
                        }
                        justTouched = true;
                        break;

                    case PLAYING:
                        justTouched = true;
                        break;

                    case PREGAME:
                        justTouched = true;
                        screenState = State.PLAYING; //Set screen to play
                        jumper.setState(Jumper.State.ALIVE); //Set jumper to alive
                        jumper.clearActions(); //Clear previous actions
                        tapToFlap.addAction(Actions.moveToAligned(JumpGame.CENTER_X, JumpGame.HEIGHT + 100, Align.bottom, .75f, Interpolation.sine)); //Move label out
                        initFirstSetOfPipes(); //Start the tubes up
                        initSecondSetOfPipes();
                        initThirdSetOfPipes();
                        addPairsToStage(gamePlayStage); //Add to our stage
                        gamePlayStage.addActor(ground1);
                        gamePlayStage.addActor(ground2);
                        gamePlayStage.addActor(jumper);

                }
                return true;
            }
        });
    }

    //The render method draws out our images and makes them act (see updateAndDrawStages
    @Override
    public void render(float delta) {

        //Clear for our GL to prevent errors
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch(screenState){
            case PREGAME:
                updateAndDrawStages(); //Update and draw stage in pregame
                GroundPair.update(); //The ground is moving
                break;
            case PLAYING:
                renderPlaying(); //Start up the tubes
                break;
            case DYING:
            case DEAD:
                renderDeadOrDying(); //Stop and kill the jumper
                break;
        }
    }

    //Our render method for when we are in state of Dead or Dying.
    private void renderDeadOrDying(){
        if(jumper.getState() == Jumper.State.DEAD){
            screenState = State.DEAD;
        }
        updateAndDrawStages();
    }

    private void renderPlaying() {
        if (justTouched) {
            jumper.jump();

            //Set back to false after we press it so we can press again
            justTouched = false;
        }
        GroundPair.update(); //Update ground
        updateTubePairs(); //Update Tubes and coin
        gamePlayStage.act(); //Act out the stages
        uiStage.act();
        checkCollision(); //Watch out for collisions
        if (jumper.getState() == Jumper.State.DYING) {
            stopTheWorld();

            //Action sequence for retry label when it comes to view
            SequenceAction actions = Actions.sequence(Actions.delay(1f), Actions.moveToAligned(JumpGame.CENTER_X, JumpGame.CENTER_Y, Align.bottom,
                    .75f, Interpolation.sine), Actions.run(new Runnable() {
                @Override
                public void run() {

                    //Allow player to restart the game once the tap to retry finishes coming up
                    allowRestart = true;
                }
            }));

            tapRetry.addAction(actions);

            //Our highscore label comes out when we die
            best.setText("Best: " + SavedDataManager.getInstance().getHighScore());
            best.setWidth(best.getWidth());
            best.setPosition(JumpGame.CENTER_X, 0, Align.top);
            best.addAction(Actions.delay(1f, Actions.moveToAligned(JumpGame.CENTER_X, JumpGame.CENTER_Y, Align.top,
                    .75f, Interpolation.sine)));
            screenState = State.DYING;
        }
        gamePlayStage.draw();
        uiStage.draw();
    }

    //our draw and act methods are put here to be called.
    private void updateAndDrawStages(){
        gamePlayStage.act();
        gamePlayStage.draw();
        uiStage.act();
        uiStage.draw();
    }

    //Our method to check collision. Conditions for when our plane bounds overlaps with our obstacle, coin, hits the ground or hits the ceiling.
    private void checkCollision() {
        for(int i = 0; i < tubePairs.size; i++){
            tubePair pair = tubePairs.get(i);
            if(pair.getBotTube().getBounds().overlaps(jumper.getBounds()) || pair.getTopTube().getBounds().overlaps(jumper.getBounds())){
                stopTheWorld();
                SavedDataManager.getInstance().setHighScore(score); //If we scored a higher score than our previous best when we save it to our preferences
        }
            else if(jumper.isBelowGround()){
                jumper.setY(JumpGame.GROUND_LEVEL); //Don't let jumper go below ground
                jumper.clearActions();
                jumper.die();
            }
            else if(jumper.isAboveCeiling()){
                jumper.setY(JumpGame.HEIGHT - jumper.getHeight()); //If jumper hits the ceiling then set to die().
                jumper.die();
            }
            else if(pair.getCoin().getBounds().overlaps(jumper.getBounds())){
                score++;
                updateScoreLabel(); //We increment our score integer and Update the label to show it on screen.
                pair.moveCoinOffScreen(); //After we score a point, we move our coin way off screen so our camera can't see it
                Assets.playPointSound(); //We play a sound
            }
        }
    }

    //This method sets our score label
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + String.valueOf(score));
        scoreLabel.setWidth(scoreLabel.getWidth());
        scoreLabel.setPosition(JumpGame.WIDTH/2, JumpGame.HEIGHT * .9f, Align.center);
        //Set Label according to width
    }

    //When we die we want the tubes and ground to stop moving, we also change our state case to dying.
    private void stopTheWorld() {
        jumper.die();
        killTubePairs();
        stopTheGround();
        screenState = State.DYING;
    }

    private void stopTheGround() {
        GroundPair.stop();
    }

    //Stops all our tubes and coins for when we die.
    private void killTubePairs() {
        for(tubePair pair : tubePairs){
            pair.getBotTube().setState(Tubes.State.DEAD);
            pair.getTopTube().setState(Tubes.State.DEAD);
            pair.getCoin().setVel(0, 0);
        }
    }


    private void updateTubePairs() { //Update the tube array
        for(int i= 0; i < tubePairs.size; i++){
            tubePairs.get(i).update();
        }
    }

    //We set the size of our camera, unload our SpriteBatch and add it to our camera projection matrix to draw on screen.
    //These are resized for different screen sizes
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        gamePlayStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }

    //We dispose our stages when we are done with them or if we need to redraw them when the game screen reloads
    @Override
    public void dispose() {
        gamePlayStage.dispose();
        uiStage.dispose();
    }

    //These actions are shown on the Screen, these actions relates to the labels on uiStage
    public void show(){
        tapRetry.addAction(Actions.moveBy(-300f, 0));
        tapRetry.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 20f, .7f), Actions.moveBy(0, -20f, .7f))));
        tapToFlap.addAction(Actions.moveToAligned(JumpGame.CENTER_X, JumpGame.CENTER_Y - 100f, Align.center, .75f, Interpolation.sine));
        tapToFlap.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, -40f, .7f), Actions.moveBy(0, 40f, .7f))));
    }


}

