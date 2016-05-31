package ifi.lmu.com.handmeasurementstudy.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import ifi.lmu.com.handmeasurementstudy.system.Scroll;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;
import ifi.lmu.com.handmeasurementstudy.system.Tap;
import ifi.lmu.com.handmeasurementstudy.system.TrialSettings;

public class DBHandler extends SQLiteOpenHelper {

	// DB constants:
	private static final String DATABASE_NAME = "tapsDB";
	private static final int DATABASE_VERSION = 11;

	// DB structure constants:

	// Table taps:
	private static final String TABLE_TAPS = "taps";
	private static final String TAPS_COL_ID = "id";
	private static final String TAPS_COL_TARGET_X = "targetX";
	private static final String TAPS_COL_TARGET_Y = "targetY";
	private static final String TAPS_COL_TARGET_W = "targetW";
	private static final String TAPS_COL_TARGET_H = "targetH";
	private static final String TAPS_COL_TOUCH_DOWN_X = "touchDownX";
	private static final String TAPS_COL_TOUCH_DOWN_Y = "touchDownY";
	private static final String TAPS_COL_TOUCH_UP_X = "touchUpX";
	private static final String TAPS_COL_TOUCH_UP_Y = "touchUpY";
	private static final String TAPS_COL_HIT = "hit";
	private static final String TAPS_COL_TIME = "timestamp";
	private static final String TAPS_COL_TIME_DOWN = "timeDown";
	private static final String TAPS_COL_TIME_UP = "timeUp";
    private static final String TAPS_COL_PRESSURE_DOWN = "pressureDown";
    private static final String TAPS_COL_PRESSURE_UP = "pressureUp";
    private static final String TAPS_COL_SIZE_DOWN = "sizeDown";
    private static final String TAPS_COL_SIZE_UP = "sizeUp";
    private static final String TAPS_COL_MINOR_DOWN = "minorDown";
    private static final String TAPS_COL_MINOR_UP = "minorUp";
    private static final String TAPS_COL_MAJOR_DOWN = "majorDown";
    private static final String TAPS_COL_MAJOR_UP = "majorUp";
	private static final String TAPS_COL_TRIAL_ID = "trialID";

    //Table Scrolling
    private static final String TABLE_SCROLLING = "scrolling";
    private static final String SCROLLING_COL_ID = "id";
    private static final String SCROLLING_COL_X = "x";
    private static final String SCROLLING_COL_Y = "y";

    //Table Swiping
    private static final String TABLE_SWIPING = "swiping";
    private static final String SWIPING_COL_ID = "id";
    private static final String SWIPING_COL_X = "x";
    private static final String SWIPING_COL_Y = "y";
    private static final String SWIPING_COL_TOOL_TYPE = "toolType";
    private static final String SWIPING_COL_EVENT_TIME = "eventTime";
    private static final String SWIPING_COL_DOWN_TIME = "downTime";

    //Table Zooming
    private static final String TABLE_ZOOMING = "zooming";
    private static final String ZOOMING_COL_ID = "id";
    private static final String ZOOMING_COL_CURRENT_SPAN = "currentSpan";
    private static final String ZOOMING_COL_CURRENT_X = "currentX";
    private static final String ZOOMING_COL_CURRENT_Y = "currentY";
    private static final String ZOOMING_COL_FOCUS_X = "focusX";
    private static final String ZOOMING_COL_FOCUS_Y = "focusY";
    private static final String ZOOMING_COL_SCALE_FACTOR = "scaleFactor";
    private static final String ZOOMING_COL_TIME_DELTA = "timeDelta";
    private static final String ZOOMING_COL_EVENT_TIME = "eventTime";
    private static final String ZOOMING_COL_TRIAL_ID = "trialID";


//TODO do we need this? maybe put participant's demographic data in here
	// Table trials:
	private static final String TABLE_TRIALS = "trials";
	private static final String TRIALS_COL_ID = "id";
	private static final String TRIALS_COL_TIME = "timestamp";
	private static final String TRIALS_COL_NAME = "subjectName";
	private static final String TRIALS_COL_SUBJECT_ID = "subjectID";
	private static final String TRIALS_COL_TRIAL_MODE = "trialMode";
	private static final String TRIALS_COL_SESSION_INDEX = "sessionIndex";
	private static final String TRIALS_COL_INPUT_STYLE = "inputStyle";
    private static final String TRIALS_COL_MOBILITY = "mobility";
	private static final String TRIALS_COL_SURFACE_W = "surfaceW";
	private static final String TRIALS_COL_SURFACE_H = "surfaceH";
	private static final String TRIALS_COL_SURFACE_X = "surfaceX";
	private static final String TRIALS_COL_SURFACE_Y = "surfaceY";
	private static final String TRIALS_COL_SCREEN_W = "screenW";
	private static final String TRIALS_COL_SCREEN_H = "screenH";

	private Context context;

	/**
	 * Singleton-Pattern instance.
	 */
	private static DBHandler instance;

	/**
	 * Returns the singleton-instance (and creates it with the given context, if
	 * it is necessary).
	 * 
	 * @param context
	 * @return
	 */
	public static DBHandler getInstance(Context context) {

		if (instance == null)
			instance = new DBHandler(context);

		return instance;
	}

	/**
	 * Returns the singleton-instance, or null if it doesn't exist.
	 * 
	 * @return
	 */
	public static DBHandler getInstanceIfExists() {

		return instance;
	}

	/**
	 * Private Constructor.
	 * 
	 * @param context
	 */
	private DBHandler(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAPS);
		String createTapsTableString = "CREATE TABLE " + TABLE_TAPS + "("
				+ TAPS_COL_ID + " INTEGER PRIMARY KEY," + TAPS_COL_TARGET_X
				+ " REAL," + TAPS_COL_TARGET_Y + " REAL,"
				+ TAPS_COL_TARGET_W + " REAL," + TAPS_COL_TARGET_H
				+ " REAL," + TAPS_COL_TOUCH_DOWN_X + " REAL,"
				+ TAPS_COL_TOUCH_DOWN_Y + " REAL, " + TAPS_COL_TOUCH_UP_X
				+ " REAL," + TAPS_COL_TOUCH_UP_Y + " REAL, "
				+ TAPS_COL_HIT + " INTEGER," + TAPS_COL_TIME
				+ " TIMESTAMP NOT NULL DEFAULT current_timestamp, "
				+ TAPS_COL_TIME_DOWN + " INTEGER," + TAPS_COL_TIME_UP + " INTEGER,"
                + TAPS_COL_PRESSURE_DOWN + " REAL," + TAPS_COL_PRESSURE_UP + " REAL,"
                + TAPS_COL_SIZE_DOWN + " REAL," + TAPS_COL_SIZE_UP + " REAL,"
                + TAPS_COL_MINOR_DOWN + " REAL," + TAPS_COL_MINOR_UP + " REAL,"
                + TAPS_COL_MAJOR_DOWN + " REAL," + TAPS_COL_MAJOR_UP + " REAL,"
                + TAPS_COL_TRIAL_ID + " INTEGER)";
		db.execSQL(createTapsTableString);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIALS);
		String createTrialsTableString = "CREATE TABLE " + TABLE_TRIALS + "("
				+ TRIALS_COL_ID + " INTEGER PRIMARY KEY," + TRIALS_COL_TIME
				+ " TIMESTAMP NOT NULL DEFAULT current_timestamp, "
				+ TRIALS_COL_TRIAL_MODE + " INTEGER, "
				+ TRIALS_COL_SESSION_INDEX + " INTEGER,"
				+ TRIALS_COL_INPUT_STYLE + " INTEGER," + TRIALS_COL_MOBILITY + " INTEGER,"
                + TRIALS_COL_SURFACE_X + " INTEGER," + TRIALS_COL_SURFACE_Y + " INTEGER,"
				+ TRIALS_COL_SURFACE_W + " INTEGER," + TRIALS_COL_SURFACE_H
				+ " INTEGER," + TRIALS_COL_SCREEN_W + " INTEGER,"
				+ TRIALS_COL_SCREEN_H + " INTEGER," + TRIALS_COL_NAME
				+ " TEXT," + TRIALS_COL_SUBJECT_ID + " INTEGER)";
		db.execSQL(createTrialsTableString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAPS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIALS);
		onCreate(db);
	}

	public int insertTrial(String subjectName, int subjectID,
			TrialSettings trialSettings, int sessionIndex, int surfaceX,
			int surfaceY, int surfaceW, int surfaceH, int screenW, int screenH) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRIALS_COL_TRIAL_MODE, trialSettings.getTrialMode());
		values.put(TRIALS_COL_SESSION_INDEX, sessionIndex);
		values.put(TRIALS_COL_INPUT_STYLE, trialSettings.getInputStyle());
        values.put(TRIALS_COL_MOBILITY, trialSettings.getMobility());
		values.put(TRIALS_COL_NAME, subjectName);
		values.put(TRIALS_COL_SUBJECT_ID, subjectID);
		values.put(TRIALS_COL_SURFACE_X, surfaceX);
		values.put(TRIALS_COL_SURFACE_Y, surfaceY);
		values.put(TRIALS_COL_SURFACE_W, surfaceW);
		values.put(TRIALS_COL_SURFACE_H, surfaceH);
		values.put(TRIALS_COL_SCREEN_W, screenW);
		values.put(TRIALS_COL_SCREEN_H, screenH);

		int id = (int) db.insert(TABLE_TRIALS, null, values);
		Log.d("DEBUG", "inserted trial with id: " + id + " and inputStyle="
				+ trialSettings.getInputStyle() + " and mobility=" + trialSettings.getMobility());

		db.close();
		return id;
	}

	public void insertTap(Tap tap, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TAPS_COL_TARGET_X, tap.targetX);
		values.put(TAPS_COL_TARGET_Y, tap.targetY);
		//values.put(TAPS_COL_TARGET_W, trialSettings.getButtonWidth());
		//values.put(TAPS_COL_TARGET_H, trialSettings.getButtonHeight());
		values.put(TAPS_COL_TOUCH_DOWN_X, tap.downX);
		values.put(TAPS_COL_TOUCH_DOWN_Y, tap.downY);
		values.put(TAPS_COL_TOUCH_UP_X, tap.upX);
		values.put(TAPS_COL_TOUCH_UP_Y, tap.upY);
		//values.put(TAPS_COL_HIT, tap.hit);
		values.put(TAPS_COL_TIME_DOWN, tap.timeDown);
		//values.put(TAPS_COL_TIME_UP, tap.timeUp);
        values.put(TAPS_COL_PRESSURE_DOWN, tap.pressureDown);
        values.put(TAPS_COL_PRESSURE_UP, tap.pressureUp);
        values.put(TAPS_COL_SIZE_DOWN, tap.sizeDown);
        values.put(TAPS_COL_SIZE_UP, tap.sizeUp);
        //values.put(TAPS_COL_MINOR_DOWN, tap.minorDown);
        //values.put(TAPS_COL_MINOR_UP, tap.minorUp);
       // values.put(TAPS_COL_MAJOR_DOWN, tap.majorDown);
        //values.put(TAPS_COL_MAJOR_UP, tap.majorUp);
		values.put(TAPS_COL_TRIAL_ID, userId);

		int id = (int) db.insert(TABLE_TAPS, null, values);
		Log.d("DEBUG", "inserted tap with id: " + id);

		db.close();
	}

	public void insertZoom(Zoom zoom) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ZOOMING_COL_CURRENT_SPAN, zoom.currentSpan);
		values.put(ZOOMING_COL_CURRENT_X, zoom.currentX);
		values.put(ZOOMING_COL_CURRENT_Y, zoom.currentY);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusX);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusY);
		values.put(ZOOMING_COL_SCALE_FACTOR, zoom.scaleFactor);
		values.put(ZOOMING_COL_TIME_DELTA, zoom.timeDelta);
		values.put(ZOOMING_COL_EVENT_TIME, zoom.eventTime);

		int id = (int) db.insert(TABLE_ZOOMING, null, values);
		Log.d("DEBUG", "inserted zoom with id: " + id);

		db.close();
	}

	public void insertScroll(Scroll scroll) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SCROLLING_COL_X, scroll.x);
		values.put(SCROLLING_COL_Y, scroll.x);

		int id = (int) db.insert(TABLE_SCROLLING, null, values);
		Log.d("DEBUG", "inserted scroll with id: " + id);

		db.close();
	}

	public void insertSwipe(Swipe swipe) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SWIPING_COL_X, swipe.x);
		values.put(SWIPING_COL_Y, swipe.y);
		values.put(SWIPING_COL_TOOL_TYPE, swipe.toolType);
		values.put(SWIPING_COL_DOWN_TIME, swipe.downTime);
		values.put(SWIPING_COL_EVENT_TIME, swipe.eventTime);

		int id = (int) db.insert(TABLE_SWIPING, null, values);
		Log.d("DEBUG", "inserted swipe with id: " + id);

		db.close();
	}

	/**
	 * Export database to external storage, so it can be accessed from / copied
	 * to a computer.
	 * 
	 * @return
	 */
	public boolean exportDB() {
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
		File sd = Environment.getExternalStorageDirectory();
		// File sd =
		// this.context.getFilesDir();//this.context.getExternalFilesDir(null);
		// File sd =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File data = Environment.getDataDirectory();
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = "/data/" + "de.tapstest" + "/databases/"
				+ DATABASE_NAME;
		String backupDBPath = DATABASE_NAME;
		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(sd, backupDBPath);

		Log.d("RESTORE", backupDB.toString());
		try {
			source = new FileInputStream(currentDB).getChannel();
			destination = new FileOutputStream(backupDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
			MediaScannerConnection.scanFile(this.context, new String[]{backupDB.getAbsolutePath()}, null, null);
			return true;
		} catch (IOException e) {
			e.printStackTrace();

		}
		return false;
	}

}
