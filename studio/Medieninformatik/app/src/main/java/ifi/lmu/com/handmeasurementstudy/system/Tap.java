package ifi.lmu.com.handmeasurementstudy.system;

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

	public Tap(float downX, float downY, float upX, float upY, float targetX,
			float targetY, long timeDown, long timeUp, float pressureDown, float pressureUp,
               float sizeDown, float sizeUp, Coords[] moveCoords) {
		this.downX = downX;
		this.downY = downY;
		this.upX = upX;
		this.upY = upY;
		this.targetX = targetX;
		this.targetY = targetY;
		//this.hit = hit;
		this.timeDown = timeDown;
		//this.timeUp = timeUp;
        this.pressureDown = pressureDown;
        this.pressureUp = pressureUp;
        this.sizeDown = sizeDown;
        this.sizeUp = sizeUp;
        //this.minorDown = minorDown;
        //this.minorUp = minorUp;
        //this.majorDown = majorDown;
        //this.majorUp = majorUp;
	}
/*
	public float getDownX() {
		return downX;
	}

	public float getDownY() {
		return downY;
	}

	public float getUpX() {
		return upX;
	}

	public float getUpY() {
		return upY;
	}

	public float getTargetX() {
		return targetX;
	}

	public float getTargetY() {
		return targetY;
	}

	public long getTimeDown() {
		return timeDown;
	}

	public float getPressureDown() {
		return pressureDown;
	}

	public float getPressureUp() {
		return pressureUp;
	}

	public float getSizeDown() {
		return sizeDown;
	}

	public float getSizeUp() {
		return sizeUp;
	}

    public Coords[] getMoveCoords() {
        return moveCoords;
    }
    */
}

