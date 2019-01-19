package lundy.com.survivor;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class PullUp extends AppCompatActivity {

    Chronometer mChrometer;
    Button start, stop, restart;
    private long timeWhenStopped=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_up);

        /* Deklarasi Custom Toolbar  */
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Pull Up"); //Judul Toolbar

        mChrometer= findViewById(R.id.simpleChronometer);
        start = findViewById(R.id.btn_play);
        stop = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_reset);

        /* ==== Aksi untuk Tombol-tombol pada activity====*/
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

        /* ==== Finish====*/
    }
}
