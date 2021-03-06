package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

public class DetalhesEmpresaActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String id, descricao, foto, theme;
    private ImageView imgDetalhesEmpresa;
    private String api_listagem, api_slide, api_detalhes, api_detalhes_foto_ot, api_detalhes_fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recebe o id, foto da classe ListarEmpresaActivity que é o id da empresa que deve ser
        //enviado para os fragments
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        descricao = intent.getStringExtra("descricao");
        foto = intent.getStringExtra("foto_capa_ot");
        api_listagem = intent.getStringExtra("api_listagem");
        api_slide = intent.getStringExtra("api_slide");
        api_detalhes = intent.getStringExtra("api_detalhes");
        api_detalhes_foto_ot = intent.getStringExtra("api_detalhes_foto_ot");
        api_detalhes_fotos = intent.getStringExtra("api_detalhes_fotos");
        theme = intent.getStringExtra("theme");


        if (theme.equals("AppTheme_NoActionBarListarRestaurante")) {
            setTheme(R.style.AppTheme_NoActionBarDetalhesEmpresaVermelho);
        }


        setContentView(R.layout.act_detalhes_empresa);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalhesEmpresa);
        setSupportActionBar(toolbar);

        //seta imagem da capa no fragment - todos
        imgDetalhesEmpresa = (ImageView) findViewById(R.id.imgDetalhesEmpresa);
        Picasso.with(getBaseContext()).load(foto).into(imgDetalhesEmpresa);

        //Definindo quando o scroll rolar muda o nome do title
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_detalhes_empresa);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.myappbar_detalhes_empresa);
        collapsingToolbarLayout.setTitle("");


        if (theme.equals("AppTheme_NoActionBarListarRestaurante")) {
            collapsingToolbarLayout.setContentScrimResource(R.color.colorTuristandoRed);
        }


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Detalhes Empresas");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

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

        //desativa titulo actionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Se for o tema restaurante muda para vermelho
        if (theme.equals("AppTheme_NoActionBarListarRestaurante")) {
            tabLayout.setBackgroundResource(R.color.colorTuristandoRed);
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorTuristandoBranco));
            tabLayout.setTabTextColors(Color.parseColor("#660101"), Color.parseColor("#ffffff"));
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDescricaoEmpresa(), "Descrição");
        adapter.addFragment(new FragmentFotosEmpresa(), "Fotos");
        adapter.addFragment(new FragmentInformacoesEmpresa(), "Informações");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            //Fragment Descricao
            if (position == 0) {
                //Envia o id da empresa selecionada para o fragment
                Bundle bundle = new Bundle();
                bundle.putString("descricao", descricao);
                mFragmentList.get(position).setArguments(bundle);
            }

            //Fragment Foto
            if (position == 1) {
                //Envia o id da empresa selecionada para o fragment
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("api_detalhes", api_detalhes);
                bundle.putString("api_detalhes_foto_ot", api_detalhes_foto_ot);
                bundle.putString("api_detalhes_fotos", api_detalhes_fotos);
                mFragmentList.get(position).setArguments(bundle);
            }

            //Fragment Informações
            if (position == 2) {
                //Envia o id da empresa selecionada para o fragment
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("api_detalhes", api_detalhes);
                mFragmentList.get(position).setArguments(bundle);
            }

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}