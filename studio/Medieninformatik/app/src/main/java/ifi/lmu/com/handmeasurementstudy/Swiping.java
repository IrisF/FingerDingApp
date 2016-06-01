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
import ifi.lmu.com.handmeasurementstudy.system.Swipe;


public class Swiping extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor gravitySensor;
    private Sensor gyroscopeSensor;
    private float accX;
    private float accY;
    private float accZ;
    private float graX;
    private float graY;
    private float graZ;
    private float gyrX;
    private float gyrY;
    private float gyrZ;
    private SeekBar seekBar;
    private ArrayList<String> posList = new ArrayList();
    private boolean finishedSuccessfully = false;

    private int nTargetCounter = 0;

    private TextView textView;
    private Button button;
    private long secondsAtStart;
    private long timeNeeded;
    private long currentStartTime;
    private long timeTask1;
    private long timeTask2;
    private long timeTask3;
    private long timeTask4;
    private long timeAllTasks;
    private long currentTime;
    private boolean taskStarted =false;
    private int userId;
    private int[] currentRow;
    private ArrayList<Swipe> loggedSwipes;

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
                secondsAtStart = System.currentTimeMillis();
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
                currentTime = System.currentTimeMillis();
                params.topMargin = 20;
                seekBar.setRotation(0);
                break;
            case 2:
                currentTime = System.currentTimeMillis();
                params.topMargin = 500;
                seekBar.setRotation(0);
                break;
            case 3:
                currentTime = System.currentTimeMillis();
                params.topMargin = 1000;
                seekBar.setRotation(0);
                break;
            case 4:
                currentTime = System.currentTimeMillis();
                params.topMargin = 500;
                seekBar.setRotation(-45);
                break;
            default:

                break;
        }
        }else{
            currentTime = System.currentTimeMillis();
            seekBar.setVisibility(View.INVISIBLE);
            finishedSuccessfully = true;
            finish();
        }

        seekBar.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accX = event.values[0];
            accY = event.values[1];
            accZ = event.values[2];
        }
        if(event.sensor.getType()==Sensor.TYPE_GRAVITY){
            graX = event.values[0];
            graY = event.values[1];
            graZ = event.values[2];
        }
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            gyrX = event.values[0];
            gyrY = event.values[1];
            gyrZ = event.values[2];
        }

        //System.out.println("Acceleration: x= " + accX + " y= " + accY + " z= " + accZ);
        //System.out.println("Gravity: x= " + graX + " y= " + graY + " z= " + graZ);
        //System.out.println("Rotation: x= " + gyrX + " y= " + gyrY + " z= " + gyrZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void createSwipeObject(float x, float y){
        Swipe swipe = new Swipe(x,y,currentTime, accX, accY, accZ, graX, graY, graZ, gyrX, gyrY, gyrZ);
        if(currentTime!=0){
            currentTime=0;
        }
        loggedSwipes.add(swipe);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if(finishedSuccessfully) {
            ActivityManager.SaveResultsInDatabase((Object[]) loggedSwipes.toArray());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isFinished", true);
            setResult(Activity.RESULT_OK, returnIntent);
        }
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
