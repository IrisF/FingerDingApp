package ifi.lmu.com.handmeasurementstudy.system;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Sarah on 12.05.2016.
 */
public class SensorHelper implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private Sensor senGravity;
    private Sensor senGyroscope;
    private Sensor senOrientation;
    private Sensor senRotation;

    private float[] acceleromterData;
    private float[] gravitiyData;
    private float[] gyroscopeData;
    private float[] orientationData;
    private float[] rotationData;


    public SensorHelper(Context context) {
        //initialize the sensor manager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        senGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        senRotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, senOrientation, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, senRotation, SensorManager.SENSOR_DELAY_FASTEST);

        acceleromterData = new float[3];
        gravitiyData = new float[3];
        gyroscopeData = new float[3];
        orientationData = new float[3];
        rotationData = new float[3];
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        switch(mySensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                Log.i("Sensor", "Accelerometer: " + String.valueOf(event.values[0]));
                acceleromterData[0] = event.values[0];
                acceleromterData[1] = event.values[1];
                acceleromterData[2] = event.values[2];
                break;
            case Sensor.TYPE_GRAVITY:
                Log.i("Sensor", "Gravity: " + String.valueOf(event.values[0]));
                gravitiyData[0] = event.values[0];
                gravitiyData[1] = event.values[1];
                gravitiyData[2] = event.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                Log.i("Sensor", "Gyroscope: " + String.valueOf(event.values[0]));
                gyroscopeData[0] = event.values[0];
                gyroscopeData[1] = event.values[1];
                gyroscopeData[2] = event.values[2];
                break;
            case Sensor.TYPE_ORIENTATION:
                Log.i("Sensor", "Orientation " + String.valueOf(event.values[0]));
                //order is z, x, y
                orientationData[0] = event.values[1];
                orientationData[1] = event.values[2];
                orientationData[2] = event.values[0];

                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                Log.i("Sensor", "Rotation Vector: " + String.valueOf(event.values[0]));
                rotationData[0] = event.values[0];
                rotationData[1] = event.values[1];
                rotationData[2] = event.values[2];
                break;

            //for testing purpose: which other Sensor events occur
            default:
                Log.i("Sensor", "Other: " + event.values.toString());
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float[] getAcceleromterData() {
        return acceleromterData;
    }

    public float[] getGravitiyData() {
        return gravitiyData;
    }

    public float[] getGyroscopeData() {
        return gyroscopeData;
    }

    public float[] getOrientationData() {
        return orientationData;
    }

    public float[] getRotationData() {
        return rotationData;
    }
}
