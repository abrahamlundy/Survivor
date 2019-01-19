package lundy.com.survivor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class PushUp extends AppCompatActivity implements SensorEventListener{

    Chronometer mChrometer;
    Button start, stop, restart,simpan;
    TextView tvPush;
    private long timeWhenStopped=0;
    int pushCounter=0;
    private Sensor lights;
    private Sensor proximit;
    private SensorManager mSensorManager;
    private SensorEventListener lightsListener;
    private SensorEventListener ProxiListener;
    final float[] lightsFloat = new float[1];
    private float[] proxiFloat= new float[1];
    boolean near,dimm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Push Up");

       // pushCounter=0;
        near=false;
        dimm=false;

        mChrometer= findViewById(R.id.simpleChronometer);
        start = findViewById(R.id.btn_play);
        stop = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_reset);
        simpan = findViewById(R.id.btn_simpan);
        tvPush = findViewById(R.id.tv_push);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lights = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Snackbar.make(getWindow().getDecorView().getRootView(),"Light Available",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximit = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            // Snackbar.make(getWindow().getDecorView().getRootView(),"Proximity Available",Snackbar.LENGTH_SHORT).show();
            Snackbar.make(getWindow().getDecorView().getRootView(),"Proximity Available",Snackbar.LENGTH_SHORT).setAction("Action",null).show();

        }

        //register listener
        mSensorManager.registerListener(this, lights,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, proximit,
                SensorManager.SENSOR_DELAY_NORMAL);


        /* ==== Aksi untuk Tombol-tombol pada activity====*/
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChrometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                mChrometer.start();
                onResume();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWhenStopped = mChrometer.getBase() - SystemClock.elapsedRealtime();
                mChrometer.stop();
                onStop();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChrometer.setBase(SystemClock.elapsedRealtime());
                pushCounter=0;

            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    OpenHelper dbHelper =  new OpenHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues newValues = new ContentValues();
                    newValues.put("GERAK", "Push Up");
                    newValues.put("GERAKAN", pushCounter);
                    //newValues.put("DATA3", az);
                    db.insert("ACTIVITY", null, newValues);

                    Cursor cur = null;
              //  OpenHelper dbHelper =  new OpenHelper(getApplicationContext());
                db = dbHelper.getReadableDatabase();
                String[] cols = new String [] {"ID", "GERAK","GERAKAN"};
                cur = db.query("ACTIVITY",cols,null,null,null,null,null);
                String gab = "";
                if (cur.getCount()>0) {  //ada data? ambil
                    cur.moveToFirst();
                    int data1 = cur.getInt(1);
                    String data2 = cur.getString(2);
                    String data3 = cur.getString(3);
                    gab = Integer.toString(data1)+":"+data3;
                    Toast.makeText(getApplicationContext(),gab,Toast.LENGTH_SHORT).show();
                }

                    db.close();
            }
        });
        //Finish
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_LIGHT) {
            lightsFloat[0]=event.values[0];
        }

        if (event.sensor.getType()==Sensor.TYPE_PROXIMITY) {
            proxiFloat[0]=event.values[0];

        }

    //Fungsi penghitungan
        if(lightsFloat[0]<5){
            dimm=true;
        }else{
            dimm=false;
//            Toast.makeText(getApplicationContext(),String.valueOf(near),Toast.LENGTH_SHORT).show();
        }

        if(proxiFloat[0]<3){
            near=true;
        }else {
            near=false;
  //          Toast.makeText(getApplicationContext(),String.valueOf(near),Toast.LENGTH_SHORT).show();

        }

        if(near){
            pushCounter++;
        }

        tvPush.setText("Push Anda : "+String.valueOf(pushCounter));

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        //lepaskan listener sensor
        super.onPause();
        mSensorManager.unregisterListener(lightsListener);
        mSensorManager.unregisterListener(ProxiListener);
        //releasenya masih error kalau ditekan back
      //  mWakeLock.release();
    }

    @Override
    protected void onResume() {
        //app kembali, daftarkan lagi listener
        super.onResume();
        mSensorManager.registerListener(lightsListener, lights,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(ProxiListener, proximit,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        //app kembali, daftarkan lagi listener
        super.onStop();
        mSensorManager.unregisterListener(lightsListener);
        mSensorManager.unregisterListener(ProxiListener);
       // mWakeLock.release();
    }

}
