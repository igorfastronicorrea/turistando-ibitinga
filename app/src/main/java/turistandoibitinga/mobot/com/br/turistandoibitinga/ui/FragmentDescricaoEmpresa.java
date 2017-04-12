package turistandoibitinga.mobot.com.br.turistandoibitinga.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;

/**
 * Created by igorf on 03/04/2017.
 */

public class FragmentDescricaoEmpresa extends Fragment {

    TextView txtDetalhesDecricaoEmpresa;
    ViewPager viewPager;

    public FragmentDescricaoEmpresa() {
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
        View v = inflater.inflate(R.layout.fragment_descricao_empresa, container, false);

        txtDetalhesDecricaoEmpresa = (TextView) v.findViewById(R.id.txtDetalhesDecricaoEmpresa);

        //Recebe da activity DetalhesEMpresaActivity e seta no txtview
        final Bundle args = getArguments();
        final String side = args.getString("descricao");
        txtDetalhesDecricaoEmpresa.setText(side);


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}