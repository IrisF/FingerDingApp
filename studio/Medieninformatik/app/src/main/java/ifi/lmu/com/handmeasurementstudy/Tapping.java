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

import ifi.lmu.com.handmeasurementstudy.gui.Drawing;


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
    private float fViewWidth;
    private float fViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tapping);// TODO out for testing
        // Remove title bar:
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_container); // TODO out for testing

        // Get/create db handler:
        //this.dbHandler = DBHandler.getInstance(this);


        drawing = new Drawing(this, this);
        drawing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Tapping","onTouch (Listener)");
                onUserTouch(v, event);
                return false;
            }
        });
        //mainLayout.addView(drawing); // TODO out for testing

        setContentView(drawing);
        fViewWidth = drawing.getViewWidth();
        fViewHeight = drawing.getViewHeight();

        startTappingTest();
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
        //int[] anNewTargetLocation = getAbsoluteTargetLocation(anLatinIndex[0], anLatinIndex[1]);
        //drawing.setNewTargetLocation(anNewTargetLocation[0], anNewTargetLocation[1]);
        drawing.setNewRelativeTargetLocation(anLatinIndex[0], anLatinIndex[1]);


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

    private int[] getAbsoluteTargetLocation (int i_nNumX, int i_nNumY){

        int nX = ((int) fViewWidth / latinSquare.length) * (i_nNumX + 1);
        int nY = ((int) fViewHeight / latinSquare.length) * (i_nNumY + 1);

        int[] anAbsoluteLocation = {nX, nY};
        return anAbsoluteLocation;
    }


    public boolean onUserTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        float globalX = v.getLeft() + rawX;
        float globalY = v.getTop() + rawY;

        // down on panel:
        /*
        if (v == this.drawingPanel
                && event.getAction() == MotionEvent.ACTION_DOWN
                && !this.trialFinished && this.trialStarted) {
            Log.d("DEBUG", "touch down panel at: " + x + ", " + y
                    + " (global: " + globalX + ", " + globalY + ")");
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
        onShowNextTap();

        return false;
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

}
