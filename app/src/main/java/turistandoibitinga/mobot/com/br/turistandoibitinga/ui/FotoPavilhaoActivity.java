package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by igorf on 04/05/2017.
 */

public class FotoPavilhaoActivity extends AppCompatActivity {

    private ImageView imgPavilhao;
    private String blocoPavilhao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Esconde o StatusBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_pavilhao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_zoom);
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


        //Recebendo a imagem
        Intent intent = getIntent();
        blocoPavilhao = intent.getStringExtra("blocoPavilhao");


        //setando imagem
        imgPavilhao = (ImageView) findViewById(R.id.imgPavilhao);

        final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgPavilhao);

        if (blocoPavilhao.equals("foto_pavilhao_a")) {
            imgPavilhao.setImageResource(R.mipmap.menu_pavilhao_a);
        } else if (blocoPavilhao.equals("foto_pavilhao_b")) {
            imgPavilhao.setImageResource(R.mipmap.menu_pavilhao_b);
        } else if (blocoPavilhao.equals("foto_pavilhao_c")) {
            imgPavilhao.setImageResource(R.mipmap.menu_pavilhao_c);
        }


        photoViewAttacher.update();


    }

}
