package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.EmpresaData;

/**
 * Created by mobot on 02/04/2017.
 */

public class CustomAdapterListarEmpresa  extends RecyclerView.Adapter<CustomAdapterListarEmpresa.ViewHolder>{

    private Context context;
    private List<EmpresaData> my_data;
    private RecycleViewOnClickListener recycleViewOnClickListener;

    public CustomAdapterListarEmpresa(Context context, List<EmpresaData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_listar_empresas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nome.setText(my_data.get(position).getNome());
        holder.enderecoListarEmpresa.setText(my_data.get(position).getDescricao());
        //Picasso.with(context).load(my_data.get(position).getFoto_capa_otimizada()).resize(120, 60).into(holder.ivFoto);
        //Picasso.with(context).load(my_data.get(position).getFoto_capa_otimizada()).into(holder.ivFoto);
        Glide.with(context).load(my_data.get(position).getFoto_capa_otimizada()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener r){
        recycleViewOnClickListener = r;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nome, enderecoListarEmpresa;
        public ImageView ivFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nomeListarEmpresa);
            ivFoto = (ImageView) itemView.findViewById(R.id.fotoListarEmpresa);
            enderecoListarEmpresa = (TextView) itemView.findViewById(R.id.enderecoListarEmpresa);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recycleViewOnClickListener != null){
                recycleViewOnClickListener.onClickListener(v, getPosition());
            }

        }
    }

}