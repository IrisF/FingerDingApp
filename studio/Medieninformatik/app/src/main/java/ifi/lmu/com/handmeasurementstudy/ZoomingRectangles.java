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

/**
 * Created by Sarah on 09.05.2016.
 * @class draw rectangles for zooming task
 */
public class ZoomingRectangles extends View {

    private Paint rectanglePaint;
    private ImageView image;
    private LinearLayout layout;

    private int heightSmall, widthSmall;
    private int heightMedium, widthMedium;
    private int heightBig, widthBig;

    //TODO ist das richtig?!
    private static final int[][] latinSquare = {
            {1, 3, 2, 4},
            {2, 4, 1, 3},
            {4, 3, 2, 1},
            {1, 3, 2, 4},
            {4, 1, 3, 2},
            {3, 2, 4, 1}
    };

    public ZoomingRectangles(Context context) {
        super(context);

        rectanglePaint = new Paint();

        layout = new LinearLayout(context);

        image = new ImageView(context);
        image.setImageResource(R.drawable.hand);
        layout.addView(image);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int squareIndex = 1;
        //default is biggest rectangle
        int width =  this.getWidth() - 10;
        int height = this.getHeight() - 10;
        switch(squareIndex) {
            case 1:
                width = this.getWidth() - 10;
                height = this.getHeight() - 10;
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }

        //TODO draw three different rectangles, latin square?!
        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.RED);

        canvas.drawRect(10, 10, width, height, rectanglePaint);
        canvas.save();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
