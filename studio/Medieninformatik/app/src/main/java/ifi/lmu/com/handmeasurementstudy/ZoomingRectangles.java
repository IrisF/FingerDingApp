package ifi.lmu.com.handmeasurementstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ifi.lmu.com.handmeasurementstudy.system.Zoom;

/**
 * Created by Sarah on 09.05.2016.
 * @class draw rectangles for zooming task
 */
public class ZoomingRectangles extends View {

    private Paint rectanglePaint;
    private ImageView image;
    private LinearLayout layout;

    private Zooming zoomingActivity;

    public ZoomingRectangles(Context context, Zooming parentActivity) {
        super(context);

        this.zoomingActivity = parentActivity;

        rectanglePaint = new Paint();

        /*
        layout = new LinearLayout(context);

        image = new ImageView(context);
        image.setImageResource(R.drawable.hand);
        layout.addView(image);*/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //default is biggest rectangle
        int width =  0;
        int height = 0;
        switch(zoomingActivity.rectangleIndex) {
            case 1:
                width = zoomingActivity.widthBig;
                height = zoomingActivity.heightBig;
                break;
            case 2:
                width = zoomingActivity.widthMedium;
                height = zoomingActivity.heightMedium;
                break;
            case 3:
                width = zoomingActivity.widthSmall;
                height = zoomingActivity.heightSmall;
                break;
        }

        Log.e("rect", "######################## w " + width + "######################### h " + height);

        //TODO draw three different rectangles, latin square?!
        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.RED);

        //coordinates of top left corner in such a way the rectangle is drawn in center
        int x = this.getWidth()/2 - width/2;
        int y = this.getHeight()/2 - height/2;

        if(width != 0 && height != 0) {
            canvas.drawRect(x, y, width, height, rectanglePaint);
            canvas.save();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
