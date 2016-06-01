package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;


public class Zooming extends ActionBarActivity implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;
    private ArrayList<Zoom> zoomData;

    private ZoomingRectangles zoomingRectangles;
    private RelativeLayout zoomingLayout;

    private long startTimeSeconds;
    private long endTimeSeconds;

    private boolean rectangleZoomingStarted;
    private boolean rectangleIsZoomed;
    private boolean taskOver;

    //case 1
    public int heightBig, widthBig;
    //case 2
    public int heightMedium, widthMedium;
    //case 3
    public int heightSmall, widthSmall;

    private int userID;
    private static final int[][] latinSquare = {
            {1, 2, 3},
            {3, 1, 2},
            {2, 3, 1},
    };

    private int[] zoomLatinRow;
    private ImageView image;
    private boolean finishedSuccessfully = false;

    private float startX, startY;

    private ImageView imageView;
    private Button startButton;

    private int counter;
    public int rectangleIndex;

    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zooming);

        //get User ID from Intent
        Intent mIntent = getIntent();
        userID = mIntent.getIntExtra("id", 0);

        // get current latin row
        //zoomLatinRow = latinSquare[ userID % latinSquare.length ];
        zoomLatinRow = latinSquare[ 2 ];

        // add custom View to draw rectangles
        zoomingRectangles = new ZoomingRectangles(this, this);
        zoomingLayout = (RelativeLayout) findViewById(R.id.zoomLayout);
        zoomingLayout.addView(zoomingRectangles);

        imageView = (ImageView) findViewById(R.id.imageToZoom);

        startButton = (Button) findViewById(R.id.startZoomingTask);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.zoomingDescr);
                text.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);

                imageView.setVisibility(View.VISIBLE);

                widthBig = zoomingRectangles.getMeasuredWidth();
                heightBig = zoomingRectangles.getMeasuredHeight();

                widthMedium = zoomingRectangles.getMeasuredWidth() / 2;
                heightMedium = zoomingRectangles.getMeasuredHeight() / 2;

                widthSmall = zoomingRectangles.getMeasuredWidth() / 3;
                heightSmall = zoomingRectangles.getMeasuredHeight() / 3;

                counter = 0;
                rectangleIndex = zoomLatinRow[counter];

                initImageDimensions();
                rectangleIsZoomed = false;
                rectangleZoomingStarted = false;
                zoomingRectangles.invalidate();

                taskOver = false;
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
                Zoom zoom = new Zoom(scaleGestureDetector.getCurrentSpan(), scaleGestureDetector.getCurrentSpanX(), scaleGestureDetector.getCurrentSpanY(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getTimeDelta(), scaleGestureDetector.getEventTime(), sensorHelper.getAcceleromterData(), sensorHelper.getGravitiyData(), sensorHelper.getGyroscopeData(), rectangleIndex);
                zoomData.add(zoom);
                Log.i("Scale", zoom.toString());

                int imageViewWidth = imageView.getLayoutParams().width;
                int imageViewHeight = imageView.getLayoutParams().height;

                //check which rectangle is currently on screen
                int rectangleWidth, rectangleHeight;
                switch (rectangleIndex) {
                    case 1:
                        rectangleWidth = widthBig;
                        rectangleHeight = heightBig;
                        break;
                    case 2:
                        rectangleWidth = widthMedium;
                        rectangleHeight = heightMedium;
                        break;
                    case 3:
                        rectangleWidth = widthSmall;
                        rectangleHeight = heightSmall;
                        break;
                    default:
                        rectangleHeight = 0;
                        rectangleWidth = 0;
                        break;
                }

                if (imageViewHeight >= rectangleHeight && imageViewWidth >= rectangleWidth) {
                    counter++;
                    rectangleIsZoomed = true;
                    Log.i("zoom", "image reached, image dimensions: " + imageViewHeight + imageViewWidth + " rectangle dimensions: " + rectangleHeight + rectangleWidth + "index " + rectangleIndex);

                    if(counter < zoomLatinRow.length) {
                        //a next rectangle exist, so draw it
                        rectangleIndex = zoomLatinRow[counter];
                        initImageDimensions();
                        zoomingRectangles.invalidate();
                    } else {
                        taskOver = true;
                    }

                } else {
                    imageView.getLayoutParams().width += 5;
                    imageView.getLayoutParams().height += 5;
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
        if(item.getItemId()==R.id.restart){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
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
                break;
            case MotionEvent.ACTION_UP:
                //log end points
                if(rectangleIsZoomed) {
                    endTimeSeconds = System.currentTimeMillis();
//TODO save end Points?!
                    Log.i("Scale", "End Points x " + event.getX() + " y " + event.getY() + " time " + endTimeSeconds);
                    rectangleIsZoomed = false;
                    rectangleZoomingStarted = false;
                }

                if(taskOver){
                    finish();
                }

                break;
        }
        return true;
    }

    //initialize imageView with the help of device orientation and aspect ratio
    private void initImageDimensions() {
        //this is the smartphone test application, so orientation is portrait and width < height
        imageView.getLayoutParams().height = heightBig/8;
        imageView.getLayoutParams().width = widthBig/8;
    }

    @Override
    public void finish () {
        ActivityManager.SaveResultsInDatabase((Object[]) zoomData.toArray());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("isFinished",true);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}
