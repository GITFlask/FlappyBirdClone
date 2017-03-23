package com.ray.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Ray on 11/01/2017.
 */


/*This class is to set our preferences which holds our high score
We set the methods to load our preferences when we start our app
and we set the methods to save our highscore if score is greater than our previous highscore.
We set score to 0 when we hit the reset score button on the main menu screen.
Since we only want one set of preferences we will only call this class once like a singleton class*/


public class SavedDataManager {

    private static final String KEY_PREFERENCES = "prefs";
    private static final String KEY_HIGH_SCORE = "high score";

    private static SavedDataManager instance = null;
    private static int highScore;

    protected SavedDataManager(){ //Exists only to defeat instantiation.
    }

    /*We only need one instance of the SavedDataManager so if there is already one then we won't call it again*/
    public static SavedDataManager getInstance(){
        if(instance == null){
            instance = new SavedDataManager();
        }
        return instance;
    }

    /*
    * Initialise all the saved values from the Preferences
    */
    public void load(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        highScore = prefs.getInteger(KEY_HIGH_SCORE, 0);

    }

    /**
     * Save all the values to Preferences
    **/
    public void save(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        prefs.putInteger(KEY_HIGH_SCORE, highScore);

        prefs.flush();
    }

    /*Sets new value for highscore if it is bigger than previous highscore*/
    public void setHighScore(int score){
        if(score > highScore)
            highScore = score;

    }
    /*Called in mainMenuScreen when reset button is pressed*/
    public void resetHighscore(){
        highScore = 0;
    }

    /*Called in mainMenuScreen when highscore button is pressed*/
    public int getHighScore(){
        return highScore;
    }
}
