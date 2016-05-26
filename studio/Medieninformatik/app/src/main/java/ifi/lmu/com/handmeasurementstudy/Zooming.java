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

    private ImageView imageView;

    private int rectangleIndex;

    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zooming);

        imageView = (ImageView) findViewById(R.id.imageToZoom);

        // add custom View to draw rectangles
        rectangleIndex = 1;
        zoomingRectangles = new ZoomingRectangles(this, this);
        zoomingLayout = (RelativeLayout) findViewById(R.id.zoomLayout);
        zoomingLayout.addView(zoomingRectangles);
        zoomingRectangles.nextRectangle(rectangleIndex);

        //custom gesture listener to grap zooming gesture
        //zoomListener = new ZoomListener(this);
        zoomData = new ArrayList<Zoom>();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                Log.e("scale", "on scale");
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                Zoom zoom = new Zoom(scaleGestureDetector.getCurrentSpan(), scaleGestureDetector.getCurrentSpanX(), scaleGestureDetector.getCurrentSpanY(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getTimeDelta(), scaleGestureDetector.getEventTime());
                zoomData.add(zoom);
                Log.i("Scale", zoom.toString());

                Log.e("image", "image view " + imageView);

                //TODO fix this prototype!!
                if (imageView.getLayoutParams().width == 482 || imageView.getLayoutParams().height == 782) {
                    //stop scaling, give success message
                    Log.i("Scale", "Scaling was successful");
                    rectangleIndex++;
                    zoomingRectangles.nextRectangle(rectangleIndex);

                } else {
                    imageView.getLayoutParams().width += 20;
                    imageView.getLayoutParams().height += 20;
                    imageView.requestLayout();
                }
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                Log.e("scale", "on scale end");
            }
        });

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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //TODO maybe log start points of zooming
                break;
            case MotionEvent.ACTION_MOVE:
                //scale
                scaleGestureDetector.onTouchEvent(event);
                Log.e("ZoomData", zoomData.toString());
                break;
            case MotionEvent.ACTION_UP:
                //TODO maybe log endpoints for max zooming gesture
                break;
        }



        return true;

    }
}
