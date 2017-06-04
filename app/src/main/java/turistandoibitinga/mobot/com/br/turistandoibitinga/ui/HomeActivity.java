package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerRestaurante;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterRestaurante;
import turistandoibitinga.mobot.com.br.turistandoibitinga.menudata.MyDataHomeCategoriaEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.menudata.MyDataHomeCategoriaRestaurante;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelHomeCategoriaEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelHomeCategoriaRestaurante;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecycleViewOnClickListener, RecycleViewOnClickListenerRestaurante {

    //Feira do Bordado E TurismoIbitingaActivity(O que fazer)
    private ImageView menuFeiraDoBordado;
    private ImageView menuTurismoIbitinga;
    private ImageView menuAgendaCultural;

    //Categoria Empresa
    private RecyclerView recyclerView;
    private CustomAdapterEmpresa adapter;
    private List<DataModelHomeCategoriaEmpresa> data_list;


    //Categoria Restaurante
    private RecyclerView recyclerViewRestaurante;
    private CustomAdapterRestaurante adapterRestaurante;
    private List<DataModelHomeCategoriaRestaurante> data_list_restaurante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        //Começa aqui o meu código
        //
        Context context = HomeActivity.this;
        menuFeiraDoBordado = (ImageView) findViewById(R.id.menuFeiradoBordado);
        Picasso.with(context).load(R.drawable.ic_imagem_feira1).into(menuFeiraDoBordado);
        menuFeiraDoBordado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMenuFeiraDoBordado = new Intent(HomeActivity.this, MenuFeiraDoBordadoActivity.class);
                startActivity(iMenuFeiraDoBordado);
            }
        });

        //Departamentos Empresa
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_categoria_empresa);
        data_list = new ArrayList<>();
        for (int i = 0; i < MyDataHomeCategoriaEmpresa.nameArray.length; i++) {
            data_list.add(new DataModelHomeCategoriaEmpresa(
                    MyDataHomeCategoriaEmpresa.nameArray[i],
                    MyDataHomeCategoriaEmpresa.id_[i],
                    MyDataHomeCategoriaEmpresa.drawableArray[i]
            ));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapterEmpresa(this, data_list);
        adapter.setRecycleViewOnClickListener(this);
        recyclerView.setAdapter(adapter);


        //Menu Home Categoria Restaurante
        recyclerViewRestaurante = (RecyclerView) findViewById(R.id.my_recycler_view_categoria_restaurante);
        data_list_restaurante = new ArrayList<>();
        for (int i = 0; i < MyDataHomeCategoriaRestaurante.nameArray.length; i++) {
            data_list_restaurante.add(new DataModelHomeCategoriaRestaurante(
                    MyDataHomeCategoriaRestaurante.nameArray[i],
                    MyDataHomeCategoriaRestaurante.id_[i],
                    MyDataHomeCategoriaRestaurante.drawableArray[i]
            ));
        }

        recyclerViewRestaurante.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRestaurante.setItemAnimator(new DefaultItemAnimator());

        adapterRestaurante = new CustomAdapterRestaurante(this, data_list_restaurante);
        adapterRestaurante.setRecycleViewOnClickListener(this);
        recyclerViewRestaurante.setAdapter(adapterRestaurante);


        menuTurismoIbitinga = (ImageView) findViewById(R.id.menuTurismoIbitinga);
        Picasso.with(context).load(R.mipmap.menuoqefazereeemibitinga).into(menuTurismoIbitinga);
        menuTurismoIbitinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuTurismoIbitnga = new Intent(HomeActivity.this, TurismoIbitingaActivity.class);
                startActivity(menuTurismoIbitnga);
            }
        });

        menuAgendaCultural = (ImageView) findViewById(R.id.menuAgendaCultural);
        Picasso.with(context).load(R.mipmap.menuagendacultural).into(menuAgendaCultural);
        menuAgendaCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuAgendaCultural = new Intent(HomeActivity.this, ListarEventosActivity.class);
                menuAgendaCultural.putExtra("api_evento", "listar_agenda_cultural");//correspondente a agenda cultural
                menuAgendaCultural.putExtra("api_detalhes_evento", "detalhes_agenda_cultural");
                startActivity(menuAgendaCultural);
            }
        });
    }

    @Override
    public void onClickListener(View view, int position) {
        switch (position) {
            case 0:
                Intent bebe = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                bebe.putExtra("nome_listagem", "Bebê");
                bebe.putExtra("api_listagem", "listagem_empresa_bebe");
                bebe.putExtra("api_slide", "slide_listagem_bebe");
                bebe.putExtra("api_detalhes", "detalhes_empresa_bebe");
                bebe.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_bebe");
                bebe.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_bebe");
                bebe.putExtra("theme", "AppTheme_NoActionBarListar");
                startActivity(bebe);
                break;
            case 1:
                Intent camamesabanho = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                camamesabanho.putExtra("nome_listagem", "Cama Mesa e Banho");
                camamesabanho.putExtra("api_listagem", "listagem_empresa_camamesabanho");
                camamesabanho.putExtra("api_slide", "slide_listagem_camamesabanho");
                camamesabanho.putExtra("api_detalhes", "detalhes_empresa_camamesabanho");
                camamesabanho.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_camamesabanho");
                camamesabanho.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_camamesabanho");
                camamesabanho.putExtra("theme", "AppTheme_NoActionBarListar");
                startActivity(camamesabanho);
                break;
            case 2:
                Intent cozinha = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                cozinha.putExtra("nome_listagem", "Cozinha");
                cozinha.putExtra("api_listagem", "listagem_empresa_cozinha");
                cozinha.putExtra("api_slide", "slide_listagem_cozinha");
                cozinha.putExtra("api_detalhes", "detalhes_empresa_cozinha");
                //cozinha.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_ot_cozinha");
                cozinha.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_cozinha");
                cozinha.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_cozinha");
                cozinha.putExtra("theme", "AppTheme_NoActionBarListar");
                startActivity(cozinha);
                break;
            case 3:
                Intent decoracao = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                decoracao.putExtra("nome_listagem", "Decoração");
                decoracao.putExtra("api_listagem", "listagem_empresa_decoracao");
                decoracao.putExtra("api_slide", "slide_listagem_decoracao");
                decoracao.putExtra("api_detalhes", "detalhes_empresa_decoracao");
                decoracao.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_decoracao");
                decoracao.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_decoracao");
                decoracao.putExtra("theme", "AppTheme_NoActionBarListar");
                startActivity(decoracao);
                break;
            case 4:
                Intent bichoPelucia = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                bichoPelucia.putExtra("nome_listagem", "Bicho de Pelucia");
                bichoPelucia.putExtra("api_listagem", "listagem_empresa_bichodepelucia");
                bichoPelucia.putExtra("api_slide", "slide_listagem_bichopelucia");
                bichoPelucia.putExtra("api_detalhes", "detalhes_empresa_bichodepelucia");
                bichoPelucia.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_bichodepelucia");
                bichoPelucia.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_bichodepelucia");
                bichoPelucia.putExtra("theme", "AppTheme_NoActionBarListar");
                startActivity(bichoPelucia);
                break;
            default:
                Intent i = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                startActivity(i);
        }

    }

    @Override
    public void onClickListenerRestaurante(View view, int position) {
        switch (position) {
            case 0:
                Intent restaurante = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                restaurante.putExtra("nome_listagem", "Restaurante");
                restaurante.putExtra("api_listagem", "listagem_empresa_restaurante");
                restaurante.putExtra("api_slide", "slide_listagem_restaurante");
                restaurante.putExtra("api_detalhes", "detalhes_empresa_restaurante");
                restaurante.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_restaurante");
                restaurante.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_restaurante");
                restaurante.putExtra("theme", "AppTheme_NoActionBarListarRestaurante");
                startActivity(restaurante);
                break;
            case 1:
                Intent hotel = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                hotel.putExtra("nome_listagem", "Hotéis");
                hotel.putExtra("api_listagem", "listagem_empresa_hoteis");
                hotel.putExtra("api_slide", "slide_listagem_hotel");
                hotel.putExtra("api_detalhes", "detalhes_empresa_hoteis");
                hotel.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_hoteis");
                hotel.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_hoteis");
                hotel.putExtra("theme", "AppTheme_NoActionBarListarRestaurante");
                startActivity(hotel);
                break;
            case 2:
                Intent bar = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                bar.putExtra("nome_listagem", "Bares");
                bar.putExtra("api_listagem", "listagem_empresa_bar");
                bar.putExtra("api_slide", "slide_listagem_bar");
                bar.putExtra("api_detalhes", "detalhes_empresa_bar");
                bar.putExtra("api_detalhes_foto_ot", "detalhes_empresa_fotos_bar");
                bar.putExtra("api_detalhes_fotos", "detalhes_empresa_fotos_bar");
                bar.putExtra("theme", "AppTheme_NoActionBarListarRestaurante");
                startActivity(bar);
                break;
            default:
                Intent i = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
                startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_code) {
            Intent politica = new Intent(HomeActivity.this, DesenvolvimentoActivity.class);
            startActivity(politica);
        } else if (id == R.id.nav_manage) {
            Intent politica = new Intent(HomeActivity.this, PoliticaPrivacidade.class);
            startActivity(politica);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
