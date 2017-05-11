package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
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

public class TurismoIbitinga extends AppCompatActivity implements RecycleViewOnClickListenerTurismoIbitinga {

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

        toolbar.setTitleTextColor(getColor(R.color.colorTuristandoBranco));
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

    }
}
