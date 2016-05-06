package ifi.lmu.com.handmeasurementstudy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;


public class Zooming extends ActionBarActivity {

    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zooming);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                Log.e("e", "On Scale");
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                Log.e("Scale", "############################### new Scaling Event #####################################");
                Log.e("Scale", "Current Span " + Float.toString(scaleGestureDetector.getCurrentSpan()));
                Log.e("Scale", "Current Coord X " + Float.toString(scaleGestureDetector.getCurrentSpanX()));
                Log.e("Scale", "Current Coord Y " + Float.toString(scaleGestureDetector.getCurrentSpanY()));
                Log.e("Scale", "Focus X " + Float.toString(scaleGestureDetector.getFocusX()));
                Log.e("Scale", "Focus Y " + Float.toString(scaleGestureDetector.getFocusY()));
                Log.e("Scale", "Scale Factor " + Float.toString(scaleGestureDetector.getScaleFactor()));
                Log.e("Scale", "Time Delta " + Float.toString(scaleGestureDetector.getTimeDelta()));
                Log.e("Scale", "Event Time " + Long.toString(scaleGestureDetector.getEventTime()));
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                Log.e("e", "On Scale End");
            }
        });
    }

    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zooming, menu);
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
