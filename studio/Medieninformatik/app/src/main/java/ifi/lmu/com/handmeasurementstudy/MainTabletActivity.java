package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainTabletActivity extends AppCompatActivity {

    private Button startButton;
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tablet);

        thisActivity = this;

        startButton = (Button) findViewById(R.id.startTasks);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisActivity, ZoomingMaximum.class);
                startActivity(intent);
            }
        });
    }
}
