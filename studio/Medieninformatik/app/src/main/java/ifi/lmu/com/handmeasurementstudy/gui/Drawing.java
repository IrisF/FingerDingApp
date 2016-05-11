package ifi.lmu.com.handmeasurementstudy.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import ifi.lmu.com.handmeasurementstudy.Tapping;
import ifi.lmu.com.handmeasurementstudy.system.Tools;

/**
 * Created by Jonny on 10.05.2016.
 */
public class Drawing extends AbstractDrawingPanel implements View.OnTouchListener { //View {

    private Tapping parent;

    private int nBackgroundW;
    private int nBackgroundH;

    private float targetX;
    private float targetY;
    private float fRelativeTargetX;
    private float fRelativeTargetY;

    private Paint paintTarget = new Paint();
    private Paint paintBackground = new Paint();


    public Drawing(Context context, Tapping parent) {
        super(context);
        this.parent = parent;
        getHolder().addCallback(this);
        nBackgroundW = this.getWidth();
        nBackgroundH = this.getHeight();
    }


    @Override
    public void onDraw(Canvas canvas) {

        Log.d("Drawing","onDraw");


        calculateAbsoluteTarget();


        paintTarget.setStrokeWidth(5);
        canvas.translate(this.targetX, this.targetY);
        float ch_size = Tools.mmToPx(2, true);
        canvas.drawLine(-ch_size / 2, 0, ch_size / 2, 0,
                paintTarget);
        canvas.drawLine(0, -ch_size / 2, 0, ch_size / 2,
                paintTarget);


    }

    private void calculateAbsoluteTarget() {
        targetX = (nBackgroundW / Tapping.nSideLength) * (fRelativeTargetX +1);
        targetY = (nBackgroundH / Tapping.nSideLength) * (fRelativeTargetY +1);
    }

    public void setNewTargetLocation(float x, float y) {

        this.targetX = x;
        this.targetY = y;
    }

    public void setNewRelativeTargetLocation(float i_fX, float i_fY) {

        fRelativeTargetX = i_fX;
        fRelativeTargetY = i_fY;
    }


    public float getViewWidth () {
        return nBackgroundW;
    }

    public float getViewHeight () {
        return nBackgroundH;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Drawing","surfaceCreated");
        nBackgroundW = this.getWidth();
        nBackgroundH = this.getHeight();
        paintTarget.setColor(Color.BLACK);
        paintTarget.setStrokeWidth(1);
        paintTarget.setStyle(Paint.Style.FILL);
        paintTarget.setColor(Color.rgb(100, 100, 100));

        // Background paint:
        paintBackground.setStrokeWidth(1);
        paintBackground.setStyle(Paint.Style.FILL);
        paintBackground.setColor(Color.rgb(255, 255, 255));
        new Rect(0, 0, nBackgroundW, nBackgroundH);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("Drawing", "onTouch");
        parent.onTouch(v, event);
        return false;
    }
}
