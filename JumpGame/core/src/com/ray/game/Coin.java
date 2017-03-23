package com.ray.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Ray on 20/12/2016.
 */

/*Our Coin class extends libgdx's Actor class, the actor class allows us to set the size, rotation, position etc. for our Coin objects
* In this class we set the size of our coin,
* the bounds for collision and set up an act method for it's behavior in game.
* We have overrided the act method for it's behavior and a draw method which takes the required file from our SpriteBatch.*/

public class Coin extends Actor {
    //The size of our coin is set to 32 pixels.
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    //Set the bounds for character(plane) for collision with coin.
    private Rectangle bounds;

    private float time; //We set up a float variable to take in delta time for our animation so it knows when to change frames.
    private Vector2 vel; //This velocity moves our coin.

    public Coin(){
        setWidth(WIDTH);
        setHeight(HEIGHT);
        vel = new Vector2(); //Libgdx's vector2 object can move in the X and Y axis
        vel.x = -Tubes.MOVE_VELOCITY; //Since coin is between the tubes, we take it's velocity to move with it.

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setRandomTime();
    }

    //Set how our coin when the act method is called in game
    @Override
    public void act(float delta) {
        time += delta;
        setX(getX() + vel.x * delta);
        bounds.setPosition(getX(), getY());
    }

    //This draw method draws our animation.
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.rubyAnimation.getKeyFrame(time), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    //Method to set the velocity in our game
    public void setVel(float x, float y) {
        this.vel.x = x;
        this.vel.y = y;
    }

    //We set to start on a random time, this is so we start on a random frame rather than the first one every time.
    public void setRandomTime(){
        time = MathUtils.random(1f, 123f);
    }

    //Method to get the bounds of our coin rectangle for collision detection
    public Rectangle getBounds() {
        return bounds;
    }

}
