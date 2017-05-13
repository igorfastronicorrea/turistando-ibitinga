package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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

    private String nome_listagem, api_listagem, api_slide, api_detalhes, api_detalhes_foto_ot, api_detalhes_fotos;

    private String[] nome_slide = new String[]{"a","b","c"};
    private String[] foto_slide = new String[]{"a", "b", "c"};
    private String[] id_slide = new String[]{"a", "b", "c"};
    private String[] id_empresa = new String[]{"a", "b", "c"};
    private String[] foto_capa_ot = new String[]{"a", "b", "c"};
    private String[] descricao_empresa = new String[]{"a", "b", "c"};
    private String[] nome_empresa = new String[]{"a", "b", "c"};

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListarEmpresa);
        setSupportActionBar(toolbar);

        //Seta o ProgressBar como visible e vai ficar carregando até os dados serem totalmente carregados
        //atraves do método onPostExecute
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Seta a seta (Button) Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Recebe da Activity HomeActivity quando o usuário clica no menu
        Intent intent = getIntent();
        nome_listagem = intent.getStringExtra("nome_listagem");
        api_listagem = intent.getStringExtra("api_listagem");
        api_slide = intent.getStringExtra("api_slide");
        api_detalhes = intent.getStringExtra("api_detalhes");
        api_detalhes_foto_ot = intent.getStringExtra("api_detalhes_foto_ot");
        api_detalhes_fotos = intent.getStringExtra("api_detalhes_fotos");


        //Inicializando Slider
        mDemoSlider = (SliderLayout)findViewById(slider);
        carregaSlide(0);

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
                    collapsingToolbarLayout.setTitle(nome_listagem);
                    Context context = ListarEmpresaActivity.this;
                    collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


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
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_listagem +".php?id=" + integers[0])
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        EmpresaData data = new EmpresaData(object.getInt("id"),
                                object.getString("nome"),
                                object.getString("foto_capa_ot"),
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
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

    private void carregaSlide(int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_slide +".php")
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objectSlide = array.getJSONObject(i);
                        id_slide[i] = objectSlide.getString("id");
                        id_empresa[i] = objectSlide.getString("id_empresa");
                        nome_slide[i] = objectSlide.getString("nome_empresa");
                        foto_slide[i] = objectSlide.getString("foto_slide");
                        foto_capa_ot[i] = objectSlide.getString("foto_capa_ot");
                        nome_empresa[i] = objectSlide.getString("nome_empresa");
                        descricao_empresa[i] = objectSlide.getString("descricao_empresa");
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

                HashMap<String,String> url_maps = new HashMap<String, String>();
                url_maps.put(id_slide[0], foto_slide[0]);
                url_maps.put(id_slide[1], foto_slide[1]);
                url_maps.put(id_slide[2], foto_slide[2]);


                for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(ListarEmpresaActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                           // .description(name)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(ListarEmpresaActivity.this);

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
                mDemoSlider.addOnPageChangeListener(ListarEmpresaActivity.this);
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
        i.putExtra("foto_capa_ot", data_list.get(position).getFoto_capa_otimizada());

        //envia a api correspondente
        i.putExtra("api_slide", api_slide);
        i.putExtra("api_detalhes", api_detalhes);
        i.putExtra("api_detalhes_foto_ot", api_detalhes_foto_ot);
        i.putExtra("api_detalhes_fotos", api_detalhes_fotos);

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

    //OnClick do Slide
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent i = new Intent(this, DetalhesEmpresaActivity.class);
        i.putExtra("id", id_empresa[0]);
        i.putExtra("descricao",  descricao_empresa[0]);
        i.putExtra("foto_capa_ot", foto_capa_ot[0]);

        //envia a api correspondente
        i.putExtra("api_slide", api_slide);
        i.putExtra("api_detalhes", api_detalhes);
        i.putExtra("api_detalhes_foto_ot", api_detalhes_foto_ot);
        i.putExtra("api_detalhes_fotos", api_detalhes_fotos);

        startActivity(i);
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
