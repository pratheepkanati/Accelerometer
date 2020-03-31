package com.lirctek.ics;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
   TextView x_dir;TextView y_dir;TextView z_dir;
    private SensorManager sensorManager;
    private String sx;
    private String sy;
    private String sz;
    // magnetic field vector
    private float[] magnet = new float[3];
    private Display mDisplay;
    private WindowManager mWindowManager;
    // accelerometer vector
    private float[] accel = new float[3];
    private long currrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        x_dir=findViewById(R.id.x_dir);
        y_dir=findViewById(R.id.y_dir);
        z_dir=findViewById(R.id.z_dir);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */
            float xVal = sensorEvent.values[0];
            float yVal = sensorEvent.values[1];
            float zVal = Math.abs(sensorEvent.values[2]);

            sx = "X Value : <font color = '#800080'> " + xVal + "</font>";
            sy = "Y Value : <font color = '#800080'> " + yVal + "</font>";
            sz = "Z Value : <font color = '#800080'> " + zVal + "</font>";
           long currrentTime=System.currentTimeMillis();
           if(this.currrentTime==0||currrentTime-this.currrentTime>1000){
               this.currrentTime=currrentTime;
            x_dir.setText(Html.fromHtml(sx));
            y_dir.setText(Html.fromHtml(sy));
            z_dir .setText(Html.fromHtml(sz));
            if(xVal<-4.76f){
                Snackbar.make(findViewById(R.id.fab), "Harsh Break", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else if(xVal>4.41f){
                Snackbar.make(findViewById(R.id.fab), "Harsh Accerleration", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(yVal<-3.0f){
                Snackbar.make(findViewById(R.id.fab), "Hard Left Turn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else if(yVal>3.0f){
                Snackbar.make(findViewById(R.id.fab), "Hard Right Turn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            if(!(zVal>=8.0f&&zVal<=11.0f)){
                Snackbar.make(findViewById(R.id.fab), "Hard Down Bump or Hard Up Bump", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }}

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
