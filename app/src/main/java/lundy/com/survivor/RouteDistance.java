package lundy.com.survivor;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class RouteDistance extends AppCompatActivity {
    private static final int RC_BTN_A = 0;
    private static final int RC_BTN_B = 1;
    public static final String KEY_LAT = "key_lat";
    public static final String KEY_LNG = "key_lng";
    public static final String KEY_LAT_START = "key_lat_start";
    public static final String KEY_LNG_START = "key_lng_start";
    public static final String KEY_LAT_END = "key_lat_end";
    public static final String KEY_LNG_END = "key_lng_end";
    float bearTo;
/*
   @BindView(R.id.tvLokasiA)
    TextView tvLokasiA;
    @BindView(R.id.tvLokasiB)
    TextView tvLokasiB;
*/
  TextView tvLokasiA,tvLokasiB;
Button Hitung;
    private double latStart, lngStart, latEnd, lngEnd;

    private Intent intentMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_distance);


        ButterKnife.bind(this);
        tvLokasiA=findViewById(R.id.tvLokasiA);
        tvLokasiB=findViewById(R.id.tvLokasiB);
        Hitung = findViewById(R.id.btnHitung);

        intentMaps = new Intent(this, SeeOnMaps.class);

        Hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent dan Variabel ekstra yang dilempar
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(RouteDistance.KEY_LAT_START, latStart);
                intent.putExtra(RouteDistance.KEY_LNG_START, lngStart);
                intent.putExtra(RouteDistance.KEY_LAT_END, latEnd);
                intent.putExtra(RouteDistance.KEY_LNG_END, lngEnd);
               v.getContext().startActivity(intent);
                Location userLoc = new Location("one");
                userLoc.setLatitude(latStart);
                userLoc.setLongitude(lngStart);
                Location destinationLoc = new Location("two");
                destinationLoc.setLatitude(latEnd);
                destinationLoc.setLongitude(lngEnd);

                bearTo=userLoc.bearingTo(destinationLoc);
                //bearTo= (bearTo+360)%360;
                Toast.makeText(v.getContext(),String.valueOf(bearTo),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtnAClick(View v) {
        startActivityForResult(intentMaps, RC_BTN_A);
    }

    public void onBtnBClick(View v) {
        startActivityForResult(intentMaps, RC_BTN_B);
    }


    /*
    public void onBtnHitungClick() {
        Intent intent = new Intent(this, DirectionActivity.class);
        intent.putExtra(RouteDistance.KEY_LAT_START, latStart);
        intent.putExtra(RouteDistance.KEY_LNG_START, lngStart);
        intent.putExtra(RouteDistance.KEY_LAT_END, latEnd);
        intent.putExtra(RouteDistance.KEY_LNG_END, lngEnd);

    }
*/
    //Fungsi untuk mengambil hasil lokas yang ditunjuk Marker Maps pada activity selanjutnya
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final double lat = data.getDoubleExtra(KEY_LAT, 0);
            final double lng = data.getDoubleExtra(KEY_LNG, 0);

            switch (requestCode) {
                case RC_BTN_A:
                    tvLokasiA.setText("lat: " + lat + " lng: " + lng);
                    latStart = lat;
                    lngStart = lng;
                    break;
                case RC_BTN_B:
                    tvLokasiB.setText("lat: " + lat + " lng: " + lng);
                    latEnd = lat;
                    lngEnd = lng;
                    break;
            }
        }
    }
}
