package ifi.lmu.com.handmeasurementstudy;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ifi.lmu.com.handmeasurementstudy.system.ActivityManager;
import ifi.lmu.com.handmeasurementstudy.system.Scroll;
import ifi.lmu.com.handmeasurementstudy.system.SensorHelper;
import ifi.lmu.com.handmeasurementstudy.system.Swipe;


public class Scrolling extends ActionBarActivity  {

    private ListView listView;
    private TextView textView;
    private Button button;
    private long time;
    private ArrayList<Scroll> loggedScrolls;
    private boolean finishedSuccessfully = false;
    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        sensorHelper = new SensorHelper(this);
        loggedScrolls = new ArrayList<>();
        textView = (TextView) findViewById(R.id.scrollingDescr);
        button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=0;
                float y=0;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        time = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();
                        //System.out.println("x: " + x + " y: " + y);
                        if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() -1 &&
                                listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight())
                        {
                            time = System.currentTimeMillis();
                            listView.setVisibility(View.INVISIBLE);
                            finishedSuccessfully = true;
                            finish();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        x = event.getX();
                        y = event.getY();
                        time = System.currentTimeMillis();
                        break;
                }
                createScrollObject(x,y);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                //System.out.println("x: " + x + " y: " + y);
            case MotionEvent.ACTION_UP:return true;
        }
        return false;
    }

    private void createScrollObject(float x, float y){
        Scroll scroll = new Scroll(x,y,time, sensorHelper.getAcceleromterData()[0], sensorHelper.getAcceleromterData()[1], sensorHelper.getAcceleromterData()[2], sensorHelper.getGravitiyData()[0], sensorHelper.getGravitiyData()[1], sensorHelper.getGravitiyData()[2], sensorHelper.getGyroscopeData()[0], sensorHelper.getGyroscopeData()[1], sensorHelper.getGyroscopeData()[2], sensorHelper.getOrientationData()[0], sensorHelper.getOrientationData()[1], sensorHelper.getOrientationData()[2], sensorHelper.getRotationData()[0], sensorHelper.getRotationData()[1], sensorHelper.getRotationData()[2]);
        loggedScrolls.add(scroll);
    }
    

    @Override
    public void finish () {
        if(finishedSuccessfully) {
            ActivityManager.SaveResultsInDatabase((Object[]) loggedScrolls.toArray());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isFinished", true);
            setResult(Activity.RESULT_OK, returnIntent);
        }
            super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

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
}
