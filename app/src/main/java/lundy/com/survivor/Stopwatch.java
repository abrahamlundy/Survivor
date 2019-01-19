package lundy.com.survivor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class Stopwatch extends AppCompatActivity implements SensorEventListener{

    Chronometer mChrometer;
    Button start, stop, restart;
    private long timeWhenStopped=0;
    private Sensor step;
    private SensorEventListener StepListener;
    private SensorManager mSensorManager;
    final float[] steppingFloat= new float[1] ;
    private TextView tvAzimuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Stop Watch");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mChrometer= findViewById(R.id.simpleChronometer);
        start = findViewById(R.id.btn_play);
        stop = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_reset);
        tvAzimuth=findViewById(R.id.textView);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            step = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Toast.makeText(this, "Step Sensor Available !", Toast.LENGTH_SHORT).show();
            mSensorManager.registerListener(this,step,SensorManager.SENSOR_DELAY_NORMAL);
        }



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChrometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                 mChrometer.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWhenStopped = mChrometer.getBase() - SystemClock.elapsedRealtime();
                mChrometer.stop();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChrometer.setBase(SystemClock.elapsedRealtime());

            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steppingFloat[0] = event.values[0];
            tvAzimuth.setText("Jumlah Langkah : "+String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        //app kembali, daftarkan lagi listener
        super.onResume();
        mSensorManager.registerListener(StepListener, step,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        //app kembali, daftarkan lagi listener
        super.onStop();
        mSensorManager.unregisterListener(StepListener);

    }


}
