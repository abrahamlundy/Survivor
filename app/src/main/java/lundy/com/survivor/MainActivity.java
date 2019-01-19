package lundy.com.survivor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvPitch,tvAzimuth;
    private Sensor lights;
    private Sensor step;
    private Sensor proximit;
    private SensorManager mSensorManager;
    private SensorEventListener lightsListener;
    private SensorEventListener StepListener;
    private SensorEventListener ProxiListener;
    private final float[] steppingFloat = new float[1];
    private WindowManager.LayoutParams params ;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private PowerManager.WakeLock mWakeLock;

    /* ============= Hanya untuk Uji Coba pembacaan Sensor ============*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        params = this.getWindow().getAttributes();
        params = this.getWindow().getAttributes();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, getClass().getName());

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lights = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Toast.makeText(this, "Light Sensor Available !", Toast.LENGTH_SHORT).show();
        } else {

        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            step = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Toast.makeText(this, "Step Sensor Available !", Toast.LENGTH_SHORT).show();
        } else {

        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximit = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
           // Snackbar.make(getWindow().getDecorView().getRootView(),"Proximity Available",Snackbar.LENGTH_SHORT).show();
            Snackbar.make(getWindow().getDecorView().getRootView(),"Proximity Available",Snackbar.LENGTH_SHORT).setAction("Action",null).show();

        } else {

        }

        //Handling Light Sensr

        tvPitch = findViewById(R.id.tv_hasil);
        final float[] lightsFloat = new float[1];
        lightsListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    lightsFloat[0] = event.values[0];
                    tvPitch.setText(String.valueOf(lightsFloat[0]));
                }

                if(lightsFloat[0]<10){
                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                    getWindow().setStatusBarColor(Color.WHITE);
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

        //Handling Step Sensor
        tvAzimuth= findViewById(R.id.tv_hasil2);
        StepListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                    steppingFloat[0] = event.values[0];
                    tvAzimuth.setText(String.valueOf(steppingFloat[0]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

        ProxiListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){

                    /* === INI ADALAH PERINTAH UNTUK MENURUNKAN KECERAHAN LAYAR  ====*/

                   if(event.values[0]<=3&& event.values[0]>1){
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = 0;
                        getWindow().setAttributes(params);

                      //INI UNTUK MENJALANKAN WAKELOCK YANG SUDAH DIDAFTARKAN


                   }else if(event.values[0]<=1){
                        mWakeLock.acquire();

                   } else{
                        /*INI ADALAH BAGIAN YANG SAMA*/
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = -1f;
                        getWindow().setAttributes(params);

                    }

                }
                Snackbar.make(getWindow().getDecorView().getRootView(),"max :"+ proximit.getMaximumRange(),Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };


    }


    @Override
        protected void onPause() {
            //app kehilangan fokus (misal user menerima telp),
            //lepaskan listener sensor
            super.onPause();
            mSensorManager.unregisterListener(lightsListener);
            mSensorManager.unregisterListener(StepListener);
            mSensorManager.unregisterListener(ProxiListener);
           //releasenya masih error kalau ditekan back
            mWakeLock.release();
        }

    @Override
        protected void onResume() {
            //app kembali, daftarkan lagi listener
            super.onResume();
            mSensorManager.registerListener(lightsListener, lights,
                    SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(StepListener, step,
                    SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(ProxiListener, proximit,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onStop() {
            //app kembali, daftarkan lagi listener
            super.onStop();
            mSensorManager.unregisterListener(lightsListener);
            mSensorManager.unregisterListener(StepListener);
            mSensorManager.unregisterListener(ProxiListener);
            mWakeLock.release();
        }

}
