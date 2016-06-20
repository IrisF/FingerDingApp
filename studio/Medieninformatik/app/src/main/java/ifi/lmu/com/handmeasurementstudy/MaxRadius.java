package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.Coords;
import ifi.lmu.com.handmeasurementstudy.system.Radius;
import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;

public class MaxRadius extends AppCompatActivity {

    private ArrayList<Radius> _aoRadiusCoords;
    private boolean _bActivityHasStarted;
    private SensorHelper _oSensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_radius);

        _oSensorHelper = new SensorHelper(this);
        _aoRadiusCoords = new ArrayList<>();

        _bActivityHasStarted = false;
        prepareActivityStart();
    }

    private void prepareActivityStart () {
        final Button oButton = (Button) findViewById(R.id.radius_button);
        final TextView oText = (TextView) findViewById(R.id.radius_text);
        final ImageView oArrow = (ImageView) findViewById(R.id.arrow);

        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oText.setVisibility(View.GONE);
                oArrow.setVisibility(View.GONE);
                oButton.setVisibility(View.GONE);
                _bActivityHasStarted = true;

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(_bActivityHasStarted) {
            saveTouch(event);
        }
        return false;
    }

    private void saveTouch(MotionEvent event) {

        float[] afAcc = _oSensorHelper.getAcceleromterData();
        float[] afGravity = _oSensorHelper.getGravitiyData();
        float[] afGyro = _oSensorHelper.getGyroscopeData();
        float[] afOrient = _oSensorHelper.getOrientationData();
        float[] afRot = _oSensorHelper.getRotationData();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                _oSensorHelper = new SensorHelper(this);
                Radius oCoordDown = new Radius(event.getX(), event.getY(), System.currentTimeMillis(),
                        afAcc[0], afAcc[1], afAcc[2], afGravity[0], afGravity[1], afGravity[2],
                        afGyro[0], afGyro[1], afGyro[2], afOrient[0], afOrient[1], afOrient[2],
                        afRot[0], afRot[1], afRot[2], event.getSize(),event.getPressure());
                _aoRadiusCoords.add(oCoordDown);
                break;

            case MotionEvent.ACTION_MOVE:
                Radius oCoordMove = new Radius(event.getX(), event.getY(), System.currentTimeMillis(),
                        afAcc[0], afAcc[1], afAcc[2], afGravity[0], afGravity[1], afGravity[2],
                        afGyro[0], afGyro[1], afGyro[2], afOrient[0], afOrient[1], afOrient[2],
                        afRot[0], afRot[1], afRot[2], event.getSize(),event.getPressure());
                _aoRadiusCoords.add(oCoordMove);
                break;

            case MotionEvent.ACTION_UP:
                Radius oCoordUp = new Radius(event.getX(), event.getY(), System.currentTimeMillis(),
                        afAcc[0], afAcc[1], afAcc[2], afGravity[0], afGravity[1], afGravity[2],
                        afGyro[0], afGyro[1], afGyro[2], afOrient[0], afOrient[1], afOrient[2],
                        afRot[0], afRot[1], afRot[2], event.getSize(),event.getPressure());
                _aoRadiusCoords.add(oCoordUp);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] strButtons = {"OKAY", "NOCHMAL"};
                builder.setTitle("Speichern?")
                        .setItems(strButtons, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item

                                if(which == 0){
                                    finish();
                                }else{
                                    _aoRadiusCoords.clear();
                                    dialog.dismiss();
                                }
                            }

                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();

                break;

            default:
                break;
        }
    }


    @Override
    public void finish () {
        ActivityManager.SaveResultsInDatabase(_aoRadiusCoords.toArray());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("isFinished",true);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }

}
