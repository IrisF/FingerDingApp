package ifi.lmu.com.handmeasurementstudy.system;

public class Tap {

	public float downX;
	public float downY;
	public float upX;
	public float upY;
	public float targetX;
	public float targetY;
	public int hit;
	public long timeDown;
	public long timeUp;
    public float pressureDown;
    public float pressureUp;
    public float sizeDown;
    public float sizeUp;
    public float minorDown;
    public float minorUp;
    public float majorDown;
    public float majorUp;

	public Tap(float downX, float downY, float upX, float upY, float targetX,
			float targetY, int hit, long timeDown, long timeUp,
            float pressureDown, float pressureUp, float sizeDown, float sizeUp,
            float minorDown, float minorUp, float majorDown, float majorUp) {
		this.downX = downX;
		this.downY = downY;
		this.upX = upX;
		this.upY = upY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.hit = hit;
		this.timeDown = timeDown;
		this.timeUp = timeUp;
        this.pressureDown = pressureDown;
        this.pressureUp = pressureUp;
        this.sizeDown = sizeDown;
        this.sizeUp = sizeUp;
        this.minorDown = minorDown;
        this.minorUp = minorUp;
        this.majorDown = majorDown;
        this.majorUp = majorUp;
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
				", hit=" + hit +
				", timeDown=" + timeDown +
				", timeUp=" + timeUp +
				", pressureDown=" + pressureDown +
				", pressureUp=" + pressureUp +
				", sizeDown=" + sizeDown +
				", sizeUp=" + sizeUp +
				", minorDown=" + minorDown +
				", minorUp=" + minorUp +
				", majorDown=" + majorDown +
				", majorUp=" + majorUp +
				'}';
	}
}
