package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerListarEventos;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterListarEvento;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.EventoData;

/**
 * Created by igorf on 06/05/2017.
 */

public class ListarEventosActivity extends AppCompatActivity implements RecycleViewOnClickListenerListarEventos {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterListarEvento adapter;
    private List<EventoData> data_list;
    private String api_evento, api_detalhes_evento;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListarEventos);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        api_evento = intent.getStringExtra("api_evento");
        api_detalhes_evento = intent.getStringExtra("api_detalhes_evento");

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

        //Listagem dos Eventos
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_listar_eventos);
        data_list = new ArrayList<>();
        carregaDados(0);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CustomAdapterListarEvento(this, data_list);
        adapter.setRecycleViewOnClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    progressBar.setVisibility(View.VISIBLE);
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
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_evento + ".php?id=" + integers[0])
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        EventoData data = new EventoData(object.getInt("id"),
                                object.getString("nome"),
                                object.getString("foto_capa"),
                                object.getString("data"),
                                object.getString("lat"),
                                object.getString("log"),
                                object.getString("local_evento"));
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


    @Override
    public void onClickListener(View view, int position) {

        Intent i = new Intent(this, EventosActivity.class);
        i.putExtra("id", Integer.toString(data_list.get(position).getId()));
        i.putExtra("nome", data_list.get(position).getNome());
        i.putExtra("lat", data_list.get(position).getLat());
        i.putExtra("log", data_list.get(position).getLog());
        i.putExtra("local_evento", data_list.get(position).getLocal_evento());
        //envia a api correspondente
        i.putExtra("api_detalhes_evento", api_detalhes_evento);
        startActivity(i);
    }
}
