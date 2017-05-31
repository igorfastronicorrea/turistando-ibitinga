package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 29/05/2017.
 */

public class DesenvolvimentoActivity extends AppCompatActivity {

    private ImageView imgMobot, imgTaina, imgPedro;
    private Context context = DesenvolvimentoActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_desenvolvimento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDesenvolvimento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Desenvolvimento");
        toolbar.setTitleTextColor(Color.WHITE);

        imgMobot = (ImageView) findViewById(R.id.imgMobot);
        imgTaina = (ImageView) findViewById(R.id.imgTaina);
        imgPedro = (ImageView) findViewById(R.id.imgPedro);


        int[] logo = {R.drawable.icone_mobot, R.drawable.logo_taina, R.drawable.logo_prefeitura_ibitinga};


        Picasso.with(context).load(logo[0]).into(imgMobot);
        Picasso.with(context).load(logo[1]).into(imgTaina);
        Picasso.with(context).load(logo[2]).into(imgPedro);

        imgMobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "338784833186387"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + "mobotapp")));
                }

            }
        });


        imgTaina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "1086729001373367"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + "DesignerTainaMeneguesso")));
                }

            }
        });

        imgPedro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "743359529081478"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + "prefeituraibitinga")));
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
