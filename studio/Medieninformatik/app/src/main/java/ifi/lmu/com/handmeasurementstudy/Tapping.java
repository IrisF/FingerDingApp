package ifi.lmu.com.handmeasurementstudy;



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

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.gui.Drawing;
import ifi.lmu.com.handmeasurementstudy.system.Tap;


public class Tapping extends Activity {

    private static final int[][] latinSquare = {
            {6, 1, 3, 2, 4, 6},
            {2, 4, 1, 6, 5, 3},
            {4, 3, 2, 5, 6, 1},
            {1, 5, 6, 3, 2, 4},
            {5, 6, 4, 1, 3, 2},
            {3, 2, 5, 4, 1, 6}
    };
    public static int nSideLength = latinSquare.length;
    private int nTargetCounter = 0;
    private Drawing drawing;
    private int nViewWidth;
    private int nViewHeight;
    private float fTargetX;
    private float fTargetY;

    private ArrayList<Tap> loggedTaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tapping);// TODO out for testing
        // Remove title bar:
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_container); // TODO out for testing

        // Get/create db handler:
        //this.dbHandler = DBHandler.getInstance(this);
        loggedTaps = new ArrayList<Tap>();


        drawing = new Drawing(this, this);
        drawing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Tapping", "onTouch: " + event.getAction());
                onUserTouch(v, event);
                return false;
            }
        });
        //mainLayout.addView(drawing); // TODO out for testing

        setContentView(drawing);
        //nViewWidth = drawing.getViewWidth();
        //nViewHeight = drawing.getViewHeight();

        startTappingTest();
    }

    public void setNewCrosshairPosition (int i_nX, int i_nY) {

    }

    private void startTappingTest () {
        // TODO iterate through Latin square
        // TODO update on touch
        showCrosshair(nTargetCounter);

    }

    private void onShowNextTap () {
        nTargetCounter++;
        drawing.invalidate();
        showCrosshair(nTargetCounter);
    }

    private void showCrosshair (int i_nCrosshairIndex) {
        int[] anLatinIndex =  getLatinIndexByCrosshairCount(i_nCrosshairIndex);
        float[] anNewTargetLocation = getAbsoluteTargetLocation(anLatinIndex[0], anLatinIndex[1]);
        drawing.setNewTargetLocation(anNewTargetLocation[0], anNewTargetLocation[1]);
        //drawing.setNewRelativeTargetLocation(anLatinIndex[0], anLatinIndex[1]);


        drawing.setBackgroundColor(Color.WHITE);
        //setContentView(drawing);
    }

    private int[] getLatinIndexByCrosshairCount(int i_nCrosshairIndex) {
        int[] anLatinIndex = new int[2];

        int nLatinSize = latinSquare.length;
        anLatinIndex[0] = i_nCrosshairIndex / nLatinSize;
        anLatinIndex[1] = i_nCrosshairIndex % nLatinSize;

        return anLatinIndex;
    }

    private float[] getAbsoluteTargetLocation (int i_nNumX, int i_nNumY){

        if(nViewWidth == 0
                || nViewHeight == 0){
            Log.e("Tapping", "No canvas size set yet!");
        }

        fTargetX = (nViewWidth / latinSquare.length) * (i_nNumX + 0.5f);
        fTargetY = (nViewHeight / latinSquare.length) * (i_nNumY + 0.5f);

        float[] anAbsoluteLocation = {fTargetX, fTargetY};
        return anAbsoluteLocation;
    }


    public boolean onUserTouch(View v, MotionEvent event) {


        saveTouch(v, event);
        onShowNextTap();

        return false;
    }

    private void saveTouch(View v, MotionEvent event) {
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
}
