package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerTurismoIbitinga;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterTurismoIbitinga;
import turistandoibitinga.mobot.com.br.turistandoibitinga.menudata.MyDataTurismoIbitinga;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelTurismoIbitinga;

/**
 * Created by igorf on 10/05/2017.
 */

public class TurismoIbitingaActivity extends AppCompatActivity implements RecycleViewOnClickListenerTurismoIbitinga {

    //Categoria Empresa
    private RecyclerView recyclerView;
    private CustomAdapterTurismoIbitinga adapter;
    private List<DataModelTurismoIbitinga> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menuturismoibitinga);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTurismoIbitinga);
        setSupportActionBar(toolbar);


        Context context = TurismoIbitingaActivity.this;
        ContextCompat.getColor(context, R.color.colorTuristandoBranco);

        //toolbar.setTitleTextColor(getColor(R.color.colorTuristandoBranco));
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));
        toolbar.setTitle("O que fazer em Ibitinga?");

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

        //Menu Feira | Pavilhão A, Pavilhão B .... etc
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_menu_turismoibitinga);
        data_list = new ArrayList<>();
        for (int i = 0; i < MyDataTurismoIbitinga.nameArray.length; i++) {
            data_list.add(new DataModelTurismoIbitinga(
                    MyDataTurismoIbitinga.nameArray[i],
                    MyDataTurismoIbitinga.id_[i],
                    MyDataTurismoIbitinga.drawableArray[i]
            ));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapterTurismoIbitinga(this, data_list);
        adapter.setRecycleViewOnClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(View view, int position) {

        switch (position) {
            //corpuschristi
            case 0:
                Intent a = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                a.putExtra("id", "1");
                a.putExtra("api", "detalhes_turismoibitinga");
                a.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                a.putExtra("nome_activity", "Corpus Christi");
                startActivity(a);
                break;
            //Via Sacra
            case 1:
                Intent b = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                b.putExtra("id", "2");
                b.putExtra("api", "detalhes_turismoibitinga");
                b.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                b.putExtra("nome_activity", "Via Sacra");
                startActivity(b);
                break;
            //Feira do Bordao
            case 2:
                Intent c = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                c.putExtra("id", "3");
                c.putExtra("api", "detalhes_turismoibitinga");
                c.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                c.putExtra("nome_activity", "Feira do Bordado");
                startActivity(c);
                break;
            //Turismo Religioso
            case 3:
                Intent d = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                d.putExtra("id", "4");
                d.putExtra("api", "detalhes_turismoibitinga");
                d.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                d.putExtra("nome_activity", "Turismo Religioso");
                startActivity(d);
                break;
            //Turismo de Compras
            case 4:
                Intent e = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                e.putExtra("id", "5");
                e.putExtra("api", "detalhes_turismoibitinga");
                e.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                e.putExtra("nome_activity", "Turismo de Compras");
                startActivity(e);
                break;
            //Turismo Rural
            case 5:
                Intent f = new Intent(TurismoIbitingaActivity.this, TurismoIbitingaDetalhesActivity.class);
                f.putExtra("id", "6");
                f.putExtra("api", "detalhes_turismoibitinga");
                f.putExtra("api_detalhes_fotos", "listagem_fotos_turismo");
                f.putExtra("nome_activity", "Turismo Rural");
                startActivity(f);
                break;
        }
    }
}
