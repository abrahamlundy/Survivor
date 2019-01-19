package lundy.com.survivor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends AppCompatActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor accel;
    private Sensor lin_accel;
    private Sensor magneto;
    private Sensor lights;
    // define the display assembly compass picture
    private ImageView image;
    private TextView Azimuth;

    // record the compass picture angle turned
    private float currentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        /* === Deklarasi untuk Toolbar Custom */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Compass"); //untuk memberi nama pada activity

        /* === Deklarasi unutk sensor Manager mengambil Service Sensor */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        image= findViewById(R.id.img_compass);
        Azimuth= findViewById(R.id.tvCompass);

        //masih 1 parameter sensor
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){

            //device memiliki accelerometer,lanjutkan
            accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            lin_accel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            magneto = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            //register listener
            mSensorManager.registerListener(this, accel,
                    SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, lin_accel,
                    SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, magneto,
                    SensorManager.SENSOR_DELAY_NORMAL);
         //   tvAzimuth = findViewById(R.id.tvhasil);
            // tvPitch = (TextView) findViewById(R.id.tvPitch);
            // tvRoll = (TextView) findViewById(R.id.tvRoll);
        }
        else {
            //tidak punya sensor , tampilkan pesan error
        }

    }



    float[] mGravity;
    float[] mGeomagnetic;
    float azimuth;
    float pitch;
    float roll;

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values.clone();
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values.clone();

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            //ambil rotationmatrix
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                //dengan Math.toDegreee hasilnya -180 sd 180
                azimuth = (float) Math.toDegrees((double) orientation[0]);
                //dikonversi jadi 0 sd 360
                // 0=Utara, 90=Timur, 180=Selatan, 270=Barat
                azimuth = (azimuth + 360) % 360;
                //print dua angka belakang koma, agar mudah dibaca

                // tvAzimuth.setText("Azimuth="+String.format("%6.2f",azimuth));

                //pitch adalah rotasi kedepan & belakang
                //bayangkan pesawat yg sedang dogfight dikejar musuh
                //kemudian naik berputar sehingga berada di belakang musuhnya
                pitch = (float) Math.toDegrees((double) orientation[1]);
                pitch = (pitch + 360) % 360;

                //tvPitch.setText("Pitch="+String.format("%6.2f",pitch));

                //roll adalah rotasi ke kiri dan kekanan
                //bayangkan pesawat yg berputar utk berbelok kekiri dan ke kanan
                roll = (float) Math.toDegrees((double) orientation[2]);
                roll = (roll + 360) % 360;
                //tvRoll.setText("roll="+String.format("%6.2f",roll));

            }

        }

        /*======== Untuk membuat animasi perputaran gambar PNG jarum kompas ==========*/
        RotateAnimation ra = new RotateAnimation(currentDegree, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // kecepatan respon terhadap perubahan nilai
        ra.setDuration(10);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Memulai animasi
        image.startAnimation(ra);
        currentDegree = -azimuth;

        /*==== Untuk memberi nama arah mata angin sesuai range ====*/
        if (azimuth >= 355 || azimuth < 5){
            Azimuth.setText("North" +"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=5 && azimuth < 85){
            Azimuth.setText("North East"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=85 && azimuth < 95){
            Azimuth.setText("East"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=95 && azimuth < 175){
            Azimuth.setText("South East"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=175 && azimuth < 185){
            Azimuth.setText("South"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=185 && azimuth < 265){
            Azimuth.setText("South West"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=265 && azimuth < 275){
            Azimuth.setText("West"+"\n"+String.format("%6.2f",azimuth));
        }else if(azimuth >=275 && azimuth < 355){
            Azimuth.setText("North West"+"\n"+String.format("%6.2f",azimuth));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onPause() {
        //lepaskan listener sensor
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        //lanjutkan listener
        super.onResume();
        mSensorManager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, lin_accel,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magneto,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

}
