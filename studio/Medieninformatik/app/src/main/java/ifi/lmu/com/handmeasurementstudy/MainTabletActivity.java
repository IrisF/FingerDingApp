package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ifi.lmu.com.handmeasurementstudy.R;
import ifi.lmu.com.handmeasurementstudy.ZoomingMaximum;
import ifi.lmu.com.handmeasurementstudy.db.DBHandler;

public class MainTabletActivity extends AppCompatActivity {

    private Button startButton;
    private Button exportButton;
    private EditText userIDText;
    private Activity thisActivity;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tablet);

        thisActivity = this;

        dbHandler = DBHandler.getInstance(thisActivity);

        userIDText = (EditText) findViewById(R.id.user_id);
        startButton = (Button) findViewById(R.id.startTasks);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userIDText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bitte User ID angeben", Toast.LENGTH_LONG).show();
                } else {
                    userIDText.setText("");
                    Intent intent = new Intent(thisActivity, ZoomingMaximumTablet.class);
                    startActivity(intent);
                }
            }
        });

        exportButton = (Button) findViewById(R.id.button_export_tablet_DB);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = DBHandler.getInstance(thisActivity);
                boolean result = dbHandler.exportDB();
                if (result) {
                    Toast.makeText(thisActivity, "Database exported to external storage! :)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
