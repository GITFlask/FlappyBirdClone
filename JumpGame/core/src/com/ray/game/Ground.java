package com.ray.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Ray on 16/12/2016.
 */

public class Ground extends Actor {

    public static final int WIDTH = 300; //pixels
    public static final int HEIGHT = 150;
    public static final float MOVE_VELOCITY = 120f; //pixels per second

    //Actor keeps track of position so we just need to keep track  of velocity and acceleration
    public Vector2 vel;

    private TextureRegion region;

    private State state;

    public enum State {ALIVE, DEAD}

    public Ground() {
        region = new TextureRegion(Assets.ground);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.ALIVE;

        vel = new Vector2(-MOVE_VELOCITY, 0); //Moves same speed as tubeset so it looks even

        //An actor's origin defines the center of rotation
        setOrigin(Align.center);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        switch(state){
            case ALIVE:
                actAlive(delta); //Move when alive and update image and bounds
                break;
            case DEAD:
                vel = Vector2.Zero; //Stop when jumper dies
                break;
        }
    }

    private void actAlive(float delta) {
        updatePosition(delta);
       /* if(getX(Align.right) <= 0){
            setX(getX() + JumpGame.WIDTH);
        }*/
    }

    private void updatePosition(float delta) {
        setX(getX() + vel.x * delta);
        //setY(getY() + vel.y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    public TextureRegion getRegion(){
        return region;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}