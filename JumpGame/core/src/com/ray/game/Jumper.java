package com.ray.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Ray on 15/12/2016.
 */

/*This class defines our character*/

public class Jumper extends Actor {

    //Width and Height of game charactor set to 32 pixels wide and tall
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    //These units are measured in pixels per second
    public static final float GRAVITY = 780f;
    public static final float JUMP_VELOCITY = 310f;

    //Actor class keeps track of position so we just keep track of velocity and acceleration
    private Vector2 vel;
    private Vector2 accel;

    //The texture used
    private TextureRegion region;
    //We need a float time for the update methods
    private float time;

    private State state;

    private Rectangle bounds; //Set around our jumper for collision detection.

    public enum State {PREGAME, ALIVE, DYING, DEAD} //The states which our jumpers can be in

    public Jumper() {
        region = new TextureRegion(Assets.jumper1);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.ALIVE;
        vel = new Vector2(0, 0);
        accel = new Vector2(0, -GRAVITY);
        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);

        //An actor's origin defines the center for rotation.
        setOrigin(Align.center);
    }

    public void jump(){
        vel.y = JUMP_VELOCITY; //Jumper boosts upwards

        clearActions(); //Clears all previous actions

        RotateToAction a1 = Actions.rotateTo(10, .1f); //Tilt slightly up
        RotateToAction a2 = Actions.rotateTo(-10, 1f); //Tilt slightly down

        SequenceAction sequenceAction = Actions.sequence(a1, a2); //Together the actions make the jump look like an arc

        addAction(sequenceAction);  //Play action
        Assets.playFlapSound(); //Play sound effect
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta; //The time is delta over time

        switch(state){
            case PREGAME:
                //During pregame state, the jumper will remain in starting position until player begins to play.
                region = Assets.jumperAnimation.getKeyFrame(time);
                break;
            case ALIVE:
                actAlive(delta);
                break;
            case DEAD:
            case DYING:
                actDying();
                break;
        }
        updateBounds();
    }

    //Method for when in alive state
    private void actAlive(float delta) {
        region = Assets.jumperAnimation.getKeyFrame(time);
        applyAccel(delta);
        updatePosition(delta);

        if(isBelowGround()){ //If jumper hits the ground then it dies
            setY(JumpGame.GROUND_LEVEL);
            clearActions();
            die();
        }
        if(isAboveCeiling()){ //If jumper hits the ceiling of our screen it dies. Moved down slightly to accommodate for bigger POW texture
            setPosition(getX(), JumpGame.HEIGHT - 32f, Align.topLeft);
            die();
        }
    }

    //Method for when it dies
    private void actDying(){
        vel.x = 0;
        vel.y = 0;
        setState(State.DEAD);

        if(isBelowGround()){
            setY(JumpGame.GROUND_LEVEL);
            setState(State.DEAD);
        }
    }

    private void updateBounds() { //Update bounds with the position of our jumper
        bounds.x = getX();
        bounds.y = getY();
    }



    public boolean isBelowGround(){
        return(getY(Align.bottom) <= JumpGame.GROUND_LEVEL);
    }

    public boolean isAboveCeiling(){
        return(getY(Align.top) >= JumpGame.HEIGHT);
    }

    private void updatePosition(float delta) {
        setX(getX() + vel.x * delta);
        setY(getY() + vel.y * delta);
    }

    private void applyAccel(float delta) {
        vel.add(accel.x * delta, accel.y * delta);
    } //Acceleration increases over time

    @Override
    public void draw(Batch batch, float parentAlpha) {
       batch.setColor(Color.WHITE);
        switch(state){
            case ALIVE:
            case PREGAME:
                drawAlive(batch); //Draw our bird animation
                break;
            case DEAD:
            case DYING:
                drawDead(batch); //Draws the POW expression
                break;
        }
    }

    private void drawAlive(Batch batch){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    private void drawDead(Batch batch){
        batch.draw(Assets.crash, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX() + 3, getScaleY() + 3, 0);
    }

    public void die(){
        //The crash sound is delayed a little bit (sound travels slower than light in reality I suppose)
        addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                Assets.playCrashSound();
            }
        }));
        state = State.DYING;
        vel.y = 0;

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
