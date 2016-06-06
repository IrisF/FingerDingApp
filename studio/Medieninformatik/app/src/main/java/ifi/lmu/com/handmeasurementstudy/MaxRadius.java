package ifi.lmu.com.handmeasurementstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.Coords;

public class MaxRadius extends AppCompatActivity {

    private ArrayList<Coords> _aoRadiusCoords;
    private boolean _bActivityHasStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_radius);

        _aoRadiusCoords = new ArrayList<>();

        _bActivityHasStarted = false;
        prepareActivityStart();
    }

    private void prepareActivityStart () {
        final Button oButton = (Button) findViewById(R.id.radius_button);
        final TextView oText = (TextView) findViewById(R.id.radius_text);

        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oText.setVisibility(View.GONE);
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

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Coords oCoordDown = new Coords(event.getX(), event.getY());
                _aoRadiusCoords.add(oCoordDown);
                break;

            case MotionEvent.ACTION_MOVE:
                Coords oCoordMove = new Coords(event.getX(), event.getY());
                _aoRadiusCoords.add(oCoordMove);
                break;

            case MotionEvent.ACTION_UP:
                Coords oCoordUp = new Coords(event.getX(), event.getY());
                _aoRadiusCoords.add(oCoordUp);
                saveMovesToDB((Coords[]) _aoRadiusCoords.toArray());
                finish();
                break;

            default:
                break;
        }
    }

    private void saveMovesToDB (Coords[] i_aoMoveCoords){
        //TODO save to DB

    }

}
