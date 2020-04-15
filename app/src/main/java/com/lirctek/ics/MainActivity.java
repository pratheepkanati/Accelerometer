package com.lirctek.ics;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lirctek.ics.database.Event;
import com.lirctek.ics.database.ObjectBox;
import com.lirctek.ics.database.SensorData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
   TextView x_dir;TextView y_dir;TextView z_dir;
    private SensorManager sensorManager;
    private long currrentTime;
    private long locationTime;

    private float[] gravity= new float[3];
    private List<SensorData>sensorData=new ArrayList<>();
    private RecyclerView  truckDataList;
    TruckInfoLogDataAdapter truckInfoLogDataAdapter;
    private  int tempSpeed=-1;
    private static  final   double GRAVITY_PER_METER_SEC=0.101972;
    //1 m/s2 = 0.101972 g; 1 g = 9.80665 m/s2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        FloatingActionButton fab = findViewById(R.id.fab);
        truckDataList=findViewById(R.id.truckDataList);
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
        if(sensorManager!=null){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        }
        HPLinearLayoutManager layoutManager = new HPLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
         truckInfoLogDataAdapter=new TruckInfoLogDataAdapter(sensorData);
        truckDataList.setLayoutManager(layoutManager);
        truckDataList.addItemDecoration(new SimpleDividerItemDecoration(this));
        truckDataList.setAdapter(truckInfoLogDataAdapter);
        truckInfoLogDataAdapter.notifyDataSetChanged();

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
        boolean canStore=false;
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            final float alpha = 0.8f;
            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

            float xVal = sensorEvent.values[0] - gravity[0];
            float yVal = sensorEvent.values[1] - gravity[1];
            float zVal = sensorEvent.values[2] - gravity[2];
            long currrentTime = System.currentTimeMillis();
            if(this.currrentTime==0||currrentTime-this.currrentTime>=500){
            this.currrentTime = currrentTime;
            SensorData sensorData=new SensorData();
            sensorData.setTimestamp(Util.getTimestmap());
            sensorData.setDateandtime(Util.currentTime(TimeZone.getDefault()));
            sensorData.setX_acc(sensorEvent.values[0]);
            sensorData.setY_acc(sensorEvent.values[1]);
            sensorData.setZ_acc(sensorEvent.values[2]);
            sensorData.setSpeed(LocationService.speed);
            sensorData.setSpeedinMiles(LocationService.speedinMiles);
            sensorData.setX_gra(gravity[0] );
            sensorData.setY_gra(gravity[1] );
            sensorData.setZ_gra(gravity[2] );
            sensorData.setX_acc_g(xVal);
            sensorData.setY_acc_g(yVal);
            sensorData.setZ_acc_g(zVal);
            sensorData.setBearing(LocationService.location!=null?((Float)LocationService.location.getBearing()).intValue():0);
            if(tempSpeed!=-1&&(locationTime==0||currrentTime-locationTime>=1000)){

             int  changeInspeedpersec= LocationService.speedinMiles-tempSpeed;
             canStore=true;
             // Harsh Accerleartion
            if(changeInspeedpersec>=7){
             sensorData.setHarshacceleration(true);
                Event event=new Event();
                event.setAcc(sensorEvent.values[0]);
                event.setSpeed(LocationService.speedinMiles);
                event.setChangeinSpeed(changeInspeedpersec);
                event.setEventName("Harsh Accerleration with Speed");
                event.setEventTime(sensorData.getDateandtime());
                event.setEventTimeStamp(sensorEvent.timestamp);
                Event.insert(event);
                Snackbar.make(findViewById(R.id.fab), "Harsh Accerleration   with Speed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            // Harsh  Break
            else if(changeInspeedpersec<=-7){
                Event event=new Event();
                event.setAcc(sensorEvent.values[0]);
                event.setSpeed(LocationService.speedinMiles);
                event.setChangeinSpeed(changeInspeedpersec);
                event.setEventName("Harsh Break   with Speed");
                event.setEventTime(sensorData.getDateandtime());
                event.setEventTimeStamp(sensorEvent.timestamp);
                Event.insert(event);
                sensorData.setHashbreak(true);
                Snackbar.make(findViewById(R.id.fab), "Harsh Break with speed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
                tempSpeed=LocationService.speedinMiles;
            }
            else {
                if(tempSpeed==-1){
                tempSpeed=LocationService.speedinMiles;
                }
            }
            double ygravity=yVal*GRAVITY_PER_METER_SEC;
            if(ygravity>0.33||ygravity<-0.33){
                sensorData.setHarshturn(true);
                Event event=new Event();
                event.setAcc(yVal);
                event.setGravity(ygravity);
                event.setSpeed(LocationService.speedinMiles);
                event.setEventName("Harsh Turn   with ACC");
                event.setEventTime(sensorData.getDateandtime());
                event.setEventTimeStamp(sensorEvent.timestamp);
                Event.insert(event);
                Snackbar.make(findViewById(R.id.fab), "Harsh Turn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            double xgravity=xVal*GRAVITY_PER_METER_SEC;
            if(xgravity>0.29){
                Event event=new Event();
                event.setAcc(xVal);
                event.setGravity(xgravity);
                event.setSpeed(LocationService.speedinMiles);
                event.setEventName("Harsh Accelearation with Acc ");
                event.setEventTime(sensorData.getDateandtime());
                event.setEventTimeStamp(sensorEvent.timestamp);
                Event.insert(event);
                Snackbar.make(findViewById(R.id.fab), "Harsh Accerleration using Acc", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else if(xgravity<-0.29){
                Event event=new Event();
                event.setAcc(sensorEvent.values[0]);
                event.setGravity(xgravity);
                event.setSpeed(LocationService.speedinMiles);
                event.setEventName("Harsh Break with Acc ");
                event.setEventTime(sensorData.getDateandtime());
                event.setEventTimeStamp(sensorEvent.timestamp);
                Event.insert(event);
                Snackbar.make(findViewById(R.id.fab), "Harsh Break using Acc", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(canStore){
            SensorData.insert(sensorData);
            }
            this.sensorData.add(0,sensorData);
                truckInfoLogDataAdapter.notifyItemInserted(0);
                truckDataList.scrollToPosition(0);
                if(this.sensorData.size()>20){
                    this.sensorData.remove(20);
                    truckInfoLogDataAdapter.notifyItemRemoved(20);
                }

//                if (xVal < -3.0f) {
//
//                } else if (xVal > 3.0f) {
//
//                }
//                if (yVal < -3.0f) {
//                    Snackbar.make(findViewById(R.id.fab), "Hard Left Turn", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                } else if (yVal > 3.0f) {
//                    Snackbar.make(findViewById(R.id.fab), "Hard Right Turn", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//
//                if (zVal < 8.0f) {
//                    Snackbar.make(findViewById(R.id.fab), "Hard Up down", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//                else if (zVal > 11.0f) {
//                    Snackbar.make(findViewById(R.id.fab), "Hard Down up ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
            }
            xVal =  sensorEvent.values[0];
            yVal =  sensorEvent.values[1];
            zVal =  sensorEvent.values[2];
            String sx = "X Value : <font color = '#800080'> " + xVal + "</font>";
            String sy = "Y Value : <font color = '#800080'> " + yVal + "</font>";
            String sz = "Z Value : <font color = '#800080'> " + zVal + "</font>";


            x_dir.setText(Html.fromHtml(sx));
            y_dir.setText(Html.fromHtml(sy));
            z_dir.setText(Html.fromHtml(sz));


        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_GRAVITY){
            gravity[0]=sensorEvent.values[0];
            gravity[1]=sensorEvent.values[1];
            gravity[2]=sensorEvent.values[2];
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
