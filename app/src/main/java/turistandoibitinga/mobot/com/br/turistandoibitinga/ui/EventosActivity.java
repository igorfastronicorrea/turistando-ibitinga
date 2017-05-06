package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 05/05/2017.
 */

public class EventosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_eventos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEvento);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        //Seta a seta (Button) Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        collapsingToolbarLayout.setTitle("CPM 22");

        collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.colorTuristandoBranco));
        collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.colorTuristandoBranco));
        //desativa titulo actionBar
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
