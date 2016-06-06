package ifi.lmu.com.handmeasurementstudy.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import ifi.lmu.com.handmeasurementstudy.system.Scroll;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;
import ifi.lmu.com.handmeasurementstudy.system.User;
import ifi.lmu.com.handmeasurementstudy.system.Zoom;
import ifi.lmu.com.handmeasurementstudy.system.Tap;
import ifi.lmu.com.handmeasurementstudy.system.TrialSettings;

public class DBHandler extends SQLiteOpenHelper {

	// DB constants:
	private static final String DATABASE_NAME = "FingerDingDB";
	private static final int DATABASE_VERSION = 11;

	// DB structure constants:

	private static final String COL_USER_ID = "userID";

	// Table taps:
	private static final String TABLE_TAPS = "taps";
	private static final String TAPS_COL_ID = "id";
	private static final String TAPS_COL_TARGET_X = "targetX";
	private static final String TAPS_COL_TARGET_Y = "targetY";
	private static final String TAPS_COL_TOUCH_DOWN_X = "touchDownX";
	private static final String TAPS_COL_TOUCH_DOWN_Y = "touchDownY";
	private static final String TAPS_COL_TOUCH_UP_X = "touchUpX";
	private static final String TAPS_COL_TOUCH_UP_Y = "touchUpY";
	private static final String TAPS_COL_TIME_DOWN = "timeDown";
	private static final String TAPS_COL_TIME_UP = "timeUp";
    private static final String TAPS_COL_PRESSURE_DOWN = "pressureDown";
    private static final String TAPS_COL_PRESSURE_UP = "pressureUp";
    private static final String TAPS_COL_SIZE_DOWN = "sizeDown";
    private static final String TAPS_COL_SIZE_UP = "sizeUp";
	private static final String TAPS_COL_ORIENTATION_X = "orientationX";
	private static final String TAPS_COL_ORIENTATION_Y = "orientationY";
	private static final String TAPS_COL_ORIENTATION_Z = "orientationZ";
	private static final String TAPS_COL_MOVE_CSV = "moveCSV";

    //Table Scrolling
    private static final String TABLE_SCROLLING = "scrolling";
    private static final String SCROLLING_COL_ID = "id";
    private static final String SCROLLING_COL_X = "x";
    private static final String SCROLLING_COL_Y = "y";
    private static final String SCROLLING_COL_ACCX = "accX";
	private static final String SCROLLING_COL_ACCY = "accY";
	private static final String SCROLLING_COL_ACCZ = "accZ";
	private static final String SCROLLING_COL_GRAX = "graX";
	private static final String SCROLLING_COL_GRAY = "graY";
	private static final String SCROLLING_COL_GRAZ = "graZ";
	private static final String SCROLLING_COL_GYRX = "gyrX";
	private static final String SCROLLING_COL_GYRY = "gyrY";
	private static final String SCROLLING_COL_ORIENTATION_X = "orientationX";
	private static final String SCROLLING_COL_ORIENTATION_Y = "orientationY";
	private static final String SCROLLING_COL_ORIENTATION_Z = "orientationZ";
	private static final String SCROLLING_COL_ROT_X = "rotX";
	private static final String SCROLLING_COL_ROT_Y = "rotY";
	private static final String SCROLLING_COL_ROT_Z = "rotZ";
	private static final String SCROLLING_COL_GYRZ = "gyrZ";
	private static final String SCROLLING_COL_SCROLLNUM = "scrollNum";
	private static final String SCROLLING_COL_TIME= "time";

	//Table Swiping
    private static final String TABLE_SWIPING = "swiping";
    private static final String SWIPING_COL_ID = "id";
    private static final String SWIPING_COL_X = "x";
    private static final String SWIPING_COL_Y = "y";
    private static final String SWIPING_COL_TIME= "time";
    private static final String SWIPING_COL_ACCX = "accX";
    private static final String SWIPING_COL_ACCY = "accY";
	private static final String SWIPING_COL_ACCZ = "accZ";
	private static final String SWIPING_COL_GRAX = "graX";
	private static final String SWIPING_COL_GRAY = "graY";
	private static final String SWIPING_COL_GRAZ = "graZ";
	private static final String SWIPING_COL_GYRX = "gyrX";
	private static final String SWIPING_COL_GYRY = "gyrY";
	private static final String SWIPING_COL_GYRZ = "gyrZ";
	private static final String SWIPING_COL_ORIENTATION_X = "orientationX";
	private static final String SWIPING_COL_ORIENTATION_Y = "orientationY";
	private static final String SWIPING_COL_ORIENTATION_Z = "orientationZ";
	private static final String SWIPING_COL_ROT_X = "rotX";
	private static final String SWIPING_COL_ROT_Y = "rotY";
	private static final String SWIPING_COL_ROT_Z = "rotZ";
    private static final String SWIPING_COL_SWIPE_ID = "swipeId";

    //Table Zooming
	private static final String TABLE_ZOOMING_TABLET = "zooming_Tablet";
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
	private static final String ZOOMING_COL_ACC_X = "accX";
	private static final String ZOOMING_COL_ACC_Y = "accY";
	private static final String ZOOMING_COL_ACC_Z = "accZ";
	private static final String ZOOMING_COL_GRA_X = "graX";
	private static final String ZOOMING_COL_GRA_Y = "graY";
	private static final String ZOOMING_COL_GRA_Z = "graZ";
	private static final String ZOOMING_COL_GYR_X = "gyrX";
	private static final String ZOOMING_COL_GYR_Y = "gyrY";
	private static final String ZOOMING_COL_GYR_Z = "gyrZ";
	private static final String ZOOMING_COL_ORIENTATION_Z = "orientationZ";
	private static final String ZOOMING_COL_ORIENTATION_X = "orientationX";
	private static final String ZOOMING_COL_ORIENTATION_Y = "orientationY";
	private static final String ZOOMING_COL_ROT_X = "rotationX";
	private static final String ZOOMING_COL_ROT_Y = "rotationY";
	private static final String ZOOMING_COL_ROT_Z = "rotationZ";
	private static final String ZOOMING_COL_RECT = "rectangleIndex";



	// Table users
	private static final String TABLE_USERS = "users";
	private static final String USER_COL_ID = "id";
	private static final String USER_COL_AGE = "age";
	private static final String USER_COL_GENDER = "gender";
	private static final String USER_COL_HAND_SPAN = "handSpan";
	private static final String USER_COL_HAND_WIDTH = "handWidth";
	private static final String USER_COL_HAND_LENGTH = "handLength";

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
        Log.d("Mal schauen", "Ob was passiert");


		String createUsersTableString = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
				+ USER_COL_ID + " INTEGER PRIMARY KEY, "
				+ USER_COL_AGE + " INTEGER, "
				+ USER_COL_GENDER + " TEXT, "
				+ USER_COL_HAND_SPAN + " INTEGER, "
				+ USER_COL_HAND_LENGTH + " INTEGER, "
				+ USER_COL_HAND_WIDTH + " INTEGER)";
		db.execSQL(createUsersTableString);

		String createTapsTableString = "CREATE TABLE IF NOT EXISTS " + TABLE_TAPS + " ("
				+ TAPS_COL_ID + " INTEGER PRIMARY KEY, "
				+ COL_USER_ID + " INTEGER, "
				+ TAPS_COL_TARGET_X + " REAL, "
				+ TAPS_COL_TARGET_Y + " REAL, "
				+ TAPS_COL_TOUCH_DOWN_X + " REAL, "
				+ TAPS_COL_TOUCH_DOWN_Y + " REAL, "
				+ TAPS_COL_TOUCH_UP_X + " REAL, "
				+ TAPS_COL_TOUCH_UP_Y + " REAL, "
				+ TAPS_COL_TIME_DOWN + " INTEGER, "
				+ TAPS_COL_TIME_UP + " INTEGER, "
                + TAPS_COL_PRESSURE_DOWN + " REAL, "
				+ TAPS_COL_PRESSURE_UP + " REAL, "
				+ TAPS_COL_SIZE_DOWN + " REAL, "
				+ TAPS_COL_SIZE_UP + " REAL, "
				+ TAPS_COL_ORIENTATION_X + " REAL, "
				+ TAPS_COL_ORIENTATION_Y + " REAL, "
				+ TAPS_COL_ORIENTATION_Z + " REAL, "
				+ TAPS_COL_MOVE_CSV + " TEXT)";
		db.execSQL(createTapsTableString);

		String createZoomingTableString = "CREATE TABLE IF NOT EXISTS " + TABLE_ZOOMING + " ("
				+ ZOOMING_COL_ID + " INTEGER PRIMARY KEY, "
				+ COL_USER_ID + " INTEGER, "
				+ ZOOMING_COL_CURRENT_SPAN + " FLOAT, "
				+ ZOOMING_COL_CURRENT_X + " FLOAT, "
				+ ZOOMING_COL_CURRENT_Y + " FLOAT, "
				+ ZOOMING_COL_FOCUS_X + " FLOAT, "
				+ ZOOMING_COL_FOCUS_Y + " FLOAT, "
				+ ZOOMING_COL_SCALE_FACTOR + " FLOAT, "
				+ ZOOMING_COL_TIME_DELTA + " FLOAT, "
				+ ZOOMING_COL_EVENT_TIME + " FLOAT, "
				+ ZOOMING_COL_ACC_X + " FLOAT, "
				+ ZOOMING_COL_ACC_Y + " FLOAT, "
				+ ZOOMING_COL_ACC_Z + " FLOAT, "
				+ ZOOMING_COL_GRA_X + " FLOAT, "
				+ ZOOMING_COL_GRA_Y + " FLOAT, "
				+ ZOOMING_COL_GRA_Z + " FLOAT, "
				+ ZOOMING_COL_GYR_X + " FLOAT, "
				+ ZOOMING_COL_GYR_Y + " FLOAT, "
				+ ZOOMING_COL_GYR_Z + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_X + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_Y + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_Z + " FLOAT, "
				+ ZOOMING_COL_ROT_X + " FLOAT, "
				+ ZOOMING_COL_ROT_Y + " FLOAT, "
				+ ZOOMING_COL_ROT_Z + " FLOAT, "
				+ ZOOMING_COL_RECT + " INTEGER)";
		db.execSQL(createZoomingTableString);

		String createZoomingTabletTableString = "CREATE TABLE IF NOT EXISTS " + TABLE_ZOOMING_TABLET + " ("
				+ ZOOMING_COL_ID + " INTEGER PRIMARY KEY, "
				+ COL_USER_ID + " INTEGER, "
				+ ZOOMING_COL_CURRENT_SPAN + " FLOAT, "
				+ ZOOMING_COL_CURRENT_X + " FLOAT, "
				+ ZOOMING_COL_CURRENT_Y + " FLOAT, "
				+ ZOOMING_COL_FOCUS_X + " FLOAT, "
				+ ZOOMING_COL_FOCUS_Y + " FLOAT, "
				+ ZOOMING_COL_SCALE_FACTOR + " FLOAT, "
				+ ZOOMING_COL_TIME_DELTA + " FLOAT, "
				+ ZOOMING_COL_EVENT_TIME + " FLOAT, "
				+ ZOOMING_COL_ACC_X + " FLOAT, "
				+ ZOOMING_COL_ACC_Y + " FLOAT, "
				+ ZOOMING_COL_ACC_Z + " FLOAT, "
				+ ZOOMING_COL_GRA_X + " FLOAT, "
				+ ZOOMING_COL_GRA_Y + " FLOAT, "
				+ ZOOMING_COL_GRA_Z + " FLOAT, "
				+ ZOOMING_COL_GYR_X + " FLOAT, "
				+ ZOOMING_COL_GYR_Y + " FLOAT, "
				+ ZOOMING_COL_GYR_Z + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_X + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_Y + " FLOAT, "
				+ ZOOMING_COL_ORIENTATION_Z + " FLOAT, "
				+ ZOOMING_COL_ROT_X + " FLOAT, "
				+ ZOOMING_COL_ROT_Y + " FLOAT, "
				+ ZOOMING_COL_ROT_Z + " FLOAT, "
				+ ZOOMING_COL_RECT + " INTEGER)";
		db.execSQL(createZoomingTableString);

		String createSwipingTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SWIPING + " ("
				+ SWIPING_COL_ID + " INTEGER PRIMARY KEY, "
				+ COL_USER_ID + " INTEGER, "
				+ SWIPING_COL_X + " FLOAT, "
				+ SWIPING_COL_Y + " FLOAT, "
				+ SWIPING_COL_ACCX + " FLOAT, "
				+ SWIPING_COL_ACCY + " FLOAT, "
				+ SWIPING_COL_ACCZ + " FLOAT, "
				+ SWIPING_COL_GRAX + " FLOAT, "
				+ SWIPING_COL_GRAY + " FLOAT, "
				+ SWIPING_COL_GRAZ + " FLOAT, "
				+ SWIPING_COL_GYRX + " FLOAT, "
				+ SWIPING_COL_GYRY + " FLOAT, "
				+ SWIPING_COL_GYRZ + " FLOAT, "
				+ SWIPING_COL_ORIENTATION_X + " FLOAT, "
				+ SWIPING_COL_ORIENTATION_Y + " FLOAT, "
				+ SWIPING_COL_ORIENTATION_Z + " FLOAT, "
				+ SWIPING_COL_ROT_X + " FLOAT, "
				+ SWIPING_COL_ROT_Y + " FLOAT, "
				+ SWIPING_COL_ROT_Z + " FLOAT, "
                + SWIPING_COL_SWIPE_ID+ " INTEGER, "
				+ SWIPING_COL_TIME + " LONG)";
		db.execSQL(createSwipingTable);

		String createScrollingTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SCROLLING + " ("
				+ SCROLLING_COL_ID + " INTEGER PRIMARY KEY, "
				+ COL_USER_ID + " INTEGER, "
				+ SCROLLING_COL_X + " FLOAT, "
				+ SCROLLING_COL_Y + " FLOAT, "
				+ SCROLLING_COL_ACCX + " FLOAT, "
				+ SCROLLING_COL_ACCY + " FLOAT, "
				+ SCROLLING_COL_ACCZ + " FLOAT, "
				+ SCROLLING_COL_GRAX + " FLOAT, "
				+ SCROLLING_COL_GRAY + " FLOAT, "
				+ SCROLLING_COL_GRAZ + " FLOAT, "
				+ SCROLLING_COL_GYRX + " FLOAT, "
				+ SCROLLING_COL_GYRY + " FLOAT, "
				+ SCROLLING_COL_GYRZ + " FLOAT, "
				+ SCROLLING_COL_ORIENTATION_X + " FLOAT, "
				+ SCROLLING_COL_ORIENTATION_Y + " FLOAT, "
				+ SCROLLING_COL_ORIENTATION_Z + " FLOAT, "
				+ SCROLLING_COL_ROT_X + " FLOAT, "
				+ SCROLLING_COL_ROT_Y + " FLOAT, "
				+ SCROLLING_COL_ROT_Z + " FLOAT, "
				+ SCROLLING_COL_SCROLLNUM + " INETGER, "
				+ SCROLLING_COL_TIME + " LONG)";
		db.execSQL(createScrollingTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAPS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}

	public int insertUser(User user) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_COL_ID, user.id);
		values.put(USER_COL_AGE, user.age);
		values.put(USER_COL_GENDER, user.gender);
		values.put(USER_COL_HAND_LENGTH, user.length);
		values.put(USER_COL_HAND_SPAN, user.span);
		values.put(USER_COL_HAND_WIDTH, user.width);

		int id = (int) db.insert(TABLE_USERS, null, values);
		//Log.d("DEBUG", "inserted user with id: " + id);

		db.close();
		return id;
	}

	public int getLastUsersId() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor oCur = db.query(TABLE_USERS, new String[] {USER_COL_ID},
				null, null, null, null, null);
		if(oCur != null) {
			oCur.moveToLast();
			if (oCur.getCount() > 0) {
				return oCur.getInt(0);
			} else {
				return -1;
			}
		}
		else return -1;
	}

	public void insertTap(Tap tap, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TAPS_COL_TARGET_X, tap.targetX);
		values.put(TAPS_COL_TARGET_Y, tap.targetY);
		values.put(TAPS_COL_TOUCH_DOWN_X, tap.downX);
		values.put(TAPS_COL_TOUCH_DOWN_Y, tap.downY);
		values.put(TAPS_COL_TOUCH_UP_X, tap.upX);
		values.put(TAPS_COL_TOUCH_UP_Y, tap.upY);
		values.put(TAPS_COL_TIME_DOWN, tap.timeDown);
		values.put(TAPS_COL_TIME_UP, tap.timeUp);
        values.put(TAPS_COL_PRESSURE_DOWN, tap.pressureDown);
        values.put(TAPS_COL_PRESSURE_UP, tap.pressureUp);
        values.put(TAPS_COL_SIZE_DOWN, tap.sizeDown);
        values.put(TAPS_COL_SIZE_UP, tap.sizeUp);
		values.put(COL_USER_ID, userId);
		values.put(TAPS_COL_ORIENTATION_X, tap.orientationX);
		values.put(TAPS_COL_ORIENTATION_Y, tap.orientationY);
		values.put(TAPS_COL_ORIENTATION_Z, tap.orientationZ);
		String strMoveCsv = "";
		for(int i =0; i < tap.moveCoords.length -1; i++){
			strMoveCsv += String.valueOf(tap.moveCoords[i].getX()) + "#";
			strMoveCsv += String.valueOf(tap.moveCoords[i].getY()) + ";";
		}
		if(tap.moveCoords.length > 0) {
			strMoveCsv += String.valueOf(tap.moveCoords[tap.moveCoords.length - 1].getX()) + "#";
			strMoveCsv += String.valueOf(tap.moveCoords[tap.moveCoords.length - 1].getY());
		}

		values.put(TAPS_COL_MOVE_CSV, strMoveCsv);

		int id = (int) db.insert(TABLE_TAPS, null, values);
		//Log.d("DEBUG", "inserted tap with id: " + id);

		db.close();
	}

	public void insertZoom(Zoom zoom, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_USER_ID, userId);
		values.put(ZOOMING_COL_CURRENT_SPAN, zoom.currentSpan);
		values.put(ZOOMING_COL_CURRENT_X, zoom.currentX);
		values.put(ZOOMING_COL_CURRENT_Y, zoom.currentY);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusX);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusY);
		values.put(ZOOMING_COL_SCALE_FACTOR, zoom.scaleFactor);
		values.put(ZOOMING_COL_TIME_DELTA, zoom.timeDelta);
		values.put(ZOOMING_COL_EVENT_TIME, zoom.eventTime);
		values.put(ZOOMING_COL_ACC_X, zoom.accX);
		values.put(ZOOMING_COL_ACC_Y, zoom.accY);
		values.put(ZOOMING_COL_ACC_Z, zoom.accZ);
		values.put(ZOOMING_COL_GRA_X, zoom.graX);
		values.put(ZOOMING_COL_GRA_Y, zoom.graY);
		values.put(ZOOMING_COL_GRA_Z, zoom.graZ);
		values.put(ZOOMING_COL_GYR_X, zoom.gyrX);
		values.put(ZOOMING_COL_GYR_Y, zoom.gyrY);
		values.put(ZOOMING_COL_GYR_Z, zoom.gyrZ);
		values.put(ZOOMING_COL_ORIENTATION_X, zoom.orientationX);
		values.put(ZOOMING_COL_ORIENTATION_Y, zoom.orientationY);
		values.put(ZOOMING_COL_ORIENTATION_Z, zoom.orientationZ);
		values.put(ZOOMING_COL_ROT_X, zoom.rotationX);
		values.put(ZOOMING_COL_ROT_Y, zoom.rotationY);
		values.put(ZOOMING_COL_ROT_Z, zoom.rotationZ);
		values.put(ZOOMING_COL_RECT, zoom.rectangleIndex);

		int id = (int) db.insert(TABLE_ZOOMING, null, values);
		//Log.d("DEBUG", "inserted zoom with id: " + id);

		db.close();
	}

	public void insertTabletZoom(Zoom zoom, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_USER_ID, userId);
		values.put(ZOOMING_COL_CURRENT_SPAN, zoom.currentSpan);
		values.put(ZOOMING_COL_CURRENT_X, zoom.currentX);
		values.put(ZOOMING_COL_CURRENT_Y, zoom.currentY);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusX);
		values.put(ZOOMING_COL_FOCUS_X, zoom.focusY);
		values.put(ZOOMING_COL_SCALE_FACTOR, zoom.scaleFactor);
		values.put(ZOOMING_COL_TIME_DELTA, zoom.timeDelta);
		values.put(ZOOMING_COL_EVENT_TIME, zoom.eventTime);
		values.put(ZOOMING_COL_ACC_X, zoom.accX);
		values.put(ZOOMING_COL_ACC_Y, zoom.accY);
		values.put(ZOOMING_COL_ACC_Z, zoom.accZ);
		values.put(ZOOMING_COL_GRA_X, zoom.graX);
		values.put(ZOOMING_COL_GRA_Y, zoom.graY);
		values.put(ZOOMING_COL_GRA_Z, zoom.graZ);
		values.put(ZOOMING_COL_GYR_X, zoom.gyrX);
		values.put(ZOOMING_COL_GYR_Y, zoom.gyrY);
		values.put(ZOOMING_COL_GYR_Z, zoom.gyrZ);
		values.put(ZOOMING_COL_ORIENTATION_X, zoom.orientationX);
		values.put(ZOOMING_COL_ORIENTATION_Y, zoom.orientationY);
		values.put(ZOOMING_COL_ORIENTATION_Z, zoom.orientationZ);
		values.put(ZOOMING_COL_ROT_X, zoom.rotationX);
		values.put(ZOOMING_COL_ROT_Y, zoom.rotationY);
		values.put(ZOOMING_COL_ROT_Z, zoom.rotationZ);
		values.put(ZOOMING_COL_RECT, zoom.rectangleIndex);

		int id = (int) db.insert(TABLE_ZOOMING_TABLET, null, values);
		Log.d("DEBUG", "inserted tablet zoom with id: " + id);

		db.close();
	}



	public void insertScroll(Scroll scroll, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_USER_ID, userId);
		values.put(SCROLLING_COL_X, scroll.x);
		values.put(SCROLLING_COL_Y, scroll.x);
        values.put(SCROLLING_COL_TIME, scroll.time);
        values.put(SCROLLING_COL_ACCX, scroll.accX);
        values.put(SCROLLING_COL_ACCY, scroll.accY);
        values.put(SCROLLING_COL_ACCZ, scroll.accZ);
        values.put(SCROLLING_COL_GRAX, scroll.graX);
        values.put(SCROLLING_COL_GRAY, scroll.graY);
        values.put(SCROLLING_COL_GRAZ, scroll.graZ);
        values.put(SCROLLING_COL_GYRX, scroll.gyrX);
        values.put(SCROLLING_COL_GYRY, scroll.gyrY);
        values.put(SCROLLING_COL_GYRZ, scroll.gyrZ);
		values.put(SCROLLING_COL_ORIENTATION_X, scroll.orientationX);
		values.put(SCROLLING_COL_ORIENTATION_Y, scroll.orientationY);
		values.put(SCROLLING_COL_ORIENTATION_Z, scroll.orientationZ);
		values.put(SCROLLING_COL_ROT_X, scroll.rotX);
		values.put(SCROLLING_COL_ROT_Y, scroll.rotY);
		values.put(SCROLLING_COL_ROT_Z, scroll.rotZ);
		values.put(SCROLLING_COL_SCROLLNUM, scroll.scrollNum);

		int id = (int) db.insert(TABLE_SCROLLING, null, values);
		//Log.d("DEBUG", "inserted scroll with id: " + id);

		db.close();
	}

	public void insertSwipe(Swipe swipe, int userId) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_USER_ID, userId);
		values.put(SWIPING_COL_X, swipe.x);
		values.put(SWIPING_COL_Y, swipe.y);
		values.put(SWIPING_COL_TIME, swipe.time);
		values.put(SWIPING_COL_ACCX, swipe.accX);
		values.put(SWIPING_COL_ACCY, swipe.accY);
		values.put(SWIPING_COL_ACCZ, swipe.accZ);
		values.put(SWIPING_COL_GRAX, swipe.graX);
		values.put(SWIPING_COL_GRAY, swipe.graY);
		values.put(SWIPING_COL_GRAZ, swipe.graZ);
		values.put(SWIPING_COL_GYRX, swipe.gyrX);
		values.put(SWIPING_COL_GYRY, swipe.gyrY);
		values.put(SWIPING_COL_GYRZ, swipe.gyrZ);
		values.put(SWIPING_COL_ORIENTATION_X, swipe.orientationX);
		values.put(SWIPING_COL_ORIENTATION_Y, swipe.orientationY);
		values.put(SWIPING_COL_ORIENTATION_Z, swipe.orientationZ);
		values.put(SWIPING_COL_ROT_X, swipe.rotX);
		values.put(SWIPING_COL_ROT_Y, swipe.rotY);
		values.put(SWIPING_COL_ROT_Z, swipe.rotZ);
        values.put(SWIPING_COL_SWIPE_ID, swipe.swipeId);

		int id = (int) db.insert(TABLE_SWIPING, null, values);
		//Log.d("DEBUG", "inserted swipe with id: " + id);

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
	/*	File sd = Environment.getExternalStorageDirectory();
		// File sd =
		// this.context.getFilesDir();//this.context.getExternalFilesDir(null);
		// File sd =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File data = Environment.getDataDirectory();
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = "/data/" + "FingerDingApp" + "/databases/"
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

            FileOutputStream fos = context.openFileOutput("Database", Context.MODE_PRIVATE);
            fos.write(source);
            fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();

		}*/
        SQLiteDatabase db = this.getWritableDatabase();
        new ExportDatabaseCSVTask(context, db).execute("");

		return false;
	}

	public void deleteDatabase ()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP DATABASE");
	}

}
