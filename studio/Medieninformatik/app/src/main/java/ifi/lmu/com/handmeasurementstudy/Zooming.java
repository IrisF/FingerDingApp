package ifi.lmu.com.handmeasurementstudy;

import android.content.Intent;
import android.content.res.Configuration;
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

import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;


public class Zooming extends ActionBarActivity implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;
    private ArrayList<Zoom> zoomData;

    private ZoomingRectangles zoomingRectangles;
    private RelativeLayout zoomingLayout;

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
        //userID = 5;

        // get current latin row
        zoomLatinRow = latinSquare[ userID % latinSquare.length ];

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

                Log.e("sizes", "here zooming Rectangle size w " + widthBig + " h " + heightBig);

                widthMedium = zoomingRectangles.getMeasuredWidth() / 2;
                heightMedium = zoomingRectangles.getMeasuredHeight() / 2;

                widthSmall = zoomingRectangles.getMeasuredWidth() / 3;
                heightSmall = zoomingRectangles.getMeasuredHeight() / 3;

                counter = 0;
                rectangleIndex = zoomLatinRow[counter];

                initImageDimensions();
                zoomingRectangles.invalidate();
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
                Zoom zoom = new Zoom(scaleGestureDetector.getCurrentSpan(), scaleGestureDetector.getCurrentSpanX(), scaleGestureDetector.getCurrentSpanY(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getTimeDelta(), scaleGestureDetector.getEventTime());
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

                if (imageViewHeight >= rectangleHeight || imageViewWidth >= rectangleWidth) {
                    //stop scaling, give success message
                    counter++;
                    if(counter >= zoomLatinRow.length) {
                        Log.i("End", "zooming is over !!!!!!!!!!!!");
                        //TODO activity end
                    } else {
                        rectangleIndex = zoomLatinRow[counter];
                        Log.i("Scale", "Scaling was successful, index is now " + rectangleIndex);

                        initImageDimensions();
                        zoomingRectangles.invalidate();
                    }

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


    //TODO stop time and log start & end points
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //log start points
                Log.i("Scale", "Start Points x " + event.getX() + " y " + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                //scale
                scaleGestureDetector.onTouchEvent(event);
                Log.e("ZoomData", zoomData.toString());
                break;
            case MotionEvent.ACTION_UP:
                //end here for maximum zooming gesture
                if(rectangleIndex == 4) {
                    Log.i("Scale", "End Points x " + event.getX() + " y " + event.getY());
                }
                break;
        }
        return true;
    }

    //initialize imageView with the help of device orientation and aspect ratio
    private void initImageDimensions() {
        //this is the smartphone test application, so orientation is portrait and width < height
        imageView.getLayoutParams().height = heightBig/8;
        imageView.getLayoutParams().width = widthBig/8 * (9/16);


        /*
        double aspectRatio;
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                aspectRatio = widthBig / heightBig;
                Log.i("ratio", Double.toString(aspectRatio));
                //width is biggest size
                imageView.getLayoutParams().width = widthBig / 8;
                imageView.getLayoutParams().height = (int) Math.round(imageView.getLayoutParams().width / (16/9));
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                aspectRatio = zoomingRectangles.getMeasuredWidth() / zoomingRectangles.getMeasuredHeight();
                Log.i("ratio", Double.toString(aspectRatio));
                //height is biggest size
                imageView.getLayoutParams().height = zoomingRectangles.getMeasuredHeight() / 8;
                imageView.getLayoutParams().width = (int) Math.round((zoomingRectangles.getMeasuredHeight() / 8) * (9/16));
                break;
            case Configuration.ORIENTATION_UNDEFINED:
                //assume that it's portrait on smartphone
                aspectRatio = zoomingRectangles.getMeasuredWidth() / zoomingRectangles.getMeasuredHeight();
                Log.i("ratio", Double.toString(aspectRatio));
                imageView.getLayoutParams().height = zoomingRectangles.getMeasuredHeight() / 8;
                imageView.getLayoutParams().width = (int) Math.round((zoomingRectangles.getMeasuredHeight() / 8) * (9/16));
                break;
        }*/

    }
}
