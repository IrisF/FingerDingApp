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


    public SensorHelper(Context context) {
        //initialize the sensor manager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        switch(mySensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                Log.i("Sensor", "Accelerometer: " + event.values.toString());
                break;
            case Sensor.TYPE_GRAVITY:
                Log.i("Sensor", "Gravity: " + event.values.toString());
                break;
            case Sensor.TYPE_GYROSCOPE:
                Log.i("Sensor", "Gyroscope: " + event.values.toString());
                break;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                Log.i("Sensor", "Gyroscope uncalibrated: " + event.values.toString());
                break;
            case Sensor.TYPE_PRESSURE:
                Log.i("Sensor", "Pressure: " + event.values.toString());
                break;
            case Sensor.TYPE_PROXIMITY:
                Log.i("Sensor", "Proximity: " + event.values.toString());
                break;
            case Sensor.TYPE_ORIENTATION:
                Log.i("Sensor", "Orientation " + event.values.toString());
            case Sensor.TYPE_ROTATION_VECTOR:
                Log.i("Sensor", "Rotation Vector: " + event.values.toString());
                break;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                Log.i("Sensor", "Significant Motion: " + event.values.toString());
                break;

    //TODO are these necessary for us?
            /*
            case Sensor.TYPE_HEART_RATE:
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_STEP_DETECTOR:
            case Sensor.TYPE_TEMPERATURE:
            */

            //for testing purpose: which other Sensor events occur
            default:
                Log.i("Sensor", "Other: " + event.values.toString());
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
