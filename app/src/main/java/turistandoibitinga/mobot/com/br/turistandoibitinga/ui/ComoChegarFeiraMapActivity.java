package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
 * Created by igorf on 04/05/2017.
 */

public class ComoChegarFeiraMapActivity extends AppCompatActivity {

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_como_chegar_map);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMenuFeiraComoChegar);
        setSupportActionBar(toolbar);

        Context context = ComoChegarFeiraMapActivity.this;
        toolbar.setTitleTextColor( ContextCompat.getColor(context, R.color.colorTuristandoBranco));
        toolbar.setTitle("Como Chegar");

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

        final String lat = "-21.766812";
        final String log = "-48.830385";
        final String nome = "Pavilhão de Exposição, Ibitinga";

        mMapView = (MapView) findViewById(R.id.mapComoChegar);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                // For showing a move to my location button
                googleMap.setMyLocationEnabled(false);

                // For dropping a marker at a point on the Map
                LatLng local = new LatLng(Double.parseDouble(lat), Double.parseDouble(log));
                googleMap.addMarker(new MarkerOptions().position(local).title(nome));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(local).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }
}