package lundy.com.survivor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.screen_splash);

        // langsung pindah ke MainActivity atau activity lain begitu memasuki
        // splash screen ini

      Intent intent = new Intent(this, Beranda.class);
      startActivity(intent);
      finish();
    }
}
