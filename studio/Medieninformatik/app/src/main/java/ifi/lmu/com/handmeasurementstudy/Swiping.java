package ifi.lmu.com.handmeasurementstudy;

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

    private static final int[] latinSquare = {1,2,3,4};
    public static int nSideLength = latinSquare.length;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping);
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
        
        showSlider(nTargetCounter);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        //System.out.println("x: " + x + " y: " + y);

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()==100){
                    seekBar.setProgress(0);
                    nTargetCounter++;
                    showSlider(nTargetCounter);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {

            }
        });
    }

    private void showSlider (int i_nSliderIndex) {
        int[] anLatinIndex =  getLatinIndexByCrosshairCount(i_nSliderIndex);
        seekBar.setProgress(0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) seekBar.getLayoutParams();
        switch(i_nSliderIndex){
            case 0:
                params.topMargin = 20;
                break;
            case 1:
                timeTask1 = System.currentTimeMillis() - secondsAtStart;
                currentStartTime = System.currentTimeMillis();
                params.topMargin = 500;
                break;
            case 2:
                timeTask2 = System.currentTimeMillis() - currentStartTime;
                currentStartTime = System.currentTimeMillis();
                params.topMargin = 1000;
                break;
            case 3:
                timeTask3 = System.currentTimeMillis() - currentStartTime;
                currentStartTime = System.currentTimeMillis();
                params.topMargin = 500;
                seekBar.setRotation(-45);
                break;
            default:
                timeTask4 = System.currentTimeMillis() - currentStartTime;
                timeAllTasks = System.currentTimeMillis()- secondsAtStart;
                seekBar.setVisibility(View.INVISIBLE);
                System.out.println("Task 1: " + timeTask1 + " Task 2: " + timeTask2 + " Task 3: " + timeTask3 + " Task 4: " + timeTask4 + " all Tasks: " + timeAllTasks);
                break;
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

    private int[] getLatinIndexByCrosshairCount(int i_nSliderIndex) {
        int[] anLatinIndex = new int[1];

        int nLatinSize = latinSquare.length;
        anLatinIndex[0] = i_nSliderIndex / nLatinSize;

        return anLatinIndex;
    }
}
