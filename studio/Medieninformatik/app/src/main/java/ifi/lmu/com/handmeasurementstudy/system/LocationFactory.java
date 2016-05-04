package ifi.lmu.com.handmeasurementstudy.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationFactory {

	private int counter;

	private float minX;
	private float minY;

	private float maxX;
	private float maxY;

	private int stepsX;
	private int stepsY;
	private int numTargets;
	private float[][] targets;

	public LocationFactory(float minX, float minY, float maxX, float maxY,
			int stepsX, int stepsY) {

		this.minX = minX;
		this.minY = minY;

		this.maxX = maxX;
		this.maxY = maxY;

		this.stepsX = stepsX;
		this.stepsY = stepsY;

		this.counter = 0;
		this.numTargets = this.stepsX * this.stepsY;

		this.targets = new float[stepsX * stepsY][2];

		this.init();
	}

	private void init() {

		double width;
		if (this.stepsX > 1) {
			width = 1.0 * (this.maxX - this.minX) / (this.stepsX - 1);
		} else if (this.maxX - this.minX != 0) {
			width = (this.maxX - this.minX);
		} else {
			width = this.minX;
		}
		double height = 1.0 * (this.maxY - this.minY) / (this.stepsY - 1);

		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < this.numTargets; i++) {
			indices.add(Integer.valueOf(i));
		}
		Collections.shuffle(indices);
		for (int i = 0; i < this.numTargets; i++) {
			int random_i = indices.get(i);
			float x = (float) (this.minX + (random_i % this.stepsX * width));
			float y = (float) (this.minY + (random_i / this.stepsX) * height);
			this.targets[i][0] = x;
			this.targets[i][1] = y;
			//Log.d("DEBUG", x + ", " + y);
		}
	}

	public float[] nextTarget() {

		float[] target = this.targets[this.counter];
		this.counter = (this.counter + 1) % this.numTargets;
		return target;
	}

	public float[][] getTargets() {
		return this.targets;
	}

}
