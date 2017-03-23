package com.ray.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.ray.game.Assets;
import com.ray.game.JumpGame;

/*This class is to define our tube object/actors for the gamePlayScreen*/

public class Tubes extends Actor {

    public static final int WIDTH = 64; //Set sizes and no need to change so set final
    public static final int HEIGHT = 400;
    public static final float MOVE_VELOCITY = 120f; //The speed that the tubes will move toward our Jumper
    private Rectangle bounds; //bound for collision

    private Vector2 vel; //Velocity to move left

    private TextureRegion region; //The image we will use

    private State state;

    public enum State {ALIVE, DEAD}

    public Tubes() {

        region = new TextureRegion(Assets.tube);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.ALIVE;

        vel = new Vector2(-MOVE_VELOCITY, 0); //Only move on X-axis

        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);

        //An actor's origin defines the center of rotation, we need to rotate the top tube by 180 degrees
        setOrigin(Align.center);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        switch(state){
            case ALIVE:
                actAlive(delta); //Update when alive
                break;
            case DEAD:
                vel = Vector2.Zero; //Stop moving when our player dies
                break;
        }
        updateBounds();
    }

    private void updateBounds() {
        bounds.x = getX();
        bounds.y = getY();
    }

    private void actAlive(float delta) {
        updatePosition(delta);
    } //When alive, keep updating position when it passes the left of screen

    private void updatePosition(float delta) {
        setX(getX() + vel.x * delta);
        setY(getY() + vel.y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setState(State state) {
        this.state = state;
    }
}