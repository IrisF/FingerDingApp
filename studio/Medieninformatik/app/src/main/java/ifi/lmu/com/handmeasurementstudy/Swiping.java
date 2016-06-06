package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;


public class Swiping extends ActionBarActivity {

    private SeekBar seekBar;
    private ArrayList<String> posList = new ArrayList();
    private boolean finishedSuccessfully = false;

    private int nTargetCounter = 0;

    private TextView textView;
    private Button button;
    private boolean taskStarted =false;
    private int userId;
    private int[] currentRow;
    private ArrayList<Swipe> loggedSwipes;
    private SensorHelper sensorHelper;
    private int swipeID = 1;

    private static final int[][] latinSquare = {
            {1, 2, 3, 4},
            {2, 1, 4, 3},
            {3, 4, 1, 2},
            {4, 3, 2, 1}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping);
        sensorHelper = new SensorHelper(this);
        //Bundle extras = getIntent().getExtras();
        loggedSwipes = new ArrayList<>();
        Intent intent = getIntent();
        userId = intent.getIntExtra("id", 0);
        setCurrentRow();
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.swipingDescr);
        button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });
        
        showSlider();
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x =0;
                float y =0;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x =  event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        x = event.getX();
                        y = event.getY();
                        break;
                }
                createSwipeObject(x,y);
                return false;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(taskStarted){
                    seekBar.setProgress(0);
                    nTargetCounter++;
                    showSlider();
                    taskStarted=false;
                    swipeID++;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                if(progress > 10) {
                    taskStarted = true;
                }
            }
        });
    }

    private void setCurrentRow(){
        currentRow = latinSquare[ userId % latinSquare.length ];
    }

    private void showSlider () {
        seekBar.setProgress(0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) seekBar.getLayoutParams();
        if(currentRow.length>nTargetCounter){
        switch(currentRow[nTargetCounter]){
            case 1:
                params.topMargin = 20;
                seekBar.setRotation(0);
                break;
            case 2:
                params.topMargin = 500;
                seekBar.setRotation(0);
                break;
            case 3:
                params.topMargin = 1000;
                seekBar.setRotation(0);
                break;
            case 4:
                params.topMargin = 500;
                seekBar.setRotation(-45);
                break;
            default:

                break;
        }
        }else{
            seekBar.setVisibility(View.INVISIBLE);
            finishedSuccessfully = true;
            finish();
        }

        seekBar.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                //System.out.println("x: " + x + " y: " + y);
                //slider.setNewX(x);
            case MotionEvent.ACTION_UP:
        }
        return false;
    }

    private void createSwipeObject(float x, float y){
        Swipe swipe = new Swipe(x,y,System.currentTimeMillis(), sensorHelper.getAcceleromterData()[0], sensorHelper.getAcceleromterData()[1], sensorHelper.getAcceleromterData()[2], sensorHelper.getGravitiyData()[0], sensorHelper.getGravitiyData()[1], sensorHelper.getGravitiyData()[2], sensorHelper.getGyroscopeData()[0], sensorHelper.getGyroscopeData()[1], sensorHelper.getGyroscopeData()[2], sensorHelper.getOrientationData()[0], sensorHelper.getOrientationData()[1], sensorHelper.getOrientationData()[2], sensorHelper.getRotationData()[0], sensorHelper.getRotationData()[1], sensorHelper.getRotationData()[2], swipeID);
        loggedSwipes.add(swipe);
    }

    @Override
    public void finish () {
        if(finishedSuccessfully) {
            ActivityManager.SaveResultsInDatabase((Object[]) loggedSwipes.toArray());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isFinished", true);
            setResult(Activity.RESULT_OK, returnIntent);
        }
            super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_swiping, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.restart){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return true;
    }
}
