package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.ImageAdapter;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.Fotos;

/**
 * Created by igorf on 21/04/2017.
 */

public class DetailFotoZoom extends AppCompatActivity {

    String[] fotosGaleria;
    String foto1, foto2, foto3, foto4, foto5;
    private List<Fotos> data_list;
    private ImageAdapter adapter;
    private String idEmpresa, position, api_detalhes_fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Esconde o StatusBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_detalhes_foto_empresa);

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

        //Recebe os parametros da tela FragmentFotosEmpresa
        Intent intent = getIntent();
        idEmpresa = intent.getStringExtra("id");
        position = intent.getStringExtra("position");
        api_detalhes_fotos = intent.getStringExtra("api_detalhes_fotos");

        data_list = new ArrayList<>();
        carregaDados(Integer.parseInt(idEmpresa));
        adapter = new ImageAdapter(this, data_list);

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


    private void carregaDados(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_detalhes_fotos +".php?id=" + id)
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        Fotos data = new Fotos(object.getInt("id"),
                                object.getString("nome_foto"));

                        data_list.add(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

                adapter.notifyDataSetChanged();
                ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(Integer.parseInt(position));
            }
        };

        task.execute(id);
    }
}
