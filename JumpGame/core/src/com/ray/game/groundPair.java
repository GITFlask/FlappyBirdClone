package com.ray.game;

import com.badlogic.gdx.utils.Align;

/**
 * Created by Ray on 16/12/2016.
 */

/*This class is designed to take in our two ground objects so we can update the position of one behind
* the other when one of them moves off screen, creating one long continuous ground.
* */

public class groundPair {

    private Ground ground1;
    private Ground ground2;

    public groundPair(Ground g1, Ground g2) {
        this.ground1 = g1;
        this.ground2 = g2;
    }

    //Resets the pair if it's scrolled off the screen
    //The X position of the ground object is aligned to the right as it is moving left.
    public void update() {
        if (ground1.getX(Align.right) <= 0) {
            ground1.setX(ground1.getX() + JumpGame.WIDTH * 2);
        }
        if (ground2.getX(Align.right) <= 0) {
            ground2.setX(ground2.getX() + JumpGame.WIDTH * 2);
        }

    }

    //This method is called when game state changes to dead/dying to prevent it from continuing to move
    public void stop() {
        ground1.vel.x = 0;
        ground2.vel.x = 0;

    }
}