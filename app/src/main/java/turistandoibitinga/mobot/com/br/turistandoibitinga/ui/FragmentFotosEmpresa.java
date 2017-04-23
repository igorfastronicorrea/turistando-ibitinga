package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    String idEmpresa;

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
        carregaDados(Integer.parseInt(idEmpresa));


        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
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
                Request request = new Request.Builder().url("http://192.168.1.36/wsturistandoibitinga/fotosempresa.php?id=" + integers[0])
                        .build();
                try {
                    Response response = cliente.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ImagemDataEmpresa data = new ImagemDataEmpresa(object.getInt("id"),
                                object.getString("foto_1"),
                                object.getString("foto_2"),
                                object.getString("foto_3"));
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
                //progressBar.setVisibility(View.GONE);
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
        Toast.makeText(getActivity(), "position" + position, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getContext(), DetailFotoZoom.class);
        startActivity(i);
    }
}