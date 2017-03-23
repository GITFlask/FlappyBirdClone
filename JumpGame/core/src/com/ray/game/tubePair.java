package com.ray.game;

import com.badlogic.gdx.utils.Align;

/**
 * Created by Ray on 15/12/2016.
 */

/*This tube pair class puts our two tubes and coin objects in the same position together
* Updates them together and reposition them together
* The randomYopening randomly places our gap somewhere in the game screen,
* this allows the app to know where to draw the tube/coin images and boundaries*/

public class tubePair {

    public static final float STARTING_X_POSITION = 400f; //We first draw our images off screen
    public static final float GAP_SIZE = 120f; //The size of the gap

    private Tubes topTube;
    private Tubes botTube;
    private Coin coin;

    public tubePair(Tubes topTube, Tubes botTube){
        this.botTube=botTube;
        this.topTube=topTube;
        this.coin = new Coin(); //Gives this set a new coin
    }

    //Resets the tubePair if it's scrolled off the screen
    public void update(){
        //Since the whole class is the same X position we just use our topTube for it's positional condition.
        //It is aligned to the right of the images so it only re-initialises when the whole image is off screen
        if(topTube.getX(Align.right) < 0){
            reInitPipes();
        }
    }

    //Re initialise the tubes when they scroll off the screen
    private  void reInitPipes(){
        float y = Utils.getRandomYOpening(); //Get a random y position for the gap
        float xDisplacement = gamePlayScreen.TUBE_SPACING * gamePlayScreen.TUBE_SETS; //Drawn back to where it started * the amount of tubes.

        botTube.setPosition(botTube.getX() + xDisplacement, y - GAP_SIZE/2, Align.topLeft);
        topTube.setPosition(topTube.getX() + xDisplacement, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(botTube.getX(Align.center), botTube.getY(Align.top) + GAP_SIZE/2, Align.center);
    }

    //These methods sets the tubes for when they first appear in our game screen
    public void initFirst(){
        float y = Utils.getRandomYOpening();
        botTube.setPosition(STARTING_X_POSITION, y - GAP_SIZE/2, Align.topLeft);
        topTube.setPosition(STARTING_X_POSITION, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(botTube.getX(Align.center), botTube.getY(Align.top) + GAP_SIZE/2, Align.center);
    }
    public void initSecond(){
        float y = Utils.getRandomYOpening();
        botTube.setPosition(STARTING_X_POSITION + gamePlayScreen.TUBE_SPACING, y - GAP_SIZE/2, Align.topLeft);
        topTube.setPosition(STARTING_X_POSITION + gamePlayScreen.TUBE_SPACING, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(botTube.getX(Align.center), botTube.getY(Align.top) + GAP_SIZE/2, Align.center);
    }
    public void initThird(){
        float y = Utils.getRandomYOpening();
        botTube.setPosition(STARTING_X_POSITION + gamePlayScreen.TUBE_SPACING * 2, y - GAP_SIZE/2, Align.topLeft);
        topTube.setPosition(STARTING_X_POSITION + gamePlayScreen.TUBE_SPACING * 2, y + GAP_SIZE/2, Align.bottomLeft);
        coin.setPosition(botTube.getX(Align.center), botTube.getY(Align.top) + GAP_SIZE/2, Align.center);
    }


    public Tubes getTopTube() {
        return topTube;
    }
    public Tubes getBotTube(){
        return botTube;
    }
    public Coin getCoin() {
        return coin;
    }

    //When we hit our coin, we reposition it way off the screen so we can't see it until it is reinitialised.
    public void moveCoinOffScreen() {
        coin.setY(coin.getY() + -1000);
    }
}
