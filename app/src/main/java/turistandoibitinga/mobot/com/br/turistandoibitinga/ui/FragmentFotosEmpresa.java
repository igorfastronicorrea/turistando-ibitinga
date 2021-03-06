package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;
import turistandoibitinga.mobot.com.br.turistandoibitinga.adapters.CustomAdapterFotosEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.ImagemDataEmpresa;

/**
 * Created by igorf on 03/04/2017.
 */

public class FragmentFotosEmpresa extends Fragment implements RecycleViewOnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterFotosEmpresa adapter;
    private List<ImagemDataEmpresa> data_list;
    String idEmpresa, api_detalhes_foto_ot, api_detalhes_fotos;
    private ProgressBar progressBar;

    public FragmentFotosEmpresa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fotos_empresa, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_fotos_empresa);
        data_list = new ArrayList<>();

        //Recebe id da classe DetalhesEmpresa Activity
        final Bundle args = getArguments();
        idEmpresa = args.getString("id");
        api_detalhes_foto_ot = args.getString("api_detalhes_foto_ot");
        api_detalhes_fotos = args.getString("api_detalhes_fotos");
        carregaDados(Integer.parseInt(idEmpresa));


        progressBar = (ProgressBar) v.findViewById(R.id.progressBarFotos);
        progressBar.setVisibility(View.VISIBLE);


        linearLayoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new CustomAdapterFotosEmpresa(getContext(), data_list);
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

        return v;
    }


    private void carregaDados(int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient cliente = new OkHttpClient();
                Request request = new Request.Builder().url("http://turistandomobot.esy.es/" + api_detalhes_foto_ot +".php?id=" + integers[0])
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ImagemDataEmpresa data = new ImagemDataEmpresa(object.getInt("id"),
                                object.getString("nome_foto"));
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClickListener(View view, int position) {
        Intent i = new Intent(getContext(), DetailFotoZoom.class);
        String posicao = String.valueOf(position);
        i.putExtra("id", idEmpresa);
        i.putExtra("position", posicao);
        i.putExtra("api_detalhes_fotos", api_detalhes_fotos);
        startActivity(i);
    }
}