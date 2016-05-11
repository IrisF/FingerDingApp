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


public class Swiping extends ActionBarActivity implements SensorEventListener {

    private Slider slider;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider = new Slider(this.getApplicationContext());
        setContentView(slider);
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
                System.out.println("x: " + x + " y: " + y);
                slider.setNewX(x);
            case MotionEvent.ACTION_UP:
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println("onSensorChanged");
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

        System.out.println("Acceleration: x= " + accX + " y= " + accY + " z= " + accZ);
        System.out.println("Gravity: x= " + graX + " y= " + graY + " z= " + graZ);
        System.out.println("Rotation: x= " + gyrX + " y= " + gyrY + " z= " + gyrZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
