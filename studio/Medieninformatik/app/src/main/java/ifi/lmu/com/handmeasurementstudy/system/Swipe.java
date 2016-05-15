package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Sarah on 15.05.2016.
 */
public class Swipe {
    public float x;
    public float y;
    public float toolType;
    public float eventTime;
    public float downTime;

    public Swipe(float x, float y, float toolType, float eventTime, float downTime) {
        this.x = x;
        this.y = y;
        this.toolType = toolType;
        this.eventTime = eventTime;
        this.downTime = downTime;
    }

    @Override
    public String toString() {
        return "Swipe{" +
                "x=" + x +
                ", y=" + y +
                ", toolType=" + toolType +
                ", eventTime=" + eventTime +
                ", downTime=" + downTime +
                '}';
    }
}
