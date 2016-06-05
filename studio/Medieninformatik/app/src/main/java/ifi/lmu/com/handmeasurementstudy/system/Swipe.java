package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Sarah on 15.05.2016.
 */
public class Swipe {
    public float x;
    public float y;
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
    public float rotZ;
    public float rotX;
    public float rotY;
    public int swipeId;
    public long time;

    public Swipe(float x, float y, long time, float accX, float accY, float accZ, float graX, float graY, float graZ, float gyrX, float gyrY, float gyrZ, float orientationX, float orientationY, float orientationZ, float rotX, float rotY, float rotZ, int swipeId) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.graX = graX;
        this.graY = graY;
        this.graZ = graZ;
        this.gyrX = gyrX;
        this.gyrY = gyrY;
        this.gyrZ = gyrZ;
        this.orientationX = orientationX;
        this.orientationY = orientationY;
        this.orientationZ = orientationZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.swipeId = swipeId;
    }

    @Override
    public String toString() {
        return "Swipe{" +
                "x=" + x +
                ", y=" + y +
                ", time=" + time +
                ", accX=" + accX +
                ", accY=" + accY +
                ", accZ=" + accZ +
                ", graX=" + graX +
                ", graY=" + graY +
                ", graZ=" + graZ +
                ", gyrX=" + gyrX +
                ", gyrY=" + gyrY +
                ", gyrZ=" + gyrZ +
                ", orienttationX=" + orientationX +
                ", orientationY=" + orientationY +
                ", orientationZ=" + orientationZ +
                ", rotX=" + rotX +
                ", rotY=" + rotY +
                ", rotZ=" + rotZ +
                ", swipeId=" + swipeId +
                '}';
    }
}
