package ifi.lmu.com.handmeasurementstudy;



import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.RelativeLayout;
import ifi.lmu.com.handmeasurementstudy.db.DBHandler;
import ifi.lmu.com.handmeasurementstudy.gui.TrialDrawingPanel;
import ifi.lmu.com.handmeasurementstudy.system.LocationFactory;
import ifi.lmu.com.handmeasurementstudy.system.Tap;
import ifi.lmu.com.handmeasurementstudy.system.TrialSettings;
import ifi.lmu.com.handmeasurementstudy.system.TrialSettingsFactory;


public class Tapping extends Activity implements View.OnTouchListener {

    protected TrialDrawingPanel drawingPanel;

    protected LocationFactory locationFactory;

    protected TrialSettings trialSettings;

    protected int targetCounter;

    private String subjectName = "n/a";

    private float lastDownX;
    private float lastDownY;

    private float lastDownPressure;
    private float lastDownSize;

    private float lastDownMinor;
    private float lastDownMajor;

    private DBHandler dbHandler;

    private float targetX;
    private float targetY;

    private int trialMode;

    private int sessionIndex;

    private int trialDBID = -1;

    private long lastTimeDown;
    private long trialStartTime;

    private boolean trialStarted;
    private boolean trialFinished;
    private boolean hasSeenFinishedMessage;

    private boolean autoAdvance;

    private int backBlockCounter;

    private boolean doInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapping);
        // Remove title bar:
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Setup view:
        setContentView(R.layout.activity_tapping);

        this.doInit = true;
        this.drawingPanel = new TrialDrawingPanel(this, this);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_container);
        mainLayout.addView(this.drawingPanel);

        // Get/create db handler:
        this.dbHandler = DBHandler.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tapping, menu);
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

    public void initTrial() {

        Intent intent = this.getIntent();

        this.subjectName = intent
                .getStringExtra(MainActivity.EXTRA_SUBJECT_NAME);

        this.trialMode = intent.getIntExtra(MainActivity.EXTRA_TRIAL_MODE, -1);
        if (this.trialMode == MainActivity.TRIAL_NUM_AUTO) {
            this.autoAdvance = true;
            this.trialMode = MainActivity.taskScheduler.getCurrentTrial();
        }

        this.sessionIndex = intent.getIntExtra(
                MainActivity.EXTRA_SESSION_INDEX, -1);

        // Init trial stuff:
        this.trialSettings = TrialSettingsFactory
                .createTrialSettingsByTrialID(this.trialMode);

        if (this.trialSettings.getButtonWidth() == -1) {
            this.trialSettings.setButtonWidth(this.drawingPanel.getWidth());
        }

        Display display = getWindowManager().getDefaultDisplay();
        @SuppressWarnings("deprecation")
        int screenW = display.getWidth(); // deprecated
        @SuppressWarnings("deprecation")
        int screenH = display.getHeight(); // deprecated
        // Point size = new Point();
        // display.getSize(size);
        // int screenW = size.x;
        // int screenH = size.y;
        this.trialDBID = this.dbHandler.insertTrial(this.subjectName,
                MainActivity.taskScheduler.getUserID(), this.trialSettings,
                this.sessionIndex, this.drawingPanel.getLeft(),
                this.drawingPanel.getTop(), this.drawingPanel.getWidth(),
                this.drawingPanel.getHeight(), screenW, screenH);

        // locations:
        float minX;
        float minY;
        if (this.trialSettings.getButtonWidth() == 0) {
            minX = 40;
            minY = minX;
        } else {
            minX = this.trialSettings.getButtonWidth() / 2f;
            minY = this.trialSettings.getButtonHeight() / 2f;
        }
        this.locationFactory = new LocationFactory(minX, minY,
                this.drawingPanel.getWidth() - minX,
                this.drawingPanel.getHeight() - minY,
                this.trialSettings.getGridStepsX(),
                this.trialSettings.getGridStepsY());

        this.targetCounter = 0;
        this.trialStarted = false;
        this.trialFinished = false;

        this.doInit = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        float globalX = v.getLeft() + rawX;
        float globalY = v.getTop() + rawY;

        // down on panel:
        if (v == this.drawingPanel
                && event.getAction() == MotionEvent.ACTION_DOWN
                && !this.trialFinished && this.trialStarted) {
            Log.d("DEBUG", "touch down panel at: " + x + ", " + y
                    + " (global: " + globalX + ", " + globalY + ")");
            this.lastDownX = x;// globalX;
            this.lastDownY = y;// globalY;
            this.lastDownPressure = event.getPressure();
            this.lastDownSize = event.getSize();
            this.lastDownMinor = event.getTouchMinor();
            this.lastDownMajor = event.getTouchMajor();
            this.lastTimeDown = System.currentTimeMillis()
                    - this.trialStartTime;
        }
        // up on panel:
        else if (v == this.drawingPanel
                && event.getAction() == MotionEvent.ACTION_UP
                && !this.trialFinished && this.trialStarted) {
            Log.d("DEBUG", "touch up panel at: " + x + ", " + y + " (global: "
                    + globalX + ", " + globalY + ")");

            this.commitTap(x, y, event.getPressure(), event.getSize(),
                    event.getTouchMinor(), event.getTouchMajor());
            this.nextTarget();
            this.backBlockCounter = 0; //reset back button block counter
        }
        // start trial:
        else if (event.getAction() == MotionEvent.ACTION_UP
                && !this.trialStarted) {
            this.trialStarted = true;
            this.trialStartTime = System.currentTimeMillis();
            this.nextTarget();
        }
        // advance or quit:
        if (event.getAction() == MotionEvent.ACTION_UP && this.trialFinished) {
            // If auto-advance, start next trial:
            if (this.hasSeenFinishedMessage && this.autoAdvance
                    && !MainActivity.taskScheduler.isDone()) {
                MainActivity.taskScheduler.advance();// advance
                Intent intent = new Intent(this, Tapping.class);
                intent.putExtra(MainActivity.EXTRA_SESSION_INDEX, sessionIndex);
                intent.putExtra(MainActivity.EXTRA_TRIAL_MODE, MainActivity.TRIAL_NUM_AUTO);// 9 = auto
                // advance
                intent.putExtra(MainActivity.EXTRA_SUBJECT_NAME,
                        this.subjectName);
                startActivity(intent);
                finish();
            }
            this.hasSeenFinishedMessage = true;
        }

        return false;
    }

    private void commitTap(float upX, float upY,
                           float upPressure, float upSize,
                           float upMinor, float upMajor) {

        int hit = 0;
        if (upX > this.targetX - this.trialSettings.getButtonWidth() / 2f
                && upX < this.targetX + this.trialSettings.getButtonWidth()
                / 2f
                && upY > this.targetY - this.trialSettings.getButtonHeight()
                / 2f
                && upY < this.targetY + this.trialSettings.getButtonHeight()
                / 2f) {
            hit = 1;
        }
        Tap tap = new Tap(this.lastDownX, this.lastDownY, upX, upY,
                this.targetX, this.targetY, hit, this.lastTimeDown,
                System.currentTimeMillis() - this.trialStartTime,
                this.lastDownPressure, this.lastDownSize, upPressure, upSize,
                this.lastDownMinor, this.lastDownMajor, upMinor, upMajor);
        this.dbHandler.insertTap(tap, this.trialSettings, this.trialDBID);
    }

    private void nextTarget() {

        // if trial finished:
        if (this.targetCounter == this.trialSettings.getNumTargets()) {
            this.trialFinished = true;
        }
        // else set next target:
        else {
            this.targetCounter++;
            float[] xy = this.locationFactory.nextTarget();
            this.targetX = xy[0];
            this.targetY = xy[1];
            this.drawingPanel.setNewTargetLocation(this.targetX, this.targetY);
        }
    }

    public TrialSettings getTrialSettings() {
        return this.trialSettings;
    }

    public boolean isTrialFinished() {
        return this.trialFinished;
    }

    public int getTargetCounter() {
        return this.targetCounter;
    }

    public LocationFactory getTargetLocationFactory() {
        return this.locationFactory;
    }

    public boolean isTrialStarted() {
        return this.trialStarted;
    }

    public boolean isAutoAdvance() {
        return this.autoAdvance;
    }

    @Override
    public void onBackPressed() {

        this.backBlockCounter++;
        if(this.backBlockCounter > 5){
            this.backBlockCounter = 0;
            super.onBackPressed();
        }
    }



    public boolean isDoInit() {
        return doInit;
    }

}
