package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;

public class TabletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        final Activity thisActivity = this;

        Button finishButton = (Button) findViewById(R.id.finishTabletTask);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisActivity.finish();
            }
        });

        displayID(savedInstanceState);
    }

    @Override
    public void finish () {
        //ActivityManager.SaveResultsInDatabase((Object[]) zoomData.toArray());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("isFinished",true);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }

    private void displayID (Bundle i_oBundle) {
        int nId;
        if (i_oBundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nId= 0;
            } else {
                nId= extras.getInt("id");
            }
        } else {
            nId = (int) i_oBundle.getSerializable("id");
        }

        TextView oIdView = (TextView) findViewById(R.id.id_tablet);
        oIdView.setText("User ID: " + String.valueOf(nId));
    }
}
