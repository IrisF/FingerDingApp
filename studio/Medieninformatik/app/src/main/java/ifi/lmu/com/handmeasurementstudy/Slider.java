package ifi.lmu.com.handmeasurementstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Iris on 11.05.2016.
 */
public class Slider extends View {

    Paint paint = new Paint();
    WindowManager wm;
    Display display;
    Rect r;
    Rect r2;
    int xMax=1000;
    int currentYPos = 10;
    int currentX=0;

    public Slider(Context context) {
        super(context);

        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();

        r = new Rect(10, 10, 1000, 100);
        r2 = new Rect (10, 10, 0, 100);

    }

    public void setNewX(int x){
        if(currentX<=x) {
            if (x <= xMax) {
                r2.set(10, currentYPos, x, 100);
                this.invalidate();
            } else {
                currentYPos += 50;
                r2.set(10, 100, 0, 100);
                r.set(10, 100, 1000, 100);
                this.invalidate();
            }
            currentX=x;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.MAGENTA);
        canvas.drawRect(r, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(r2, paint);

    }
}
