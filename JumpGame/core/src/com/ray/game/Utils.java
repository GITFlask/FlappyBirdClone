package com.ray.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by Ray on 15/12/2016.
 */

/*This class contains some methods we will be using.
* getRandomYOpening for where our tube gap is placed
* and getFloatyAction tap to start label and plane in pregame state.
* */

public class Utils {

    public static float getRandomYOpening(){ //Set up a random position for the tube gap
        return MathUtils.random(96f + 48f, JumpGame.HEIGHT - 64f);
    }

    public static Action getFloatyAction(){
        MoveByAction a1 = Actions.moveBy(0, 10f, 1f, Interpolation.sine); //Move up
        MoveByAction a2 = Actions.moveBy(0, -10f, 1f, Interpolation.sine); //Move down
        SequenceAction sa = Actions.sequence(a1, a2); //Do the movements one after another
        return Actions.forever(sa); //Keep doing this action
    }

}
