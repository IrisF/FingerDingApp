package ifi.lmu.com.handmeasurementstudy.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ifi.lmu.com.handmeasurementstudy.Swiping;
import ifi.lmu.com.handmeasurementstudy.Tablet;
import ifi.lmu.com.handmeasurementstudy.ZoomingRectangles;
import ifi.lmu.com.handmeasurementstudy.Tapping;
import ifi.lmu.com.handmeasurementstudy.Zooming;
import ifi.lmu.com.handmeasurementstudy.Scrolling;

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

    public static final int n_ACTIVITY_TAPPING = 0,
            n_ACTIVITY_SWIPING = 1,
            n_ACTIVITY_ZOOMING = 2,
            n_ACTIVITY_SCOLLING = 3,
            n_ACTIVITY_ZOOMING_VIEW = 4,
            n_ACTIVITY_TABLET = 5;

    enum Activities {
        n_ACTIVITY_TAPPING,
        n_ACTIVITY_SWIPING,
        n_ACTIVITY_ZOOMING,
        n_ACTIVITY_SCOLLING,
        n_ACTIVITY_ZOOMING_MAXIMUM,
        n_ACTIVITY_TABLET
    }

    private Activities _eCurrentActivity;

    private Class[] _aoActivities = {
            Tapping.class,
            Swiping.class,
            Zooming.class,
            Scrolling.class,
            ZoomingRectangles.class,
            Tablet.class
    };

    private Class[] _aoOrder;
    private static Object[] _aoResult;

    private int _nCurrentActivity;
    private int _nUserId;
    private Context _oContext;

    public ActivityManager (Context context, int i_nUserId) {

        // TODO DBHelper anlegen
        // TODO onActivityFinished (Datentyp incht festgelegt --> Objekt?)

        _nCurrentActivity = 0;
        _oContext = context;
        _nUserId = i_nUserId;

        // get current latin row
        int[] anLatinRow = latinSquare[ i_nUserId % latinSquare.length ];

        _aoOrder = new Class[6];

        // create latin order
        for(int i = 0; i < anLatinRow.length; i++){
            _aoOrder[i] = _aoActivities[anLatinRow[i]];
        }


    }

    public void Start () {
        // TODO start latin squared activities here
        StartNextActivity();

    }

    private void StartNextActivity() {

        if(_nCurrentActivity < _aoOrder.length) {

            _eCurrentActivity = SetCurrentActivityEnumByInt(_nCurrentActivity);
            //_eCurrentActivity = Activities.n_ACTIVITY_SCOLLING;

            //Intent i = new Intent(_oContext, _aoOrder[_nCurrentActivity]);
            Intent i = new Intent(this, _aoOrder[_nCurrentActivity]);
            i.putExtra("id", _nUserId);
            startActivityForResult(i,1);

            _nCurrentActivity++;
        }
        else {
            _nCurrentActivity = 0;
            //TODO exit stuff
            
        }

    }

    private Activities SetCurrentActivityEnumByInt (int i_nCurrentActivity) {

        switch(i_nCurrentActivity){
            case n_ACTIVITY_TAPPING: //0
                return Activities.n_ACTIVITY_TAPPING;
            case n_ACTIVITY_SWIPING: //1
                return Activities.n_ACTIVITY_SWIPING;
            case n_ACTIVITY_ZOOMING: //2
                return Activities.n_ACTIVITY_ZOOMING;
            case n_ACTIVITY_SCOLLING: //3
                return Activities.n_ACTIVITY_SCOLLING;
            case n_ACTIVITY_ZOOMING_VIEW: //4
                return Activities.n_ACTIVITY_ZOOMING_MAXIMUM;
            case n_ACTIVITY_TABLET: //5
                return Activities.n_ACTIVITY_TABLET;
            default:
                return null;
        }
    }


    public void StoreResultsInDatabase () {
        // TODO distinguish and save accordingly
        switch(_nCurrentActivity){
            case n_ACTIVITY_TAPPING: //0
                Tap[] aoTap = (Tap[]) _aoResult;
                // TODO save to database
                break;
            case n_ACTIVITY_SWIPING: //1
                Swipe[] aoSwipe = (Swipe[]) _aoResult;
                // TODO save to database
                break;
            case n_ACTIVITY_ZOOMING: //2
                Zoom[] aoZoom = (Zoom[]) _aoResult;
                // TODO save to database
                break;
            case n_ACTIVITY_SCOLLING: //3
                Scroll[] aoScroll = (Scroll[]) _aoResult;
                // TODO save to database
                break;
            case n_ACTIVITY_ZOOMING_VIEW: //4
                Zoom[] aoZoom1 = (Zoom[]) _aoResult;
                // TODO save to database
                break;
            case n_ACTIVITY_TABLET: //5
                // do nothing, tablet handles this
            default:
                break;
        }

        StartNextActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                boolean bIsFinished = data.getBooleanExtra("isFinished", true);

                if(bIsFinished){
                    StoreResultsInDatabase();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public static void SaveResultsInDatabase (Object[] i_aoResults) {
        _aoResult = i_aoResults;
        Log.d("ActivityManager RESULTS", i_aoResults.toString());
    }
}
