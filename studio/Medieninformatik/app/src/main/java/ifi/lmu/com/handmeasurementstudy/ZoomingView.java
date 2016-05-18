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

//TODO after next meeting: delete this or grap elements if needed!!

/**
 * Created by Sarah on 09.05.2016.
 *
public class ZoomingView extends View implements View.OnTouchListener{

    private Paint rectanglePaint;
    private ImageView image;
    private LinearLayout layout;

    public ZoomingView(Context context) {
        super(context);

        rectanglePaint = new Paint();

        layout = new LinearLayout(context);

        image = new ImageView(context);
        image.setImageResource(R.drawable.hand);
        layout.addView(image);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = this.getWidth();
        int viewHeight = this.getHeight();

        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.RED);

        canvas.drawRect(10, 10, viewWidth-10, viewHeight-10, rectanglePaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hand);
        int bitmapWidth = bitmap.getWidth()/3;
        int bitmapHeight = bitmap.getWidth()/3;

        Bitmap smallerB = Bitmap.createBitmap(bitmap, 0,0, bitmapWidth, bitmapHeight);
        canvas.drawBitmap(smallerB, 120, 120, rectanglePaint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.e("View", view.toString() + motionEvent.toString());
        return false;
    }
}
*/