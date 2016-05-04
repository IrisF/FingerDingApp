package ifi.lmu.com.handmeasurementstudy.system;

import de.tapstest.MainActivity;

public class Tools {

	public static float mmToPx(float mm, boolean xdim) {

		float dpi = xdim ? MainActivity.DISPLAY_XDPI
				: MainActivity.DISPLAY_YDPI;
		float px = mm * dpi * (1.0f / 25.4f);
		return px;
	}

}