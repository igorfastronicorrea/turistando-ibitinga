package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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

        toolbar.setTitleTextColor(getColor(R.color.colorTuristandoBranco));
        toolbar.setTitle("Feira do Bordado");

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
        Toast.makeText(this, "position" + position, Toast.LENGTH_LONG).show();
        //Intent i = new Intent(HomeActivity.this, ListarEmpresaActivity.class);
        //startActivity(i);
    }
}
