package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import ifi.lmu.com.handmeasurementstudy.db.DBHandler;
import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.TaskScheduler;
import ifi.lmu.com.handmeasurementstudy.system.Tools;


public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_SESSION_INDEX = "de.tapstest.SESSION_INDEX";
    public static final String EXTRA_TRIAL_MODE = "de.tapstest.TRIAL_MODE";
    public static final String EXTRA_SUBJECT_NAME = "de.tapstest.SUBJECT_NAME";
    public static final int TRIAL_NUM_AUTO = 5;

    public static final boolean b_IS_DEBUG = true;

    public static float DISPLAY_XDPI = -1;
    public static float DISPLAY_YDPI = -1;

    public static TaskScheduler taskScheduler = null;

    private Spinner debugSpinner;

    private int nCurrentId;

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
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // fill debug test options list
        List<String> debugArray = new ArrayList<>();
        debugArray.add("Tapping");
        debugArray.add("Zooming");
        debugArray.add("Scrolling");
        debugArray.add("Swiping");
        debugArray.add("ZoomingMaximum");

        ArrayAdapter<String> debugAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, debugArray);
        debugAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //final Spinner
                debugSpinner = (Spinner) findViewById(R.id.debug_mode);
        debugSpinner.setAdapter(debugAdapter);


        // fill session spinner:
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("1st session");
        spinnerArray.add("2nd session");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.session_index_spinner);
        spinner.setAdapter(adapter);

        // fill trial spinner:
        List<String> spinnerArray2 = new ArrayList<>();
        spinnerArray2.add("0: Test");
        spinnerArray2.add("1: DEBUG");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = (Spinner) findViewById(R.id.trial_spinner);
        spinner2.setAdapter(adapter2);
        //spinner2.setSelection(TRIAL_NUM_AUTO);

        setUserId();


        // check for debug mode selected in spinner2 to show debug mode spinner
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) // 1 == DEBUG
                {
                    debugSpinner.setVisibility(View.VISIBLE);
                } else {
                    debugSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUserId() {
        TextView idView = (TextView) findViewById(R.id.userId);
        int nId = 1; // TODO get user id through DB connection
        idView.setText(String.valueOf(nId));
        nCurrentId = nId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.restart){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return true;
    }

    /**
     * Start a trial.
     *
     * @param view
     */
    public void onClickStartButton(View view) {

        boolean bIsDebug = debugSpinner.getVisibility() == View.VISIBLE;

            // check if debug mode is on
        if(bIsDebug){
            int debugMode = debugSpinner.getSelectedItemPosition();
            Intent intent;
            switch(debugMode)
            {
                case 0: //Tapping
                    intent = new Intent(this, Tapping.class);
                    break;
                case 1: // Zooming
                    intent = new Intent(this, Zooming.class);
                    break;
                case 2: //Scrolling
                    intent = new Intent(this, Scrolling.class);
                    break;
                case 3: // Swiping
                    intent = new Intent(this, Swiping.class);
                    break;
                case 4: // Zooming Maximum
                    intent = new Intent(this, ZoomingMaximum.class);
                    break;
                default:
                    intent = new Intent(this, Tapping.class);
            }
            startActivity(intent);
        }
        else {

            if(checkForInputsCorrect()) {

                Spinner spinner = (Spinner) findViewById(R.id.session_index_spinner);
                int sessionIndex = spinner.getSelectedItemPosition();

                //Spinner spinner2 = (Spinner) findViewById(R.id.trial_spinner);
                // int selectedTrialID = spinner2.getSelectedItemPosition();

                /*
                EditText userID_edit = (EditText) findViewById(R.id.userID_text);
                String strUserID = userID_edit.getText().toString();
                if (!strUserID.equals("")) {
                    int userID = Integer.parseInt(strUserID);
                    MainActivity.taskScheduler = new TaskScheduler(userID, sessionIndex);
                }

                */

                // TODO save user data to DB

                new ActivityManager(this, nCurrentId).Start();
                //Intent intent = new Intent(this, Tapping.class);
                //intent.putExtra(MainActivity.EXTRA_SESSION_INDEX, sessionIndex);
                // intent.putExtra(MainActivity.EXTRA_TRIAL_MODE, selectedTrialID);
                // intent.putExtra(MainActivity.EXTRA_SUBJECT_NAME, name);

                //startActivity(intent);
            }
        }



    }

    private boolean checkForInputsCorrect() {

        RadioButton m = (RadioButton) findViewById(R.id.radioButton_m);
        RadioButton w = (RadioButton) findViewById(R.id.radioButton_w);
        //EditText id = (EditText) findViewById(R.id.userID_text);
        EditText age = (EditText) findViewById(R.id.user_age);
        EditText handLength = (EditText) findViewById(R.id.hand_height);
        EditText handWidth = (EditText) findViewById(R.id.hand_width);


        if (m.isChecked() == w.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Please select gender.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (//id.getText().toString().equals("")
                handLength.getText().toString().equals("")
                || handWidth.getText().toString().equals("")
                || age.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Bitte fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
