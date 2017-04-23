package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.ImageAdapter;

/**
 * Created by igorf on 21/04/2017.
 */

public class DetailFotoZoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esconde o StatusBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_detalhes_foto_empresa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_zoom);
        setSupportActionBar(toolbar);

        int[] GalImages = new int[]{
                R.drawable.cat_camamesabanho,
                R.drawable.instagram,
                R.drawable.icon_whats,

                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um,
                R.drawable.um
        };

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, GalImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        //Seta a seta (Button) Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
