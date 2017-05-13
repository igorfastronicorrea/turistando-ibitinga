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
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerMenuFeira;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterMenuFeira;
import turistandoibitinga.mobot.com.br.turistandoibitinga.menudata.MyDataMenuFeiraDoBordado;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelMenuFeira;

/**
 * Created by mobot on 01/05/2017.
 */

public class MenuFeiraDoBordadoActivity extends AppCompatActivity implements RecycleViewOnClickListenerMenuFeira{

    //Categoria Empresa
    private RecyclerView recyclerView;
    private CustomAdapterMenuFeira adapter;
    private List<DataModelMenuFeira> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menufeiradobordado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMenuFeira);
        setSupportActionBar(toolbar);

        //utilizado para api -23
        Context context = MenuFeiraDoBordadoActivity.this;
        //ContextCompat.getColor(context, R.color.colorTuristandoBranco);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.colorTuristandoBranco));
        toolbar.setTitle("Feira do Bordado");

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
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_menu_feiradobordado);
        data_list = new ArrayList<>();
        for (int i = 0; i < MyDataMenuFeiraDoBordado.nameArray.length; i++) {
            data_list.add(new DataModelMenuFeira(
                    MyDataMenuFeiraDoBordado.nameArray[i],
                    MyDataMenuFeiraDoBordado.id_[i],
                    MyDataMenuFeiraDoBordado.drawableArray[i]
            ));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapterMenuFeira(this, data_list);
        adapter.setRecycleViewOnClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClickListener(View view, int position) {

        switch (position){
            //pavilhao a
            case 0:
                Intent a = new Intent(MenuFeiraDoBordadoActivity.this, FotoPavilhaoActivity.class);
                a.putExtra("blocoPavilhao", "foto_pavilhao_a");
                startActivity(a);
                break;
            //pavilhao b
            case 1:
                Intent b = new Intent(MenuFeiraDoBordadoActivity.this, FotoPavilhaoActivity.class);
                b.putExtra("blocoPavilhao", "foto_pavilhao_b");
                startActivity(b);
                break;
            //pavilhao C
            case 2:
                Intent c = new Intent(MenuFeiraDoBordadoActivity.this, FotoPavilhaoActivity.class);
                c.putExtra("blocoPavilhao", "foto_pavilhao_c");
                startActivity(c);
                break;

            //Agenda de Shows
            case 3:
                Intent d = new Intent(MenuFeiraDoBordadoActivity.this, ListarEventosActivity.class);
                d.putExtra("api_evento", "listar_eventos_feira");//correspondente a feira do bordado
                d.putExtra("api_detalhes_evento", "detalhes_eventos_feira");
                startActivity(d);
                break;

            //Como chegar
            case 4:
                Intent e = new Intent(MenuFeiraDoBordadoActivity.this, ComoChegarFeiraMapActivity.class);
                startActivity(e);
                break;

            //Como contato
            case 5:
                Intent f = new Intent(MenuFeiraDoBordadoActivity.this, ContatoFeiraActivity.class);
                startActivity(f);
                break;

        }

        //Toast.makeText(this, "position" + position, Toast.LENGTH_LONG).show();
        //Intent i = new Intent(MenuFeiraDoBordadoActivity.this, ComoChegarFeiraMapActivity.class);
        //startActivity(i);
    }
}
