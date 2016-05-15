package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Sarah on 15.05.2016.
 */
public class Scroll {
    public float x;
    public float y;

    public Scroll(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Scroll{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
