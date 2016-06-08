package ifi.lmu.com.handmeasurementstudy.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import ifi.lmu.com.handmeasurementstudy.MainActivity;

/**
 * Created by Iris on 03.06.2016.
 */
public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

    private final Context context;
    private static final String DATABASE_NAME = "FingerDingDB";
    private static final String[] TABLE_NAMES = {"taps", "scrolling", "swiping", "zooming", "users", "radius", "zoomingTablet"};

    private static SQLiteDatabase db;


    public ExportDatabaseCSVTask(Context context, SQLiteDatabase db){
        this.context=context;
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Exporting database...", Toast.LENGTH_SHORT).show();
    }

    protected Boolean doInBackground(final String... args) {
        File dbFile = context.getDatabasePath(DATABASE_NAME+".db");
        System.out.println(dbFile);  // displays the data base path in your logcat
        File exportDir = new File(Environment.getExternalStorageDirectory(), "FingerDingApp");

        if (!exportDir.exists()) { exportDir.mkdirs(); }


        try {
            for(int i=0; i<TABLE_NAMES.length;i++){
            File file = new File(exportDir, TABLE_NAMES[i] + ".csv");
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            Cursor curCSV = db.rawQuery("select * from " + TABLE_NAMES[i], null);
            csvWrite.writeNext(curCSV.getColumnNames());
            int length = curCSV.getColumnCount();
            while (curCSV.moveToNext()) {
                String arrStr[] = new String[length];

                for(int j=0; j<length;j++){
                    arrStr[j] = curCSV.getString(j);
                }
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
            db.close();
            return true;
        } catch(SQLException sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            db.close();
            return false;
        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage(), e);
            db.close();
            return false;
        }
    }

    protected void onPostExecute(final Boolean success) {
        if (success) {
            Toast.makeText(context, "Export successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT).show();
        }
    }
}