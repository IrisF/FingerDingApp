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
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.Scroll;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;


public class Scrolling extends ActionBarActivity implements SensorEventListener {

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
    private ListView listView;
    private TextView textView;
    private Button button;
    private long secondsAtStart;
    private long time;
    private ArrayList<Scroll> loggedScrolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        loggedScrolls = new ArrayList<>();
        textView = (TextView) findViewById(R.id.scrollingDescr);
        button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                secondsAtStart = System.currentTimeMillis();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=0;
                float y=0;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        time = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();
                        //System.out.println("x: " + x + " y: " + y);
                        if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() -1 &&
                                listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight())
                        {
                            time = System.currentTimeMillis();
                            listView.setVisibility(View.INVISIBLE);
                            finish();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        x = event.getX();
                        y = event.getY();
                        time = System.currentTimeMillis();
                        break;
                }
                createScrollObject(x,y);
                return false;
            }
        });
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
            case MotionEvent.ACTION_DOWN:return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                //System.out.println("x: " + x + " y: " + y);
            case MotionEvent.ACTION_UP:return true;
        }
        return false;
    }

    private void createScrollObject(float x, float y){
        Scroll scroll = new Scroll(x,y,time, accX, accY, accZ, graX, graY, graZ, gyrX, gyrY, gyrZ);
        loggedScrolls.add(scroll);
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

    @Override
    protected void onDestroy () {
        super.onDestroy();
        ActivityManager.SaveResultsInDatabase((Object[]) loggedScrolls.toArray());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("isFinished",true);
        setResult(Activity.RESULT_OK,returnIntent);
    }
}
