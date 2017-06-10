package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 09/06/2017.
 */

public class SobreNos extends AppCompatActivity {

    private ImageView face, whats, icLogo;
    private TextView email, tel;
    private String whatsApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sobrenos);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSobreNos);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Turistando Ibitinga");
        //toolbar.setTitleTextColor(Color.WHITE);

        face = (ImageView) findViewById(R.id.facebookSobreNos);
        whats = (ImageView) findViewById(R.id.whatsSobreNos);
        email = (TextView) findViewById(R.id.txtEmailSobreNos);
        tel = (TextView) findViewById(R.id.txtTel1SobreNos);
        icLogo = (ImageView) findViewById(R.id.icLogo);


        Picasso.with(SobreNos.this).load(R.mipmap.logo_turistando).into(icLogo);

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "603700046456176"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + "turistandoibitinga")));
                }
            }
        });

        whatsApp = "16991377661";

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!whatsApp.equals("null")) {
                    if (!contatoExiste(whatsApp)) {
                        ContentValues cv = new ContentValues();
                        cv.put(Contacts.People.NAME, "Turistando Ibitinga");
                        Uri u = getContentResolver().insert(Contacts.People.CONTENT_URI, cv);
                        Uri pathu = Uri.withAppendedPath(u, Contacts.People.Phones.CONTENT_DIRECTORY);
                        cv.clear();
                        cv.put(Contacts.People.NUMBER, whatsApp);
                        getContentResolver().insert(pathu, cv);
                        Toast.makeText(getApplicationContext(), "Contato Adicionado", Toast.LENGTH_LONG).show();

                        long start = new Date().getTime();
                        while (new Date().getTime() - start < 1500L) {
                        }

                        Uri uri = Uri.parse("smsto:" + whats);
                        Intent iu = new Intent(Intent.ACTION_SENDTO, uri);
                        iu.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(iu, ""));
                    } else {
                        Uri uri = Uri.parse("smsto:" + whats);
                        Intent iu = new Intent(Intent.ACTION_SENDTO, uri);
                        iu.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(iu, ""));
                    }
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"turistandoibitinga@gmail.com"});
                startActivity(Intent.createChooser(intent, ""));
            }
        });



            tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "16991377661"));
                    startActivity(callIntent);
                }
            });

    }

    public boolean contatoExiste(String number) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = getApplicationContext().getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
