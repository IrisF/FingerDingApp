package ifi.lmu.com.handmeasurementstudy.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import ifi.lmu.com.handmeasurementstudy.Tapping;
import ifi.lmu.com.handmeasurementstudy.system.Tools;

/**
 * Created by Jonny on 10.05.2016.
 */
public class Drawing extends AbstractDrawingPanel { //View {

    private Tapping parent;

    private int nBackgroundW;
    private int nBackgroundH;

    private float targetX;
    private float targetY;
    private float fRelativeTargetX;
    private float fRelativeTargetY;

    private Paint paintTarget = new Paint();
    private Paint paintBackground = new Paint();
    private Rect rectBackground;


    public Drawing(Context context, Tapping parent) {
        super(context);
        this.parent = parent;
        getHolder().addCallback(this);

        // Background paint:
        this.paintBackground = new Paint();
        this.paintBackground.setStrokeWidth(1);
        this.paintBackground.setStyle(Paint.Style.FILL);
        this.paintBackground.setColor(Color.rgb(255, 255, 255));

        this.rectBackground = new Rect(0, 0, this.nBackgroundW,
                this.nBackgroundH);

    }


    @Override
    public void onDraw(Canvas canvas) {

        if(targetX == 0) {
            setNewRelativeTargetLocation(0,0);
            calculateAbsoluteTarget();
        }
        //calculateAbsoluteTarget();

        // draw bg:
        canvas.drawRect(this.rectBackground, this.paintBackground);


        paintTarget.setStrokeWidth(5);
        canvas.translate(this.targetX, this.targetY);
        float ch_size = Tools.mmToPx(2, true);
        canvas.drawLine(-ch_size / 2, 0, ch_size / 2, 0,
                paintTarget);
        canvas.drawLine(0, -ch_size / 2, 0, ch_size / 2,
                paintTarget);


    }

    private void calculateAbsoluteTarget() {
        targetX = (nBackgroundW / Tapping.n_TARGET_WIDTH) * (fRelativeTargetX + 0.5f);
        targetY = (nBackgroundH / Tapping.n_TARGET_HEIGHT) * (fRelativeTargetY + 0.5f);
    }

    public void setNewTargetLocation(float x, float y) {

        this.targetX = x;
        this.targetY = y;
    }

    public void setNewRelativeTargetLocation(float i_fX, float i_fY) {

        fRelativeTargetX = i_fX;
        fRelativeTargetY = i_fY;
    }


    public int getViewWidth () {
        return nBackgroundW;
    }

    public int getViewHeight () {
        return nBackgroundH;
    }

    public float getTargetWidth () { return targetX; }

    public float getTargetHeight () {
        return targetY;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        nBackgroundW = this.getWidth();
        nBackgroundH = this.getHeight();
        parent.setBackgroundSize(nBackgroundW, nBackgroundH);
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
}
