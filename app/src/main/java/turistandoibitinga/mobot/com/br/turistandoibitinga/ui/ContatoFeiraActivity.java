package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 05/05/2017.
 */

public class ContatoFeiraActivity extends AppCompatActivity {

    private TextView txtEmailContatoFeira, txtTel1ContatoFeira, txtTel2ContatoFeira;
    private ImageView facebookContatoFeira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contato_feira);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarContatoFeira);
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

        //desativa titulo actionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //CONTATO FACEBOOK
        facebookContatoFeira = (ImageView) findViewById(R.id.facebookContatoFeira);
        facebookContatoFeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Context context = ContatoFeiraActivity.this;
                    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "1545753635455600"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + "Feira-do-Bordado-de-Ibitinga-1545753635455600")));
                }

            }
        });



        //CONTATO FEIRA
        txtEmailContatoFeira = (TextView) findViewById(R.id.txtContatoEmailFeira);
        txtEmailContatoFeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@feira.com.br"});
                startActivity(Intent.createChooser(intent, ""));
            }
        });


        //CONTATO TELEFONE 1
        txtTel1ContatoFeira = (TextView) findViewById(R.id.txtTel1ContatoFeira);
        txtTel1ContatoFeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ "1633412719"));
                startActivity(callIntent );
            }
        });


        //CONTATO TELEFONE 2
        txtTel2ContatoFeira = (TextView) findViewById(R.id.txtTel2ContatoFeira);
        txtTel2ContatoFeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ "16991377161"));
                startActivity(callIntent );
            }
        });


    }

}
