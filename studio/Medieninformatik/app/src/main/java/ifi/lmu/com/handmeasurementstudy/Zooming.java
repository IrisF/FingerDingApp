package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
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


public class Zooming extends ActionBarActivity implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;
    private ImageView image;
    private boolean finishedSuccessfully = false;

    private ZoomingView zoomingView;
    private Paint rectanglePaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //rectangle view
        //zoomingView = new ZoomingView(this);
        //setContentView(zoomingView);

        setContentView(R.layout.activity_zooming);
        rectanglePaint = new Paint();

        image = (ImageView) findViewById(R.id.imageToZoom);
        Log.e("e", "hier das image" + image);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
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

    public void onDraw(Canvas canvas) {
        //int viewWidth = this.getWidth();
        //int viewHeight = this.getHeight();

        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.RED);

        canvas.drawRect(10, 10, 120-10, 120-10, rectanglePaint);

    }

    /**
     * @class manages the scaling gesture
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
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

            image.getLayoutParams().width += 20;
            image.getLayoutParams().height += 20;
            image.requestLayout();

            //if width >= rectWidth || height >= rectWidth
            //stop scaling, give success message

            Log.e("Scale", "Time Delta " + Float.toString(scaleGestureDetector.getTimeDelta()));
            Log.e("Scale", "Event Time " + Long.toString(scaleGestureDetector.getEventTime()));

            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            Log.e("e", "On Scale End");
        }

    }

}
