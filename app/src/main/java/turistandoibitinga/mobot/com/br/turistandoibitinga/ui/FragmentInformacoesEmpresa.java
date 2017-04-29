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
import android.widget.LinearLayout;
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
    private ImageView ic_siteDetalhesEmpresa;
    private View view_site, view_email, view_tel1, view_tel2, view_horario, view_endereco,view_redessociais;
    private LinearLayout layout_site,layout_email, layout_tel1, layout_tel2, layout_horario, layout_endereco;

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


        ic_siteDetalhesEmpresa = (ImageView) v.findViewById(R.id.ic_siteDetalhesEmpresa);


        layout_site = (LinearLayout) v.findViewById(R.id.layout_site);
        layout_email = (LinearLayout) v.findViewById(R.id.layout_email);
        layout_tel1 = (LinearLayout) v.findViewById(R.id.layout_tel1);
        layout_tel2 = (LinearLayout) v.findViewById(R.id.layout_tel2);
        layout_horario = (LinearLayout) v.findViewById(R.id.layout_horario);
        layout_endereco = (LinearLayout) v.findViewById(R.id.layout_endereco);

        view_site = (View) v.findViewById(R.id.view_site);
        view_email = (View) v.findViewById(R.id.view_email);
        view_tel1 = (View) v.findViewById(R.id.view_tel1);
        view_tel2 = (View) v.findViewById(R.id.view_tel2);
        view_horario = (View) v.findViewById(R.id.view_horario);
        view_endereco = (View) v.findViewById(R.id.view_endereco);
        view_redessociais = (View) v.findViewById(R.id.view_redessociais);

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
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/detalhes_empresa.php?id=" + id)
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

                if(nome != null){
                    txtNomeDetalhesEmpresa.setVisibility(View.VISIBLE);
                    txtNomeDetalhesEmpresa.setText(nome);
                }

                if(site != "null"){
                    layout_site.setVisibility(View.VISIBLE);
                    txtSiteDetalhesEmpresa.setVisibility(View.VISIBLE);
                    ic_siteDetalhesEmpresa.setVisibility(View.VISIBLE);
                    view_site.setVisibility(View.VISIBLE);
                    txtSiteDetalhesEmpresa.setText(site);

                    txtSiteDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://"+site.toString()+"/")));
                        }
                    });
                }

                if(email != "null"){
                    layout_email.setVisibility(View.VISIBLE);
                    view_site.setVisibility(View.VISIBLE);
                    txtEmailDetalhesEmpresa.setText(email);

                    txtEmailDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                            startActivity(Intent.createChooser(intent, ""));
                        }
                    });
                }


                if(tel1 != "null"){
                    layout_tel1.setVisibility(View.VISIBLE);
                    view_tel1.setVisibility(View.VISIBLE);
                    txtTel1DetalhesEmpresa.setText(tel1);

                    txtTel1DetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:"+tel1));
                            startActivity(callIntent );
                        }
                    });
                }


                if(tel2 != "null"){
                    layout_tel2.setVisibility(View.VISIBLE);
                    view_tel2.setVisibility(View.VISIBLE);
                    txtTel2DetalhesEmpresa.setText(tel2);

                    txtTel2DetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:"+tel2));
                            startActivity(callIntent );
                        }
                    });
                }

                if(horario != "null"){
                    layout_horario.setVisibility(View.VISIBLE);
                    view_horario.setVisibility(View.VISIBLE);
                    txtHorarioDetalhesEmpresa.setText(horario);
                }

                if(endereco != "null"){
                    layout_endereco.setVisibility(View.VISIBLE);
                    view_endereco.setVisibility(View.VISIBLE);
                    txtEnderecoDetalhesEmpresa.setText(endereco);

                    //chama o app Google maps ao clicar sobre o endereço
                    txtEnderecoDetalhesEmpresa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("geo:<"+ lat + ">,<" + log + ">?q=<" + lat + ">,<" + log + ">(" + nome + ")" ));
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
                            googleMap.addMarker(new MarkerOptions().position(local).title(nome));

                            // For zooming automatically to the location of the marker
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(local).zoom(15).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });

                }

                //Redes Sociais - Facebook
                if(face != "null"){
                    facebookDetalhesEmpresa.setVisibility(View.VISIBLE);
                    view_redessociais.setVisibility(View.VISIBLE);

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

                }

                //instagram
                if(insta != "null"){
                    instagramDetalhesEmpresa.setVisibility(View.VISIBLE);
                    view_redessociais.setVisibility(View.VISIBLE);

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
                }


                //WhatsApp
                if(whats != "null"){
                    whatsDetalhesEmpresa.setVisibility(View.VISIBLE);
                    view_redessociais.setVisibility(View.VISIBLE);

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
                }

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