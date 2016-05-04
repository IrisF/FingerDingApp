package ifi.lmu.com.handmeasurementstudy.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import ifi.lmu.com.handmeasurementstudy.MainActivity;
import ifi.lmu.com.handmeasurementstudy.system.Tools;
import ifi.lmu.com.handmeasurementstudy.system.TrialSettings;
import ifi.lmu.com.handmeasurementstudy.Tapping;

@SuppressLint("ViewConstructor")
public class TrialDrawingPanel extends AbstractDrawingPanel {

	protected Tapping parent;

	private int drawing_surface_w;
	private int drawing_surface_h;
	private Paint drawing_bg_paint;
	private Rect drawing_bg_rect;

	private Paint drawing_target_paint;
	private Rect drawing_target_rect;
	private float targetX;
	private float targetY;

	private Paint drawing_text_paint;
	private Paint drawing_task_text_paint;
	private Paint drawing_stat_paint;


	public TrialDrawingPanel(Context context, Tapping parent) {
		super(context);
		getHolder().addCallback(this);
		this.parent = parent;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {

		// Drawing stuff pre-allocation:

		this.drawing_surface_w = this.getWidth();
		this.drawing_surface_h = this.getHeight();

		// Background paint:
		this.drawing_bg_paint = new Paint();
		this.drawing_bg_paint.setStrokeWidth(1);
		this.drawing_bg_paint.setStyle(Style.FILL);
		this.drawing_bg_paint.setColor(Color.rgb(255, 255, 255));

		this.drawing_bg_rect = new Rect(0, 0, this.drawing_surface_w,
				this.drawing_surface_h);

		// Target paint:
		this.drawing_target_paint = new Paint();
		this.drawing_target_paint.setStrokeWidth(1);
		this.drawing_target_paint.setStyle(Style.FILL);
		this.drawing_target_paint.setColor(Color.rgb(100, 100, 100));

		// Text paint:
		this.drawing_text_paint = new Paint();
		this.drawing_text_paint.setStrokeWidth(1);
		this.drawing_text_paint.setStyle(Style.FILL);
		this.drawing_text_paint.setColor(Color.rgb(60, 60, 60));
		this.drawing_text_paint.setTextAlign(Align.CENTER);
		this.drawing_text_paint.setTextSize(40);
		this.drawing_text_paint.setAntiAlias(true);

		// Task text paint:
		this.drawing_task_text_paint = new Paint();
		this.drawing_task_text_paint.setStrokeWidth(1);
		this.drawing_task_text_paint.setStyle(Style.FILL);
		this.drawing_task_text_paint.setColor(Color.rgb(60, 60, 60));
		this.drawing_task_text_paint.setTextAlign(Align.CENTER);
		this.drawing_task_text_paint.setTextSize(40);
		this.drawing_task_text_paint.setAntiAlias(true);

		// Stats paint:
		this.drawing_stat_paint = new Paint();
		this.drawing_stat_paint.setStrokeWidth(1);
		this.drawing_stat_paint.setStyle(Style.FILL);
		this.drawing_stat_paint.setColor(Color.rgb(200, 200, 200));
		this.drawing_stat_paint.setTextSize(30);
		this.drawing_stat_paint.setAntiAlias(true);

		if(this.parent.isDoInit()){
			this.parent.initTrial();
		}

		// create button size/shape:
		this.drawing_target_rect = new Rect(0, 0, this.parent
				.getTrialSettings().getButtonWidth(), this.parent
				.getTrialSettings().getButtonHeight());

		setWillNotDraw(false);
		this.thread = new PanelThread(getHolder(), this);
		this.thread.setRunning(true);
		this.thread.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {

		try {
			this.thread.setRunning(false);
			this.thread.join();
		} catch (InterruptedException e) {
		}
	}

	public void onDraw(Canvas canvas) {

		// draw bg:
		canvas.drawRect(this.drawing_bg_rect, this.drawing_bg_paint);

		// draw stats:
		canvas.drawText(this.parent.getTargetCounter() + " / "
				+ this.parent.getTrialSettings().getNumTargets(), 10, 30,
				this.drawing_stat_paint);
		canvas.drawText("task id: " + this.parent.getTrialSettings().getTrialMode(), this.drawing_surface_w - 150, 30,
				this.drawing_stat_paint);

		// draw grid: //TODO: switch off grid for real study
		if (false && !this.parent.isTrialFinished()) {
			float[][] targets = this.parent.getTargetLocationFactory()
					.getTargets();
			for (int i = 0; i < targets.length; i++) {
				canvas.drawRect(targets[i][0] - 3, targets[i][1] - 3,
						targets[i][0] + 3, targets[i][1] + 3,
						this.drawing_stat_paint);
			}
		}

		if (!this.parent.isTrialStarted()) {
			String inputStyleText1 = "";
			String inputStyleText2 = "";
			if (this.parent.getTrialSettings().getInputStyle() == TrialSettings.SETTING_INPUT_STYLE_THUMB) {
				inputStyleText1 = "Please hold the phone in your right hand";
				inputStyleText2 = "and touch with your right thumb.";
			} else {
				inputStyleText1 = "Please hold the phone in your left hand";
				inputStyleText2 = "and touch with your right index finger.";
			}
            String mobilityString = "";
            if (this.parent.getTrialSettings().getMobility() == TrialSettings.SETTING_MOBILITY_SITTING) {
                mobilityString = "SITTING";
            } else {
                mobilityString = "WALKING";
            }

			canvas.drawText(inputStyleText1, this.drawing_surface_w / 2,
					this.drawing_surface_h / 2 - 45,
					this.drawing_task_text_paint);
			canvas.drawText(inputStyleText2, this.drawing_surface_w / 2,
					this.drawing_surface_h / 2, this.drawing_task_text_paint);
            canvas.drawText(mobilityString, this.drawing_surface_w / 2,
                    this.drawing_surface_h / 2 + 45, this.drawing_task_text_paint);
			canvas.drawText("Touch anywhere to start.",
					this.drawing_surface_w / 2,
					this.drawing_surface_h / 2 + 90,
					this.drawing_task_text_paint);
		}

		// draw target:
		if (!this.parent.isTrialFinished() && this.parent.isTrialStarted()) {

			canvas.save();

			// rectangle target:
			if (this.parent.getTrialSettings().getButtonWidth() > 0) {
				this.drawing_target_paint.setStrokeWidth(1);
				canvas.translate(this.targetX
						- this.parent.getTrialSettings().getButtonWidth() / 2f,
						this.targetY
								- this.parent.getTrialSettings()
										.getButtonHeight() / 2f);
				canvas.drawRect(this.drawing_target_rect,
						this.drawing_target_paint);
			}
			// crosshair target:
			else {
				this.drawing_target_paint.setStrokeWidth(5);
				canvas.translate(this.targetX, this.targetY);
				float ch_size = Tools.mmToPx(2, true);
				canvas.drawLine(-ch_size / 2, 0, ch_size / 2, 0,
						this.drawing_target_paint);
				canvas.drawLine(0, -ch_size / 2, 0, ch_size / 2,
						this.drawing_target_paint);
			}
			canvas.restore();
		} else if (this.parent.isTrialFinished()) {
			canvas.drawText("Done! Thank you! :)", this.drawing_surface_w / 2,
					this.drawing_surface_h / 2, this.drawing_text_paint);

			if (this.parent.isAutoAdvance() && ! MainActivity.taskScheduler.isDone()) {
				canvas.drawText("Touch anywhere to advance.",
						this.drawing_surface_w / 2,
						this.drawing_surface_h / 2 + 30,
						this.drawing_task_text_paint);
			}
		}

	}

	public boolean onTouchEvent(MotionEvent event) {

		this.parent.onTouch(this, event);

		// if (event.getAction() == MotionEvent.ACTION_MOVE) {
		// float x = event.getX() / this.getWidth();
		// float y = event.getY() / this.getHeight();
		// this.parent.onDrawingSurfaceMoved(x, y);
		// } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// this.parent.onDrawingSurfaceDown();
		// } else if (event.getAction() == MotionEvent.ACTION_UP) {
		// this.parent.onDrawingSurfaceUp();
		// }
		return true;
	}

	public void setNewTargetLocation(float x, float y) {

		this.targetX = x;
		this.targetY = y;
	}

}
