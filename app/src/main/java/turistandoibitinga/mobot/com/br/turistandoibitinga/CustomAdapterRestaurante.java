package turistandoibitinga.mobot.com.br.turistandoibitinga;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mobot on 01/04/2017.
 */

public class CustomAdapterRestaurante extends RecyclerView.Adapter<CustomAdapterRestaurante.ViewHolder> {

    private Context context;
    private List<DataModelHomeCategoriaRestaurante> my_data;
    private RecycleViewOnClickListenerRestaurante recycleViewOnClickListener;

    public CustomAdapterRestaurante(Context context, List<DataModelHomeCategoriaRestaurante> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout_categoria_restaurante,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView imageView = holder.ivFoto;
        imageView.setImageResource(my_data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListenerRestaurante r){
        recycleViewOnClickListener = r;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView description;
        public ImageView ivFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = (ImageView) itemView.findViewById(R.id.imgHomeCategoriaRestaurante);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recycleViewOnClickListener != null){
                recycleViewOnClickListener.onClickListenerRestaurante(v, getLayoutPosition());
            }

        }
    }

}