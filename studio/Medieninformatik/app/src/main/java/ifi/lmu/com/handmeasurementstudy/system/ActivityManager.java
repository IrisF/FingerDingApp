package ifi.lmu.com.handmeasurementstudy.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ifi.lmu.com.handmeasurementstudy.Swiping;
import ifi.lmu.com.handmeasurementstudy.Tablet;
import ifi.lmu.com.handmeasurementstudy.ZoomingRectangles;
import ifi.lmu.com.handmeasurementstudy.Tapping;
import ifi.lmu.com.handmeasurementstudy.Zooming;
import ifi.lmu.com.handmeasurementstudy.Scrolling;
import ifi.lmu.com.handmeasurementstudy.Slider;

/**
 * Created by Jonny on 24.05.2016.
 */
public class ActivityManager extends Activity { // extends Activity to call startActivity()

    private static final int[][] latinSquare = {
            {6, 1, 3, 2, 4, 6},
            {2, 4, 1, 6, 5, 3},
            {4, 3, 2, 5, 6, 1},
            {1, 5, 6, 3, 2, 4},
            {5, 6, 4, 1, 3, 2},
            {3, 2, 5, 4, 1, 6}
    };

    private Class[] aoActivities = {
            Tapping.class,
            Swiping.class,
            Zooming.class,
            Scrolling.class,
            ZoomingRectangles.class,
            Tablet.class
    };

    private Class[] aoOrder;

    private int nCurrentActivity;
    private int nUserId;
    private Context oContext;

    public ActivityManager (Context context, int i_nUserId) {

        // TODO DBHelper anlegen
        // TODO onActivityFinished (Datentyp incht festgelegt --> Objekt?)

        nCurrentActivity = 0;
        oContext = context;
        nUserId = i_nUserId;

        // get current latin row
        int[] anLatinRow = latinSquare[ i_nUserId % latinSquare.length ];

        aoOrder = new Class[6];

        // create latin order
        for(int i = 0; i < anLatinRow.length; i++){
            aoOrder[i] = aoActivities[anLatinRow[i]];
        }


    }

    public void Start () {
        // TODO start latin squared activities here
        StartNextActivity();

    }

    private void StartNextActivity() {

        if(nCurrentActivity < aoOrder.length) {

            Intent i = new Intent(oContext, aoOrder[nCurrentActivity]);
            i.putExtra("id", nUserId);
            startActivity(i);

            nCurrentActivity++;
        }
        else {
            nCurrentActivity = 0;
            
        }

    }

    public void SaveEntries (Object[] aoLoggingArray){
        // TODO distinguish and save accordingly
    }
}
