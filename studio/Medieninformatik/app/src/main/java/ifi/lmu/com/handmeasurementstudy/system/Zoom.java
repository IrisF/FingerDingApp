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
    public float accX;
    public float accY;
    public float accZ;
    public float graX;
    public float graY;
    public float graZ;
    public float gyrX;
    public float gyrY;
    public float gyrZ;
    public float orientationZ;
    public float orientationX;
    public float orientationY;
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public int rectangleIndex;

    public Zoom(float currentSpan, float currentX, float currentY, float focusX, float focusY, float scaleFactor, float timeDelta, float eventTime, float accX, float accY, float accZ, float graX, float graY, float graZ, float gyrX, float gyrY, float gyrZ, float orientationZ, float orientationX, float orientationY, float rotationX, float rotationY, float rotationZ, int rectangleIndex) {
        this.currentSpan = currentSpan;
        this.currentX = currentX;
        this.currentY = currentY;
        this.focusX = focusX;
        this.focusY = focusY;
        this.scaleFactor = scaleFactor;
        this.timeDelta = timeDelta;
        this.eventTime = eventTime;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.graX = graX;
        this.graY = graY;
        this.graZ = graZ;
        this.gyrX = gyrX;
        this.gyrY = gyrY;
        this.gyrZ = gyrZ;
        this.orientationZ = orientationZ;
        this.orientationX = orientationX;
        this.orientationY = orientationY;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
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
                ", accX=" + accX +
                ", accY=" + accY +
                ", accZ=" + accZ +
                ", graX=" + graX +
                ", graY=" + graY +
                ", graZ=" + graZ +
                ", gyrX=" + gyrX +
                ", gyrY=" + gyrY +
                ", gyrZ=" + gyrZ +
                ", orientationZ=" + orientationZ +
                ", orientationX=" + orientationX +
                ", orientationY=" + orientationY +
                ", rotationX=" + rotationX +
                ", rotationY=" + rotationY +
                ", rotationZ=" + rotationZ +
                ", rectangleIndex=" + rectangleIndex +
                '}';
    }
}