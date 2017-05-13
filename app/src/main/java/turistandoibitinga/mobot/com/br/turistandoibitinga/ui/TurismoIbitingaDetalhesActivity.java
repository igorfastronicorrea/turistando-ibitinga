package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerTurismoFotos;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterFotosTurismo;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.ImagemDataTurismo;

/**
 * Created by igorf on 13/05/2017.
 */

public class TurismoIbitingaDetalhesActivity extends AppCompatActivity implements RecycleViewOnClickListenerTurismoFotos {

    private String id, api, descricao, foto_capa, facebook, faceid, endereco, lat, log;
    private ImageView imgDetalhesTurismo, facebookTurismo;
    private TextView txtDescricaoTurismo, txtEnderecoTurismo;
    private LinearLayout layoutFaceTurismo, layoutEnderecoTurismo;
    MapView mMapView;
    private GoogleMap googleMap;
    private View view_endereco_turismo;

    //fotos
    private RecyclerView recyclerViewTurismo;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterFotosTurismo adapter;
    private List<ImagemDataTurismo> data_list;
    String idTurismoFoto, nome_activity, api_detalhes_fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_turismoibitinga_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalhesTurismo);
        setSupportActionBar(toolbar);

        //Setando componentes da tela
        //seta imagem da capa no fragment - todos
        imgDetalhesTurismo = (ImageView) findViewById(R.id.imgDetalhesTurismo);
        facebookTurismo = (ImageView) findViewById(R.id.facebookTurismo);
        txtEnderecoTurismo = (TextView) findViewById(R.id.txtEnderecoTurismo);
        txtDescricaoTurismo = (TextView) findViewById(R.id.txtDescricaoTurismo);
        layoutEnderecoTurismo = (LinearLayout) findViewById(R.id.layoutEnderecoTurismo);
        layoutFaceTurismo = (LinearLayout) findViewById(R.id.layoutFaceTurismo);
        view_endereco_turismo = (View) findViewById(R.id.view_endereco_turismo);
        recyclerViewTurismo = (RecyclerView) findViewById(R.id.recycler_view_fotos_turismo);
        data_list = new ArrayList<>();


        //Recebe o id, api da classe TurismoIbitingaActivity
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        api = intent.getStringExtra("api");
        //referente as fotos
        api_detalhes_fotos = intent.getStringExtra("api_detalhes_fotos");
        nome_activity = intent.getStringExtra("nome_activity");


        carregaDados(Integer.parseInt(id));
        carregaDadosFotos(Integer.parseInt(id));

        //Seta a seta (Button) Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Definindo quando o scroll rolar muda o nome do title
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_detalhes_turismo);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.myappbar_detalhes_turismo);
        collapsingToolbarLayout.setTitle("");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(nome_activity);
                    Context context = TurismoIbitingaDetalhesActivity.this;
                    collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(nome_activity);//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });



        mMapView = (MapView) findViewById(R.id.mapViewTurismo);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(TurismoIbitingaDetalhesActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Fotos
        Context context = TurismoIbitingaDetalhesActivity.this;
        linearLayoutManager = new LinearLayoutManager(context);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerViewTurismo.setLayoutManager(new GridLayoutManager( TurismoIbitingaDetalhesActivity.this, 2));
        recyclerViewTurismo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new CustomAdapterFotosTurismo(context, data_list);
        adapter.setRecycleViewOnClickListener(TurismoIbitingaDetalhesActivity.this);
        recyclerViewTurismo.setAdapter(adapter);

        recyclerViewTurismo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    //progressBar.setVisibility(View.VISIBLE);
                    carregaDadosFotos(data_list.get(data_list.size() - 1).getId());
                }

            }
        });



    }

    private void carregaDados(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api +".php?id=" + id)
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        descricao = object.getString("descricao");
                        foto_capa = object.getString("foto_capa");
                        facebook = object.getString("facebook");
                        faceid = object.getString("faceid");
                        endereco = object.getString("endereco");
                        lat = object.getString("lat");
                        log = object.getString("log");

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

                Context context = TurismoIbitingaDetalhesActivity.this;

                txtDescricaoTurismo.setText(descricao);

                Picasso.with(context).load(foto_capa).into(imgDetalhesTurismo);

                if(endereco != "null"){
                    layoutEnderecoTurismo.setVisibility(View.VISIBLE);
                    view_endereco_turismo.setVisibility(View.VISIBLE);
                    txtEnderecoTurismo.setText(endereco);

                    //chama o app Google maps ao clicar sobre o endereço
                    txtEnderecoTurismo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("geo:<"+ lat + ">,<" + log + ">?q=<" + lat + ">,<" + log + ">(" + "" + ")" ));
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
                            googleMap.addMarker(new MarkerOptions().position(local).title(""));

                            // For zooming automatically to the location of the marker
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(local).zoom(15).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });

                }

                //Redes Sociais - Facebook
                if(facebook != "null"){
                    facebookTurismo.setVisibility(View.VISIBLE);
                    layoutFaceTurismo.setVisibility(View.VISIBLE);

                    facebookTurismo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                Context context = TurismoIbitingaDetalhesActivity.this;
                                context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + faceid));
                                startActivity(intent);
                            } catch (Exception e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + facebook)));
                            }

                        }
                    });

                }


            }
        };

        task.execute(id);
    }



    private void carregaDadosFotos(final int id) {

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

                        ImagemDataTurismo data = new ImagemDataTurismo(object.getInt("id"),
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
                //progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }


    @Override
    public void onClickListener(View view, int position) {
        Intent i = new Intent(TurismoIbitingaDetalhesActivity.this, DetailFotoZoom.class);
        String posicao = String.valueOf(position);
        i.putExtra("id", id);
        i.putExtra("position", posicao);
        i.putExtra("api_detalhes_fotos", api_detalhes_fotos);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
