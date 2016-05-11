package ifi.lmu.com.handmeasurementstudy.gui;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class AbstractDrawingPanel extends SurfaceView implements
SurfaceHolder.Callback{
	
	protected PanelThread thread;

	public AbstractDrawingPanel(Context context) {
		super(context);

	}

}
