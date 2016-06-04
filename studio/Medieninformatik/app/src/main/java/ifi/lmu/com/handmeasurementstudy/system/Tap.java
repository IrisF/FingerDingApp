package ifi.lmu.com.handmeasurementstudy.system;

import java.util.Arrays;

public class Tap {

	public float downX;
    public float downY;
    public float upX;
    public float upY;
    public float targetX;
    public float targetY;
	//public int hit;
    public long timeDown;
	public long timeUp;
    public float pressureDown;
    public float pressureUp;
    public float sizeDown;
    public float sizeUp;

   // public float minorDown;
   // public float minorUp;
   // public float majorDown;
   // public float majorUp;
    public Coords[] moveCoords;


    // TODO get orientation
    public float orientationX;
    public float orientationY;
    public float orientationZ;

	public Tap(float downX, float downY, float upX, float upY, float targetX, float targetY,
			   long timeDown, long timeUp, float pressureDown, float pressureUp, float sizeDown,
			   float sizeUp, Coords[] moveCoords, float orientationX, float orientationY,
			   float orientationZ) {
		this.downX = downX;
		this.downY = downY;
		this.upX = upX;
		this.upY = upY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.timeDown = timeDown;
		this.timeUp = timeUp;
		this.pressureDown = pressureDown;
		this.pressureUp = pressureUp;
		this.sizeDown = sizeDown;
		this.sizeUp = sizeUp;
		this.moveCoords = moveCoords;
		this.orientationX = orientationX;
		this.orientationY = orientationY;
		this.orientationZ = orientationZ;
	}


	@Override
	public String toString() {
		return "Tap{" +
				"downX=" + downX +
				", downY=" + downY +
				", upX=" + upX +
				", upY=" + upY +
				", targetX=" + targetX +
				", targetY=" + targetY +
				", timeDown=" + timeDown +
				", timeUp=" + timeUp +
				", pressureDown=" + pressureDown +
				", pressureUp=" + pressureUp +
				", sizeDown=" + sizeDown +
				", sizeUp=" + sizeUp +
				", moveCoords=" + Arrays.toString(moveCoords) +
				", orientationX=" + orientationX +
				", orientationY=" + orientationY +
				", orientationZ=" + orientationZ +
				'}';
	}
}

