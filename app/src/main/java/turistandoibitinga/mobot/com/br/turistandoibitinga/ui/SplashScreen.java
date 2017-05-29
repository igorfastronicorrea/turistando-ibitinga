package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 28/05/2017.
 */

public class SplashScreen extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    public void run() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
