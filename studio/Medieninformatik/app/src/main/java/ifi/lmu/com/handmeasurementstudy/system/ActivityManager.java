package ifi.lmu.com.handmeasurementstudy.system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import ifi.lmu.com.handmeasurementstudy.MaxRadius;
import ifi.lmu.com.handmeasurementstudy.R;
import ifi.lmu.com.handmeasurementstudy.Swiping;
import ifi.lmu.com.handmeasurementstudy.TabletActivity;
import ifi.lmu.com.handmeasurementstudy.ZoomingMaximum;
import ifi.lmu.com.handmeasurementstudy.Tapping;
import ifi.lmu.com.handmeasurementstudy.Zooming;
import ifi.lmu.com.handmeasurementstudy.Scrolling;
import ifi.lmu.com.handmeasurementstudy.db.DBHandler;

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
            n_ACTIVITY_ZOOMING_MAXIMUM = 4,
            n_ACTIVITY_TABLET = 5;

    private static final int n_REQUEST_CODE_ACTIVITIES = 1,
            n_REQUEST_CODE_RADIUS = 2;

    enum Activities {
        n_ACTIVITY_TAPPING,
        n_ACTIVITY_SWIPING,
        n_ACTIVITY_ZOOMING,
        n_ACTIVITY_SCOLLING,
        n_ACTIVITY_ZOOMING_MAXIMUM,
        n_ACTIVITY_TABLET
    }

    private Activities _eCurrentActivity;
    private int _nCurrentActivityNum;

    private Class[] _aoActivities = {
            Tapping.class,
            Swiping.class,
            Zooming.class,
            Scrolling.class,
            ZoomingMaximum.class,
            TabletActivity.class
    };

    private Class[] _aoOrder;
    private static Object[] _aoResult;
    private int[] _anLatinRow;

    private int _nCurrentActivity;
    private int _nUserId;
    private Context _oContext;
    private DBHandler _oDbHandler;

    private void ActivityManager (Context context, int i_nUserId) {

        _oContext = context;

        init(i_nUserId);


    }

    private void init (int i_nUserId){
        _nUserId = i_nUserId;
        // get current latin row
        _anLatinRow = latinSquare[ i_nUserId % latinSquare.length ];

        _aoOrder = new Class[6];
        _oDbHandler = DBHandler.getInstance(_oContext);

        // create latin order
        for(int i = 0; i < _anLatinRow.length; i++){
            _aoOrder[i] = _aoActivities[_anLatinRow[i]-1];
        }
        _nCurrentActivity = 0;
        _nCurrentActivityNum= -1;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar:
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_blank);

        _oContext = getApplicationContext();


        int nId;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nId= 0;
            } else {
                nId= extras.getInt("id");
            }
        } else {
            nId = (int) savedInstanceState.getSerializable("id");
        }

        init(nId);
        Intent oIntent = new Intent(ActivityManager.this, MaxRadius.class);
        startActivityForResult(oIntent, n_REQUEST_CODE_RADIUS);
        // Start now in onActivityResult method
        // Start();
    }

    public void Start () {
        // TODO start latin squared activities here
        StartNextActivity();

    }

    private void StartNextActivity() {
        Log.d("ActivityManager", "StartNextActivity");

        if(_nCurrentActivity < _aoOrder.length) {

            //TODO _eCurrentActivvity passt noch nicht
            _eCurrentActivity = SetCurrentActivityEnumByInt(_nCurrentActivity);
            //_eCurrentActivity = Activities.n_ACTIVITY_SCOLLING;

            //Intent i = new Intent(_oContext, _aoOrder[_nCurrentActivity]);
            Log.e("this: " + this.toString(), ", _aoOrder: " +_aoOrder +", _nCurrentActivity:"+ _nCurrentActivity);
            Intent i = new Intent(_oContext, _aoOrder[_nCurrentActivity]);
            i.putExtra("id", _nUserId);
            startActivityForResult(i,n_REQUEST_CODE_ACTIVITIES);

            //only for test - delete later
            //Intent i = new Intent(_oContext, Tapping.class);
            //i.putExtra("id", _nUserId);
            //startActivity(i);
            _nCurrentActivityNum = _anLatinRow[_nCurrentActivity];
            _nCurrentActivity++;
        }
        else {
            _nCurrentActivity = 0;
            Log.d("ActivityManager", "FERTIG");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String[] strButtons = {"OKAY"};
            builder.setTitle("FERTIG! Vielen Dank fürs mitmachen!")
                    .setItems(strButtons, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            if(which == 0){
                                //TODO count up user id
                                finish();
                            }
                        }
                    });
            builder.create();
            builder.show();


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
            case n_ACTIVITY_ZOOMING_MAXIMUM: //4
                return Activities.n_ACTIVITY_ZOOMING_MAXIMUM;
            case n_ACTIVITY_TABLET: //5
                return Activities.n_ACTIVITY_TABLET;
            default:
                return null;
        }
    }


    public void StoreResultsInDatabase (boolean i_bIsRadius) {

        if( ! i_bIsRadius) {
            switch (_nCurrentActivityNum - 1) {
                case n_ACTIVITY_TAPPING: //0
                    //Tap[] aoTap = (Tap[]) _aoResult;
                    // save to database
                    for (int i = 0; i < _aoResult.length; i++) {
                        _oDbHandler.insertTap((Tap) _aoResult[i], _nUserId);
                    }
                    break;
                case n_ACTIVITY_SWIPING: //1
                    //Swipe[] aoSwipe = (Swipe[]) _aoResult;
                    for (int i = 0; i < _aoResult.length; i++) {
                        _oDbHandler.insertSwipe((Swipe) _aoResult[i], _nUserId);
                    }
                    break;
                case n_ACTIVITY_ZOOMING: //2
                    //Zoom[] aoZoom = (Zoom[]) _aoResult;
                    for (int i = 0; i < _aoResult.length; i++) {
                        _oDbHandler.insertZoom((Zoom) _aoResult[i], _nUserId);
                    }
                    break;
                case n_ACTIVITY_SCOLLING: //3
                    //Scroll[] aoScroll = (Scroll[]) _aoResult;
                    for (int i = 0; i < _aoResult.length; i++) {
                        _oDbHandler.insertScroll((Scroll) _aoResult[i], _nUserId);
                    }
                    break;
                case n_ACTIVITY_ZOOMING_MAXIMUM: //4
                    //Zoom[] aoZoom1 = (Zoom[]) _aoResult;
                    for (int i = 0; i < _aoResult.length; i++) {
                        _oDbHandler.insertZoom((Zoom) _aoResult[i], _nUserId);
                    }
                    break;
                case n_ACTIVITY_TABLET: //5
                    // do nothing, tablet handles this
                default:
                    break;
            }

            StartNextActivity();
        }
        else {
            for (int i = 0; i < _aoResult.length; i++) {
                _oDbHandler.insertRadius((Swipe) _aoResult[i], _nUserId);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("ActivityManager", "onActivityResult " + String.valueOf(requestCode) +" " + String.valueOf(resultCode)+ " ?= " + String.valueOf(Activity.RESULT_OK));
        if (requestCode == n_REQUEST_CODE_ACTIVITIES) {
            if(resultCode == Activity.RESULT_OK){
                boolean bIsFinished = data.getBooleanExtra("isFinished", true);

                if(bIsFinished){
                    StoreResultsInDatabase(false);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("ActvityManager", "RESULT CANCELED");
            }
        }
        else if (requestCode == n_REQUEST_CODE_RADIUS) {
            if(resultCode == Activity.RESULT_OK){
                boolean bIsFinished = data.getBooleanExtra("isFinished", true);

                if(bIsFinished){
                    StoreResultsInDatabase(true);
                    Start();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("ActvityManager", "RESULT CANCELED");
            }
        }
    }

    public static void SaveResultsInDatabase (Object[] i_aoResults) {
        if(i_aoResults != null) {
            _aoResult = i_aoResults;
            Log.d("ActivityManager RESULTS", i_aoResults.toString());
        }
        else{
            Log.d("ActivityManager RESULTS", "results NULL :(");
        }
    }
}
