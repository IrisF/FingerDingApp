package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import ifi.lmu.com.handmeasurementstudy.db.DBHandler;
import ifi.lmu.com.handmeasurementstudy.system.TaskScheduler;
import ifi.lmu.com.handmeasurementstudy.system.Tools;


public class MainActivity extends Activity {


    public static final String EXTRA_SESSION_INDEX = "de.tapstest.SESSION_INDEX";
    public static final String EXTRA_TRIAL_MODE = "de.tapstest.TRIAL_MODE";
    public static final String EXTRA_SUBJECT_NAME = "de.tapstest.SUBJECT_NAME";
    public static final int TRIAL_NUM_AUTO = 5;

    public static float DISPLAY_XDPI = -1;
    public static float DISPLAY_YDPI = -1;

    public static TaskScheduler taskScheduler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.DISPLAY_XDPI = getResources().getDisplayMetrics().xdpi;
        MainActivity.DISPLAY_YDPI = getResources().getDisplayMetrics().ydpi;

        // TODO: Just for testing unit conversions:
        Log.d("UNITS", "xdpi: " + MainActivity.DISPLAY_XDPI + ", ydpi: "
                + MainActivity.DISPLAY_YDPI);
        Log.d("UNITS", "46 mm (xdim): " + Tools.mmToPx(46, true));
        Log.d("UNITS", "82 mm (ydim): " + Tools.mmToPx(82, false));

        // Remove title bar:
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // fill session spinner:
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("1st session");
        spinnerArray.add("2nd session");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.session_index_spinner);
        spinner.setAdapter(adapter);

        // fill trial spinner:
        List<String> spinnerArray2 = new ArrayList<String>();
        spinnerArray2.add("0: debug");
        spinnerArray2.add("1: sitting, thumb");
        spinnerArray2.add("2: sitting, index");
        spinnerArray2.add("3: walking, thumb");
        spinnerArray2.add("4: walking, index");
        spinnerArray2.add("auto");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = (Spinner) findViewById(R.id.trial_spinner);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(TRIAL_NUM_AUTO);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Start a trial.
     *
     * @param view
     */
    public void onClickStartButton(View view) {

        Spinner spinner = (Spinner) findViewById(R.id.session_index_spinner);
        int sessionIndex = spinner.getSelectedItemPosition();

        Spinner spinner2 = (Spinner) findViewById(R.id.trial_spinner);
        int selectedTrialID = spinner2.getSelectedItemPosition();

        EditText name_edit = (EditText) findViewById(R.id.name_text);
        String name = name_edit.getText().toString();

        EditText userID_edit = (EditText) findViewById(R.id.userID_text);
        int userID = Integer.valueOf(userID_edit.getText().toString());
        MainActivity.taskScheduler = new TaskScheduler(userID, sessionIndex);


        Intent intent = new Intent(this, Tapping.class);
        intent.putExtra(MainActivity.EXTRA_SESSION_INDEX, sessionIndex);
        intent.putExtra(MainActivity.EXTRA_TRIAL_MODE, selectedTrialID);
        intent.putExtra(MainActivity.EXTRA_SUBJECT_NAME, name);

        startActivity(intent);
    }

    public void onClickExportDBButton(View view) {

        DBHandler dbHandler = DBHandler.getInstance(this);
        boolean result = dbHandler.exportDB();
        if (result) {
            Toast.makeText(this, "Database exported to external storage! :)",
                    Toast.LENGTH_LONG).show();
        }
    }
}
