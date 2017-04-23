package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.ImagemDataEmpresa;

/**
 * Created by mobot on 02/04/2017.
 */

public class CustomAdapterFotosEmpresa extends RecyclerView.Adapter<CustomAdapterFotosEmpresa.ViewHolder>{

    private Context context;
    private List<ImagemDataEmpresa> my_data;
    private RecycleViewOnClickListener recycleViewOnClickListener;

    public CustomAdapterFotosEmpresa(Context context, List<ImagemDataEmpresa> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fotos_empresas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(my_data.get(position).getFoto_1()).resize(120, 60).into(holder.foto_1);
        Picasso.with(context).load(my_data.get(position).getFoto_2()).resize(120, 60).into(holder.foto_2);
        //Picasso.with(context).load(my_data.get(position).getFoto_3()).resize(120, 60).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener r){
        recycleViewOnClickListener = r;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView foto_1, foto_2;

        public ViewHolder(View itemView) {
            super(itemView);
            foto_1 = (ImageView) itemView.findViewById(R.id.fotosEmpresa);
            foto_2 = (ImageView) itemView.findViewById(R.id.afotosEmpresa);
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