package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Sarah on 15.05.2016.
 */
public class Radius {
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
    public long time;
    public float size;
    public float pressure;

    public Radius(float x, float y, long time, float accX, float accY, float accZ, float graX,
                  float graY, float graZ, float gyrX, float gyrY, float gyrZ, float orientationX,
                  float orientationY, float orientationZ, float rotX, float rotY, float rotZ,
                  float size, float pressure) {
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
        this.size = size;
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Radius{" +
                "x=" + x +
                ", y=" + y +
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
                ", rotZ=" + rotZ +
                ", rotX=" + rotX +
                ", rotY=" + rotY +
                ", time=" + time +
                ", size=" + size +
                ", pressure=" + pressure +
                '}';
    }
}
