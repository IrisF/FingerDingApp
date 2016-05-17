package ifi.lmu.com.handmeasurementstudy.system;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import ifi.lmu.com.handmeasurementstudy.R;

/**
 * Created by Sarah on 15.05.2016.
 * Listens to scaling Gesture for zooming task
 */
public class ZoomListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ImageView image;

    public ZoomListener(Context context) {
        this.image = (ImageView) ((Activity)context).findViewById(R.id.imageToZoom);;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        Log.e("e", "On Scale");
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Zoom zoom = new Zoom(scaleGestureDetector.getCurrentSpan(), scaleGestureDetector.getCurrentSpanX(), scaleGestureDetector.getCurrentSpanY(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getTimeDelta(), scaleGestureDetector.getEventTime());
        Log.i("Scale", zoom.toString());

        image.getLayoutParams().width += 20;
        image.getLayoutParams().height += 20;
        image.requestLayout();

        //if width >= rectWidth || height >= rectWidth
        //stop scaling, give success message

        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.e("e", "On Scale End");
    }

}