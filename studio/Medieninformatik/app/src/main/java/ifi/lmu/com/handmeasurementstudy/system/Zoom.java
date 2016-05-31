package ifi.lmu.com.handmeasurementstudy.system;

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



    public Zoom(float currentSpan, float currentX, float currentY, float focusX, float focusY, float scaleFactor, float timeDelta, float eventTime) {
        this.currentSpan = currentSpan;
        this.currentX = currentX;
        this.currentY = currentY;
        this.focusX = focusX;
        this.focusY = focusY;
        this.scaleFactor = scaleFactor;
        this.timeDelta = timeDelta;
        this.eventTime = eventTime;
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
                '}';
    }
}
