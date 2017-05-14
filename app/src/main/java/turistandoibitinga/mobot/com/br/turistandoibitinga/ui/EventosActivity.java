package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

import static turistandoibitinga.mobot.com.br.turistandoibitinga.R.id.facebookEvento;

/**
 * Created by igorf on 05/05/2017.
 */

public class EventosActivity extends AppCompatActivity {

    private LinearLayout layout_endereco_evento;
    MapView mMapView;
    private GoogleMap googleMap;
    private TextView txtEnderecoEvento, txtDiaEvento, txtMesEvento, txtAnoEvento, txtHorarioEvento, txtLocalEvento, txtDescricaoEvento;
    private String lat, log, local_evento, api_detalhes_evento;
    private String id, fotoCapaEvento, nome, dia, mes, ano, horario, descricao, foto1, foto2, facebook, faceid, endereco;
    private ImageView imgFotoCapaEvento, imgFotoEvento1, imgFotoEvento2, imgFacebookEvento;
    private LinearLayout layoutFaceEvento, layoutEnderecoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_eventos);

        //
        //COMPONENENTES DE TELA
        //
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

        //Definindo componentes da tela FINDVIEWBYID
        layoutEnderecoEvento = (LinearLayout) findViewById(R.id.layoutEnderecoEvento);
        txtEnderecoEvento = (TextView) findViewById(R.id.txtEnderecoEvento);
        imgFotoCapaEvento = (ImageView) findViewById(R.id.imgFotoCapaEvento);
        txtDiaEvento = (TextView) findViewById(R.id.txtDiaEvento);
        txtMesEvento = (TextView) findViewById(R.id.txtMesEvento);
        txtAnoEvento = (TextView) findViewById(R.id.txtAnoEvento);
        txtHorarioEvento = (TextView) findViewById(R.id.txtHorarioEvento);
        txtLocalEvento = (TextView) findViewById(R.id.txtLocalEvento);
        txtDescricaoEvento = (TextView) findViewById(R.id.txtDescricaoEvento);
        imgFotoEvento1 = (ImageView) findViewById(R.id.fotoEvento1);
        imgFotoEvento2 = (ImageView) findViewById(R.id.fotoEvento2);
        imgFacebookEvento = (ImageView) findViewById(facebookEvento);
        layoutFaceEvento = (LinearLayout) findViewById(R.id.layoutFaceEvento);


        //Recupera os detalhes vindo da tela anterior ListarEventosActivity
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nome = intent.getStringExtra("nome");
        lat = intent.getStringExtra("lat");
        log = intent.getStringExtra("log");
        local_evento = intent.getStringExtra("local_evento");
        api_detalhes_evento = intent.getStringExtra("api_detalhes_evento");

        carregaDados(Integer.parseInt(id));

        //Nome no collapsing
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(nome);
        Context context = EventosActivity.this;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));


        //
        //GOOGLE MAPS
        //
        mMapView = (MapView) findViewById(R.id.mapViewEvento);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(EventosActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void carregaDados(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_detalhes_evento + ".php?id=" + id)
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        fotoCapaEvento = object.getString("foto_capa");
                        dia = object.getString("dia");
                        mes = object.getString("mes");
                        ano = object.getString("ano");
                        horario = object.getString("hora");
                        descricao = object.getString("descricao");
                        foto1 = object.getString("foto1");
                        foto2 = object.getString("foto2");
                        facebook = object.getString("facebook");
                        faceid = object.getString("faceid");
                        endereco = object.getString("endereco");
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

                if(fotoCapaEvento != "null"){
                    Picasso.with(EventosActivity.this).load(fotoCapaEvento).into(imgFotoCapaEvento);
                    imgFotoCapaEvento.setVisibility(View.VISIBLE);
                }

                if (dia != "null") {
                    txtDiaEvento.setText(dia);
                    txtDiaEvento.setVisibility(View.VISIBLE);
                }

                if (mes != "null") {
                    txtMesEvento.setText(mes);
                    txtMesEvento.setVisibility(View.VISIBLE);
                }

                if (ano != "null") {
                    txtAnoEvento.setText(ano);
                    txtAnoEvento.setVisibility(View.VISIBLE);
                }

                if (horario != "null") {
                    txtHorarioEvento.setText(horario);
                    txtHorarioEvento.setVisibility(View.VISIBLE);
                }

                if (local_evento != "null") {
                    txtLocalEvento.setText(local_evento);
                    txtLocalEvento.setVisibility(View.VISIBLE);
                }

                if (descricao != "null") {
                    txtDescricaoEvento.setText(descricao);
                    txtDescricaoEvento.setVisibility(View.VISIBLE);
                }

                if (foto1 != "null") {
                    Picasso.with(EventosActivity.this).load(foto1).into(imgFotoEvento1);
                    imgFotoEvento1.setVisibility(View.VISIBLE);

                    imgFotoEvento1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent imagemFoto1 = new Intent(EventosActivity.this, DetailEventoZoom.class);
                            imagemFoto1.putExtra("foto", foto1);
                            startActivity(imagemFoto1);
                        }
                    });
                }

                if (foto2 != "null") {
                    Picasso.with(EventosActivity.this).load(foto2).into(imgFotoEvento2);
                    imgFotoEvento2.setVisibility(View.VISIBLE);

                    imgFotoEvento2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent imagemFoto2 = new Intent(EventosActivity.this, DetailEventoZoom.class);
                            imagemFoto2.putExtra("foto", foto2);
                            startActivity(imagemFoto2);
                        }
                    });
                }

                if (facebook != "null") {
                    layoutFaceEvento.setVisibility(View.VISIBLE);
                    imgFacebookEvento.setVisibility(View.VISIBLE);

                    imgFacebookEvento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                Context context = EventosActivity.this;
                                context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + faceid));
                                startActivity(intent);
                            } catch (Exception e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + facebook)));
                            }
                        }
                    });
                }

                if (endereco != "null") {
                    txtEnderecoEvento.setText(endereco);
                    layoutEnderecoEvento.setVisibility(View.VISIBLE);
                    //chama o app Google maps ao clicar sobre o endereço
                    txtEnderecoEvento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("geo:<" + lat + ">,<" + log + ">?q=<" + lat + ">,<" + log + ">(" + local_evento + ")"));
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


            }
        };

        task.execute(id);
    }


}
