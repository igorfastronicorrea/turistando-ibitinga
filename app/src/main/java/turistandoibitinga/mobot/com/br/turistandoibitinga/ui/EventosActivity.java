package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 05/05/2017.
 */

public class EventosActivity extends AppCompatActivity {

    private LinearLayout layout_endereco_evento;
    MapView mMapView;
    private GoogleMap googleMap;
    private TextView txtEnderecoEvento;
    private String lat, log, nome_evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_eventos);

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
        collapsingToolbarLayout.setTitle("CPM 22");
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


        layout_endereco_evento = (LinearLayout) findViewById(R.id.layout_endereco_evento);
        txtEnderecoEvento = (TextView) findViewById(R.id.txtEnderecoEvento);

        lat = "-21.766703";
        log = "-48.830363";
        nome_evento = "Posição de Exposições";

        //chama o app Google maps ao clicar sobre o endereço
        txtEnderecoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:<"+ lat + ">,<" + log + ">?q=<" + lat + ">,<" + log + ">(" + nome_evento + ")" ));
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
                googleMap.addMarker(new MarkerOptions().position(local).title(nome_evento));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(local).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }
}
