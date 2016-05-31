package ifi.lmu.com.handmeasurementstudy.system;

import java.util.Arrays;

/**
 * Created by Sarah on 15.05.2016.
 */
public class Zoom {
    public float currentSpan;
    public float currentX;
    public float currentY;
    public float focusX;
    public float focusY;
    public float scaleFactor;
    public float timeDelta;
    public float eventTime;
    public float[] accelerometerData;
    public float[] gravitiyData;
    public float[] gyroscopeData;
    public int rectangleIndex;

    public Zoom(float currentSpan, float currentX, float currentY, float focusX, float focusY, float scaleFactor, float timeDelta, float eventTime, float[] accelerometerData, float[] gravitiyData, float[] gyroscopeData, int rectangleIndex) {
        this.currentSpan = currentSpan;
        this.currentX = currentX;
        this.currentY = currentY;
        this.focusX = focusX;
        this.focusY = focusY;
        this.scaleFactor = scaleFactor;
        this.timeDelta = timeDelta;
        this.eventTime = eventTime;
        this.accelerometerData = accelerometerData;
        this.gravitiyData = gravitiyData;
        this.gyroscopeData = gyroscopeData;
        this.rectangleIndex = rectangleIndex;
    }

    @Override
    public String toString() {
        return "Zoom{" +
                "currentSpan=" + currentSpan +
                ", currentX=" + currentX +
                ", currentY=" + currentY +
                ", focusX=" + focusX +
                ", focusY=" + focusY +
                ", scaleFactor=" + scaleFactor +
                ", timeDelta=" + timeDelta +
                ", eventTime=" + eventTime +
                ", accelerometerData=" + Arrays.toString(accelerometerData) +
                ", gravitiyData=" + Arrays.toString(gravitiyData) +
                ", gyroscopeData=" + Arrays.toString(gyroscopeData) +
                ", rectangleIndex=" + rectangleIndex +
                '}';
    }
}