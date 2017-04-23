package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterListarEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.EmpresaData;

import static turistandoibitinga.mobot.com.br.turistandoibitinga.R.id.slider;

public class ListarEmpresaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecycleViewOnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterListarEmpresa adapter;
    private List<EmpresaData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListarEmpresa);
        setSupportActionBar(toolbar);

        //Defini quando o scroll rola fica o nome da Categoria da Empresa
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.MyAppbar);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Listar Empresas");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        //Inicializando Slider
        mDemoSlider = (SliderLayout)findViewById(slider);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


        //Listagem das empresas
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_listar_empresa);
        data_list = new ArrayList<>();
        carregaDados(0);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CustomAdapterListarEmpresa(this, data_list);
        adapter.setRecycleViewOnClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    //progressBar.setVisibility(View.VISIBLE);
                    carregaDados(data_list.get(data_list.size() - 1).getId());
                }

            }
        });

    }


    private void carregaDados(int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.1.36/wsturistandoibitinga/ws_listagemempresa.php?id=" + integers[0])
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        EmpresaData data = new EmpresaData(object.getInt("id"),
                                object.getString("nome"),
                                object.getString("foto_capa_otimizada"),
                                object.getString("descricao"));
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

        // Passando o id da empresa que foi clicada para a tela Detalhes Empresa que depos
        // passa para a tela FragmentDescricao etc.
        Intent i = new Intent(this, DetalhesEmpresaActivity.class);
        i.putExtra("id", Integer.toString(data_list.get(position).getId()));
        i.putExtra("descricao",  data_list.get(position).getDescricao());
        i.putExtra("foto_capa_otimizada", data_list.get(position).getFoto_capa_otimizada());
        startActivity(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
