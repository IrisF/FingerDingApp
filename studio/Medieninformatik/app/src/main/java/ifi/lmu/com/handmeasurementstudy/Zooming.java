package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;
import ifi.lmu.com.handmeasurementstudy.system.ZoomListener;


public class Zooming extends ActionBarActivity implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;
    private ArrayList<Zoom> zoomData;
    private ZoomListener zoomListener;

    private ZoomingRectangles zoomingRectangles;
    private RelativeLayout zoomingLayout;

    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zooming);

        // add custom View to draw rectangles
        zoomingRectangles = new ZoomingRectangles(this);
        zoomingLayout = (RelativeLayout) findViewById(R.id.zoomLayout);
        zoomingLayout.addView(zoomingRectangles);

        //custom gesture listener to grap zooming gesture
        zoomListener = new ZoomListener(this);
        scaleGestureDetector = new ScaleGestureDetector(this, zoomListener);
        zoomData = new ArrayList<Zoom>();

        //grap other sensor events
        sensorHelper = new SensorHelper(this);
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


//TODO this does not fire?!
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("Touch", "########## Touch Event ###########");
        Log.e("Touch", event.toString());
        return true;
    }


    @Override
    public boolean onTouchEvent (MotionEvent event) {
        //scale
        scaleGestureDetector.onTouchEvent(event);
        if(zoomListener.getZoom() != null) {
            zoomData.add(zoomListener.getZoom());
        }

        Log.e("ZoomData", zoomData.toString());

        return true;

    }
}
