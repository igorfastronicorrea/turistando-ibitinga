package turistandoibitinga.mobot.com.br.turistandoibitinga;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecycleViewOnClickListener, RecycleViewOnClickListenerRestaurante {

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

    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(this, "position" + position, Toast.LENGTH_LONG).show();
        Intent i = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
        startActivity(i);
    }

    @Override
    public void onClickListenerRestaurante(View view, int position) {
        Toast.makeText(this, "position" + position, Toast.LENGTH_LONG).show();
        Intent i = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
        startActivity(i);
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



}
