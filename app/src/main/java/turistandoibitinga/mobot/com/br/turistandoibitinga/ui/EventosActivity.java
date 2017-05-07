package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.Fotos;

/**
 * Created by igorf on 05/05/2017.
 */

public class EventosActivity extends AppCompatActivity {

    private LinearLayout layout_endereco_evento;
    MapView mMapView;
    private GoogleMap googleMap;
    private TextView txtEnderecoEvento;
    private String lat, log, local_evento, api_evento;
    private String nome, dia, mes, ano, horario,;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_eventos);

        carregaDados(0);

        //Recupera os detalhes vindo da tela anterior ListarEventosActivity
        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        lat = intent.getStringExtra("lat");
        log = intent.getStringExtra("log");
        local_evento = intent.getStringExtra("local_evento");

        //Defini toolbar etc
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

        //Nome no collapsing
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(nome);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.colorTuristandoBranco));
        collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.colorTuristandoBranco));

        mMapView = (MapView) findViewById(R.id.mapViewEvento);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(EventosActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Definindo componentes da tela FINDVIEWBYID
        layout_endereco_evento = (LinearLayout) findViewById(R.id.layout_endereco_evento);
        txtEnderecoEvento = (TextView) findViewById(R.id.txtEnderecoEvento);



        //chama o app Google maps ao clicar sobre o endereço
        txtEnderecoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:<"+ lat + ">,<" + log + ">?q=<" + lat + ">,<" + log + ">(" + local_evento + ")" ));
                startActivity(intent);
            }
        });

        mMapView.setVisibility(View.VISIBLE);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(false);

                // For dropping a marker at a point on the Map
                LatLng local = new LatLng(Double.parseDouble(lat), Double.parseDouble(log));
                googleMap.addMarker(new MarkerOptions().position(local).title(local_evento));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(local).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }


    private void carregaDados(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_evento +".php?id=" + id)
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        object.getString()
                        /*Fotos data = new Fotos(object.getInt("id"),
                                object.getString("nome_foto"));*/

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
