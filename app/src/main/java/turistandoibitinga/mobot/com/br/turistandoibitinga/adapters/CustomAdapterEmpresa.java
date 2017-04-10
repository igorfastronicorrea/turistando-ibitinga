package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelHomeCategoriaEmpresa;
import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListener;

/**
 * Created by mobot on 01/04/2017.
 */

public class CustomAdapterEmpresa extends RecyclerView.Adapter<CustomAdapterEmpresa.ViewHolder> {

    private Context context;
    private List<DataModelHomeCategoriaEmpresa> my_data;
    private RecycleViewOnClickListener recycleViewOnClickListener;

    public CustomAdapterEmpresa(Context context, List<DataModelHomeCategoriaEmpresa> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout_categoria_empresa,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.description.setText(my_data.get(position).getName());
        //Picasso.with(context).load(my_data.get(position).getImage()).resize(120, 60).into(holder.ivFoto);
        ImageView imageView = holder.ivFoto;
        imageView.setImageResource(my_data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener r){
        recycleViewOnClickListener = r;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView description;
        public ImageView ivFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            //description = (TextView) itemView.findViewById(R.id.description);
            ivFoto = (ImageView) itemView.findViewById(R.id.imgHomeCategoriaEmpresa);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recycleViewOnClickListener != null){
                recycleViewOnClickListener.onClickListener(v, getLayoutPosition());
            }

        }
    }

}