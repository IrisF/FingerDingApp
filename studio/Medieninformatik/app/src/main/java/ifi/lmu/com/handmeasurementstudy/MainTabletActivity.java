package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainTabletActivity extends AppCompatActivity {

    private Button startButton;
    private EditText userIDText;
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tablet);

        thisActivity = this;

        userIDText = (EditText) findViewById(R.id.user_id);
        startButton = (Button) findViewById(R.id.startTasks);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userIDText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bitte User ID angeben", Toast.LENGTH_LONG).show();
                } else {
                    userIDText.setText("");
                    Intent intent = new Intent(thisActivity, ZoomingMaximum.class);
                    startActivity(intent);
                }
            }
        });
    }
}
