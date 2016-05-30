package ifi.lmu.com.handmeasurementstudy;



import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ifi.lmu.com.handmeasurementstudy.gui.Drawing;
import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.Coords;
import ifi.lmu.com.handmeasurementstudy.system.Tap;


public class Tapping extends Activity {
/*
    private static final int[][] latinSquare = {
            {6, 1, 3, 2, 4, 6},
            {2, 4, 1, 6, 5, 3},
            {4, 3, 2, 5, 6, 1},
            {1, 5, 6, 3, 2, 4},
            {5, 6, 4, 1, 3, 2},
            {3, 2, 5, 4, 1, 6}
    };
    */
    public static final int n_TARGET_WIDTH = 9;//14;
    public static final int n_TARGET_HEIGHT = 16;//24;
  //  public static int nSideLength = latinSquare.length;
    //private int nTargetCounter = 0;
    private Drawing drawing;
    private int nViewWidth;
    private int nViewHeight;
    private float fTargetX;
    private float fTargetY;

    private boolean bBackPressedSecondTime = false;

    // touch related variables
    private float fTouchDownX;
    private float fTouchDownY;
    private float fPressureDown;
    private float fSizeDown;
    private long nStartTime;
    private ArrayList<Coords> aMoveCoords;

    private ArrayList<Tap> loggedTaps;
    private ArrayList<Integer> anAllCrosshairs;

    private long nStartTime2;
    //private Array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        nStartTime2 = System.currentTimeMillis();
        //setContentView(R.layout.activity_tapping);// TODO out for testing
        // Remove title bar:
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_container); // TODO out for testing

        // Get/create db handler:
        //this.dbHandler = DBHandler.getInstance(this);
        loggedTaps = new ArrayList<>();
        aMoveCoords = new ArrayList<>();


        drawing = new Drawing(this, this);
        /*
        drawing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Tapping", "onTouch: " + event.getAction());
                onUserTouch(v, event);
                return false;
            }
        });
        */

        //mainLayout.addView(drawing); // TODO out for testing

        setContentView(drawing);
        //nViewWidth = drawing.getViewWidth();
        //nViewHeight = drawing.getViewHeight();

        // fill array with all crosshair numbers
        anAllCrosshairs = new ArrayList<Integer>();
        for (int i = 0; i < (n_TARGET_HEIGHT * n_TARGET_WIDTH); i++){
            anAllCrosshairs.add(i);
        }

        startTappingTest();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        onUserTouch(event);
        return false;
    }

    private void startTappingTest () {
        // TODO iterate through Latin square
        // TODO update on touch
        showNextCrosshair();

    }

    private void onShowNextTap () {
        //nTargetCounter++;
        drawing.invalidate();
        showNextCrosshair();
    }

    private void showNextCrosshair () {
        //check if crosshairs are left
        if(anAllCrosshairs.size() > 0) {

            int nNextCrosshairIndex = getNextRandomCrosshairIndex();
            int[] anLatinIndex = getLatinIndexByCrosshairCount(nNextCrosshairIndex);
            float[] anNewTargetLocation = getAbsoluteTargetLocation(anLatinIndex[0], anLatinIndex[1]);
            drawing.setNewTargetLocation(anNewTargetLocation[0], anNewTargetLocation[1]);
            //drawing.setNewRelativeTargetLocation(anLatinIndex[0], anLatinIndex[1]);


            drawing.setBackgroundColor(Color.WHITE);
            //setContentView(drawing);
        }
        else {
            // TODO save all to database
            // TODO switch to next activity
            float fTime = (float)(System.currentTimeMillis() - nStartTime2);
            Log.e("TIME FOR THIS TASK: ", String.valueOf(fTime/1000) + " seconds");
            finish();
        }
    }

    private int getNextRandomCrosshairIndex () {
        Random oRand = new Random();
        int i_nIndex = oRand.nextInt(anAllCrosshairs.size());
        //anAllCrosshairs.re
        return i_nIndex;
    }

    private int[] getLatinIndexByCrosshairCount(int i_nCrosshairIndex) {
        int[] anLatinIndex = new int[2];
        int nIndex = anAllCrosshairs.get(i_nCrosshairIndex);

        Log.d("Crosshairs left: ", String.valueOf(anAllCrosshairs.size()));

        anAllCrosshairs.remove(i_nCrosshairIndex);
        anLatinIndex[0] = nIndex / n_TARGET_HEIGHT;
        anLatinIndex[1] = nIndex % n_TARGET_HEIGHT;

        return anLatinIndex;
    }

    private float[] getAbsoluteTargetLocation (int i_nNumX, int i_nNumY){

        if(nViewWidth == 0
                || nViewHeight == 0){
            Log.e("Tapping", "No canvas size set yet!");
        }

        fTargetX = (nViewWidth / n_TARGET_WIDTH) * (i_nNumX + 0.5f);
        fTargetY = (nViewHeight / n_TARGET_HEIGHT) * (i_nNumY + 0.5f);

        float[] anAbsoluteLocation = {fTargetX, fTargetY};
        return anAbsoluteLocation;
    }


    public boolean onUserTouch(MotionEvent event) {

        saveTouch(event);

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            onShowNextTap();
        }

        return false;
    }

    private void saveTouch(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                fTouchDownX = event.getX();
                fTouchDownY = event.getY();
                nStartTime = System.currentTimeMillis();
                fSizeDown = event.getSize();
                fPressureDown = event.getPressure();
                break;

            case MotionEvent.ACTION_MOVE:
                Coords oMove = new Coords(event.getX(), event.getY());
                aMoveCoords.add(oMove);
                break;

            case MotionEvent.ACTION_UP:
                int nDuration = (int) (System.currentTimeMillis() - nStartTime);
                float fTouchUpX = event.getX();
                float fTouchUpY = event.getY();
                float fPressureUp = event.getPressure();
                float fSizeUp = event.getSize();
                float fTargetX = drawing.getTargetWidth();
                float fTargetY = drawing.getTargetHeight();
                Coords[] aoMoveArray = aMoveCoords.toArray(new Coords[aMoveCoords.size()]);

                Tap oTap = new Tap(fTouchDownX, fTouchDownY, fTouchUpX, fTouchUpY, fTargetX, fTargetY,
                        nDuration, fPressureDown, fPressureUp, fSizeDown, fSizeUp, aoMoveArray);
                loggedTaps.add(oTap);

                aMoveCoords.clear();
                break;

            default: break;
        }




/*
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        float globalX = v.getLeft() + rawX;
        float globalY = v.getTop() + rawY;





        // down on panel:

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("Tapping", "touch down panel at: " + x + ", " + y
                    + " (global: " + globalX + ", " + globalY + ")");

            Tap oCurrentTouch = new Tap(event.getX(),
                    event.getY(),
                    ,
                    ,
                    fTargetX,
                    fTargetY,);

            public float downX;
            public float downY;
            public float upX;
            public float upY;
            public float targetX;
            public float targetY;
            public int hit;
            public long timeDown;
            public long timeUp;
            public float pressureDown;
            public float pressureUp;
            public float sizeDown;
            public float sizeUp;
            public float minorDown;
            public float minorUp;
            public float majorDown;
            public float majorUp;

            this.lastDownX = x;// globalX;
            this.lastDownY = y;// globalY;
            this.lastDownPressure = event.getPressure();
            this.lastDownSize = event.getSize();
            this.lastDownMinor = event.getTouchMinor();
            this.lastDownMajor = event.getTouchMajor();
            this.lastTimeDown = System.currentTimeMillis()
                    - this.trialStartTime;
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tapping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBackgroundSize(int i_nBackgroundW, int i_nBackgroundH) {
        Log.d("Tapping", "setBackgroundSize: " + i_nBackgroundW + " x " + i_nBackgroundH);
        nViewWidth = i_nBackgroundW;
        nViewHeight = i_nBackgroundH;
    }

    public void onBackPressed() {
        //doing nothing on pressing Back key
        if( ! bBackPressedSecondTime){
            bBackPressedSecondTime = true;
            Toast.makeText(getApplicationContext(), "press < again to leave", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            bBackPressedSecondTime = false;
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        ActivityManager.SaveResultsInDatabase((Object[]) loggedTaps.toArray());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("isFinished",true);
        setResult(Activity.RESULT_OK,returnIntent);
    }
}
