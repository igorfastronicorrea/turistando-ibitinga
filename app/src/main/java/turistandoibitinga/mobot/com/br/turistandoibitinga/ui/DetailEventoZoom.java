package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by igorf on 06/05/2017.
 */

public class DetailEventoZoom extends AppCompatActivity {

    private ImageView imgFotoZoomEvento;
    private String imagemFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Esconde o StatusBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_detalhes_foto_evento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_zoom_evento);
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
        getSupportActionBar().setTitle("");

        //Recebe imagem da tela EventosActivity
        Intent i = getIntent();
        imagemFoto = i.getStringExtra("foto");

        imgFotoZoomEvento = (ImageView) findViewById(R.id.imgFotoZoomEvento);

        //Carrega a iamgem
        Picasso.with(DetailEventoZoom.this).load(imagemFoto).into(imgFotoZoomEvento);

        //Defini o Zoom
        final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgFotoZoomEvento);
        photoViewAttacher.update();

    }
}
