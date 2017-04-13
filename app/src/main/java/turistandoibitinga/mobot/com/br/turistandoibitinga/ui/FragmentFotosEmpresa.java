package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 03/04/2017.
 */

public class FragmentFotosEmpresa extends Fragment {


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


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}