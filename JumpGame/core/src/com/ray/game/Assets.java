package com.ray.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ray on 15/12/2016.
 */

/*The textures and music used in game are defined in this class.
* For images we have set them as libgdx's textureRegion variables, these variables are found in a TextureAtlas.
* The texture files are inside our SpriteBatch where we can call out when we need to draw certain files in game.
* The images and sound files used are downloaded from www.opengameart.org.
* The logo is created using royalty free stock images and GIMP application.
* The font we used in the game, the font is downloaded from www.1001freefont.com and files are made using BMFont application.
* The font fnt file is loaded to a BitMapFont to allow us to find the characters we need and the png contains the font character images loaded and used.
* The file font colour is set to white to allow libgdx to tint with colour.
* The images are stored in JumpGame/Unpacked_External_Assets folder, these images are packed using a texture packer
* and the texture atlas files are stored in JumpGame/android/assets
*/

public class Assets {

    //Disposables, these files can and are disposed when they are no longer needed to prevent memory leaks.

    /*The texture atlas gets the position of our images*/
    public static TextureAtlas atlas;

    /*The spritebatch holds these texture and called in draw methods*/
    public static SpriteBatch batch;

    /*The bitmap found finds the font characters*/
    public static BitmapFont font;

    /*Sound effect variables (Short sounds)*/
    public static Sound sfxFlap;
    public static Sound sfxCrash;
    public static Sound sfxPress;
    public static Sound sfxPoint;

    /*Our game music*/
    public static Music Theme;


    //Non-Disposables

    /*Our Jumper character*/
    public static TextureRegion jumper1;
    public static TextureRegion jumper2;
    public static TextureRegion jumper3;

    /*Our crash Image*/
    public static TextureRegion crash;

    /*Our ruby/coin images*/
    public static TextureRegion ruby1;
    public static TextureRegion ruby2;
    public static TextureRegion ruby3;
    public static TextureRegion ruby4;
    public static TextureRegion ruby5;
    public static TextureRegion ruby6;

    /*Background image for gamePlayScreen*/
    public static TextureRegion background;

    /*The ground*/
    public static TextureRegion ground;

    /*Tube Image*/
    public static TextureRegion tube;

    /*JumpGame Title image*/
    public static TextureRegion title;

    /*EraticNinja Logo*/
    public static TextureRegion logo;

    /*Start Game button pressed and not pressed*/
    public static TextureRegion buttonUp;
    public static TextureRegion buttonDown;

    /*Background image for mainMenuScreen*/
    public static TextureRegion mainBg;

    /*Reset score Button up and down*/
    public static TextureRegion resetScore;
    public static TextureRegion resetScore2;

    /*Hghscore button up and down*/
    public static TextureRegion highScore;
    public static TextureRegion highScore2;

    //Animations
    public static Animation jumperAnimation;
    public static Animation rubyAnimation;

    /*Load method for the Assets class is created here. This method is called on create method in main game class where the activity is set(JumpGame.java)*/
    public static void load(){
        //We load our TextureAtlas which contains the positional coordinates and names of our images.
        atlas = new TextureAtlas("pack.atlas");

        //The batch will hold our image files to be drawn in game.
        batch = new SpriteBatch();

        //The BitMapFont file(.fnt) contains the positional coordinates of our characters.
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font_0.png"), false);

        //This is our character
        jumper1 = atlas.findRegion("planeB1");
        jumper2 = atlas.findRegion("planeB2");
        jumper3 = atlas.findRegion("planeB3");

        //Images for reset button. Images are for when button is unpressed and pressed respectively.
        resetScore = atlas.findRegion("resetUp");
        resetScore2 = atlas.findRegion("resetDown");

        //Image for when our character crashes/dies.
        crash = atlas.findRegion("pow");

        //Images for our coin, for when we score a point after passing a set of obstacles.
        ruby1 = atlas.findRegion("ruby1");
        ruby2 = atlas.findRegion("ruby2");
        ruby3 = atlas.findRegion("ruby3");
        ruby4 = atlas.findRegion("ruby4");
        ruby5 = atlas.findRegion("ruby5");
        ruby6 = atlas.findRegion("ruby6");

        //The background image for gamePlayScreen
        background = atlas.findRegion("bg1");

        //The image for our ground.
        ground = atlas.findRegion("ground");

        //Image for obstacles.
        tube =  atlas.findRegion("tube");

        //Our title image.
        title =  atlas.findRegion("title");

        //Button images to play game.
        buttonUp =  atlas.findRegion("buttonUp");
        buttonDown =  atlas.findRegion("buttonDown");

        highScore = atlas.findRegion("scoreUp");
        highScore2 = atlas.findRegion("scoreDown");

        //mainBg is for the colour background of our main screen(first screen when game loads.
        mainBg =  atlas.findRegion("mainBg");

        //Our logo on the bottom left of our main screen.
        logo = atlas.findRegion("eratcNinja");

        /*The character(plane) and coin/ruby frames are loaded to libgdx's Animation object so that we can play frame by frame. The animation is looped*/
        jumperAnimation = new Animation(.04f, jumper1, jumper2, jumper3);
        jumperAnimation.setPlayMode(Animation.PlayMode.LOOP);
        rubyAnimation = new Animation(.1f, ruby1, ruby2, ruby3, ruby4, ruby5, ruby6);
        rubyAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //The sound effect and music files are loaded here.
        sfxFlap = Gdx.audio.newSound(Gdx.files.internal("sounds/sfxFlap.mp3"));
        sfxCrash = Gdx.audio.newSound(Gdx.files.internal("sounds/bang.mp3"));
        sfxPress = Gdx.audio.newSound(Gdx.files.internal("sounds/sfxPress.mp3"));
        sfxPoint = Gdx.audio.newSound(Gdx.files.internal("sounds/sfxPoint.mp3"));
        Theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/happy.mp3"));
    }

    //Dispose all disposables here. The dispose method is called in JumpGame.java
    public static void dispose(){
        if(atlas != null) {
            atlas.dispose();
        }
        if(batch != null) {
            batch.dispose();
        }

        font.dispose();
        sfxFlap.dispose();
        sfxCrash.dispose();
        sfxPoint.dispose();
        sfxPress.dispose();
        Theme.dispose();
    }

    /*Methods to play sound effect files in game*/
    public static void playFlapSound(){ sfxFlap.play();}
    public static void playCrashSound(){ sfxCrash.play();}
    public static void playPointSound(){ sfxPoint.play();}
    public static void playPressSound(){ sfxPress.play();}

    /*Method to play music file for game, the volume has been turned down so sound effects can be heard and looped*/
    public static void playThemeMusic(){
        Theme.setVolume(.1f);
        Theme.setLooping(true);
        Theme.play();
    }


}
