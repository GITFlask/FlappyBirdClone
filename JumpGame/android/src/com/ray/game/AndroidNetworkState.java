package com.ray.game;
import android.app.Activity;
import android.widget.Toast;

import com.ray.game.*;

/**
 * Created by Ray on 19/01/2017.
 */

/*This class takes implements android interface and allows us to display a toast in our app on android devices*/
    /*This gets taken in by the android launcher to enable to the use of toast*/
public class AndroidNetworkState implements AndroidInterfaces {

    /*A toast needs a context*/
    private Activity context;

    public AndroidNetworkState(Activity context) {
        this.context = context;
    }
        public int getNetworkState () {
            return 1;
        }

        @Override
        public void toast (final String t){
            //Android code here
            //Must do this to run on ui thread
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

