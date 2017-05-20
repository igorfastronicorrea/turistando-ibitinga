package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 20/05/2017.
 */

public class PoliticaPrivacidade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_politica_privacidade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPoliticaPrivacidade);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Política de Privacidade");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
