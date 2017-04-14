package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 03/04/2017.
 */

public class FragmentInformacoesEmpresa extends Fragment {

    private TextView txtNomeDetalhesEmpresa, txtSiteDetalhesEmpresa,txtEmailDetalhesEmpresa, txtTel1DetalhesEmpresa,
            txtTel2DetalhesEmpresa, txtHorarioDetalhesEmpresa, txtEnderecoDetalhesEmpresa;
    private ImageView facebookDetalhesEmpresa, instagramDetalhesEmpresa, whatsDetalhesEmpresa;
    private String idEmpresa, nome, face, faceid, insta, whats, site, email, tel1, tel2, horario, endereco, lat, log;

    MapView mMapView;
    private GoogleMap googleMap;



    public FragmentInformacoesEmpresa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_informacoes_empresa, container, false);

        txtNomeDetalhesEmpresa = (TextView) v.findViewById(R.id.txtNomeDetalhesEmpresa);
        txtSiteDetalhesEmpresa = (TextView) v.findViewById(R.id.txtSiteDetalhesEmpresa);
        txtEmailDetalhesEmpresa = (TextView) v.findViewById(R.id.txtEmailDetalhesEmpresa);
        txtTel1DetalhesEmpresa = (TextView) v.findViewById(R.id.txtTel1DetalhesEmpresa);
        txtTel2DetalhesEmpresa = (TextView) v.findViewById(R.id.txtTel2DetalhesEmpresa);
        txtHorarioDetalhesEmpresa = (TextView) v.findViewById(R.id.txtHorarioDetalhesEmpresa);
        txtEnderecoDetalhesEmpresa = (TextView) v.findViewById(R.id.txtEnderecoDetalhesEmpresa);


        facebookDetalhesEmpresa = (ImageView) v.findViewById(R.id.facebookDetalhesEmpresa);
        whatsDetalhesEmpresa    = (ImageView) v.findViewById(R.id.whatsDetalhesEmpresa);
        instagramDetalhesEmpresa = (ImageView) v.findViewById(R.id.instagramDetalhesEmpresa);


        //Recebe id da classe DetalhesEmpresa Activity
        final Bundle args = getArguments();
        idEmpresa = args.getString("id");
        carregaDados(Integer.parseInt(idEmpresa));



        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        return v;
    }

    private void carregaDados(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.1.33/wsturistandoibitinga/detalhesinformacaoesempresa.php?id=" + id)
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        nome = object.getString("nome");
                        face = object.getString("facebook");
                        faceid = object.getString("faceid");
                        insta = object.getString("instagram");
                        whats = object.getString("whatsapp");
                        site = object.getString("site");
                        email = object.getString("email");
                        tel1 = object.getString("tel1");
                        tel2 = object.getString("tel2");
                        horario = object.getString("horario");
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

                txtNomeDetalhesEmpresa.setText(nome);
                txtSiteDetalhesEmpresa.setText(site);
                txtEmailDetalhesEmpresa.setText(email);
                txtTel1DetalhesEmpresa.setText(tel1);
                txtTel2DetalhesEmpresa.setText(tel2);
                txtHorarioDetalhesEmpresa.setText(horario);
                txtEnderecoDetalhesEmpresa.setText(endereco);


                //Redes Sociais - Facebook
                facebookDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + faceid));
                            startActivity(intent);
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + face)));
                        }

                    }
                });

                //instagram
                instagramDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("http://instagram.com/_u/"+insta);
                        Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
                        instagram.setPackage("com.instagram.android");

                        if (isIntentAvailable(getContext(), instagram)){
                            startActivity(instagram);
                        } else{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+insta)));
                        }

                    }
                });

                //WhatsApp
                whatsDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!whats.equals("null")) {
                            if (!contatoExiste(whats)) {
                                ContentValues cv = new ContentValues();
                                cv.put(Contacts.People.NAME, nome);
                                Uri u = getActivity().getContentResolver().insert(Contacts.People.CONTENT_URI, cv);
                                Uri pathu = Uri.withAppendedPath(u, Contacts.People.Phones.CONTENT_DIRECTORY);
                                cv.clear();
                                cv.put(Contacts.People.NUMBER, whats);
                                getActivity().getContentResolver().insert(pathu, cv);
                                Toast.makeText(getActivity().getApplicationContext(), "Contato Adicionado", Toast.LENGTH_LONG).show();

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


                //Demais informações - site, email, telefone, horario, endereço
                txtSiteDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://"+site.toString()+"/")));
                    }
                });

                txtEmailDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                        startActivity(Intent.createChooser(intent, ""));
                    }
                });

                txtTel1DetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+tel1));
                        startActivity(callIntent );
                    }
                });

                txtTel2DetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+tel2));
                        startActivity(callIntent );
                    }
                });


                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;

                        // For showing a move to my location button
                        googleMap.setMyLocationEnabled(false);

                        // For dropping a marker at a point on the Map
                        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(log));
                        googleMap.addMarker(new MarkerOptions().position(sydney).title(nome));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });

            }
        };

        task.execute(id);
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public boolean contatoExiste(String number) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = getActivity().getApplicationContext().getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
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


/*
  MapView mMapView;
    private GoogleMap googleMap;

    mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
*/