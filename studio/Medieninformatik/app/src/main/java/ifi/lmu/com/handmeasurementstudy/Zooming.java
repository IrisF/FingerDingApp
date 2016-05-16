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

    //private Paint rectanglePaint;

    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zooming);

        //rectanglePaint = new Paint();

        zoomListener = new ZoomListener(this);
        scaleGestureDetector = new ScaleGestureDetector(this, zoomListener);
        zoomData = new ArrayList<Zoom>();

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
//TODO eventually log some data here
        Log.e("TouchEvent", event.toString());

        //scale
        scaleGestureDetector.onTouchEvent(event);

        return true;

    }

//TODO eventually do sth on draw
    /*
    public void onDraw(Canvas canvas) {
        //int viewWidth = this.getWidth();
        //int viewHeight = this.getHeight();

        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.RED);

        canvas.drawRect(10, 10, 120-10, 120-10, rectanglePaint);

    }*/



}
