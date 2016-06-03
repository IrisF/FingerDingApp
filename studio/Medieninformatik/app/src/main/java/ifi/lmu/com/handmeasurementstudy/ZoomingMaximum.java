package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.db.DBHandler;
import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;


public class ZoomingMaximum extends ActionBarActivity implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;
    private ArrayList<Zoom> zoomData;

    private int userID;
    private boolean isZoomed;
    private boolean rectangleZoomingStarted;

    private ImageView imageView;
    private Button startButton;

    private float startX, startY;
    private long startTimeSeconds;
    private long endTimeSeconds;

    private SensorHelper sensorHelper;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zooming_maximum);

        //grap other sensor events
        sensorHelper = new SensorHelper(this);

        //db Handler
        dbHandler = DBHandler.getInstance(this);

        //get User ID from Intent
        Intent mIntent = getIntent();
        userID = mIntent.getIntExtra("id", 0);

        imageView = (ImageView) findViewById(R.id.imageToZoomMaximum);

        startButton = (Button) findViewById(R.id.startZoomingMaximumTask);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.zoomingMaximumDescr);
                text.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);

                imageView.setVisibility(View.VISIBLE);

                isZoomed = false;
                rectangleZoomingStarted = false;

                initImageDimensions();
            }
        });

        //custom gesture listener to grap zooming gesture
        zoomData = new ArrayList<Zoom>();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                Log.e("scale", "on scale");
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                if(!rectangleZoomingStarted) { //log start point here to know that scaling really begun
                    rectangleZoomingStarted = true;
//TODO save start point?!
                    Log.i("Scale", "Start Points x " + startX + " y " + startY + " time " + startTimeSeconds);
                }

                Zoom zoom = new Zoom(scaleGestureDetector.getCurrentSpan(),
                        scaleGestureDetector.getCurrentSpanX(), scaleGestureDetector.getCurrentSpanY(),
                        scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(),
                        scaleGestureDetector.getScaleFactor(),
                        scaleGestureDetector.getTimeDelta(),
                        scaleGestureDetector.getEventTime(),
                        sensorHelper.getAcceleromterData()[0], sensorHelper.getAcceleromterData()[1],  sensorHelper.getAcceleromterData()[2],
                        sensorHelper.getGravitiyData()[0], sensorHelper.getGravitiyData()[1],sensorHelper.getGravitiyData()[2],
                        sensorHelper.getGyroscopeData()[0],sensorHelper.getGyroscopeData()[1], sensorHelper.getGyroscopeData()[2],
                        sensorHelper.getOrientationData()[0],sensorHelper.getOrientationData()[1], sensorHelper.getOrientationData()[2],
                        sensorHelper.getRotationData()[0],sensorHelper.getRotationData()[1], sensorHelper.getRotationData()[2],
                        -1);
                zoomData.add(zoom);
                Log.i("Scale", zoom.toString());

                if (!isZoomed) {
                    //this is maximum scaling task, so just scale image
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


    //TODO stop time and log start & end points
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //log start points
                if(!rectangleZoomingStarted) {
                    startTimeSeconds = System.currentTimeMillis();
                    startX = event.getX();
                    startY = event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //scale
                scaleGestureDetector.onTouchEvent(event);
                Log.e("ZoomData", zoomData.toString());
                break;
            case MotionEvent.ACTION_UP:
                //log end points
                if (rectangleZoomingStarted) {
                    endTimeSeconds = System.currentTimeMillis();
                    Log.i("Scale", "End Points x " + event.getX() + " y " + event.getY() + " time " + endTimeSeconds);
                    rectangleZoomingStarted = false;

                    finish();
                }

                break;
        }
        return true;
    }

    //initialize imageView with the help of device orientation and aspect ratio
    private void initImageDimensions() {
        //getting screen size from this Activity
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //this is the smartphone test application, so orientation is portrait and width < height
        imageView.getLayoutParams().height = height/8;
        imageView.getLayoutParams().width = width/8 * (9/16);
    }

    @Override
    public void finish () {
        if(storeResultsToDB(zoomData.toArray())){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isFinished",true);
            setResult(Activity.RESULT_OK,returnIntent);
            super.finish();
        } else {
//TODO what to do on error?!
        }

    }

    //TODO save results to DB
    private boolean storeResultsToDB (Object[] results) {
        for(int i = 0; i<results.length; i++){
            dbHandler.insertZoom((Zoom) results[i]);
        }
        return true;
    }
}
