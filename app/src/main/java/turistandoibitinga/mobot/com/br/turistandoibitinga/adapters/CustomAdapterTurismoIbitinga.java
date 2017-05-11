package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerTurismoIbitinga;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.DataModelTurismoIbitinga;

/**
 * Created by mobot on 01/04/2017.
 */

public class CustomAdapterTurismoIbitinga extends RecyclerView.Adapter<CustomAdapterTurismoIbitinga.ViewHolder> {

    private Context context;
    private List<DataModelTurismoIbitinga> my_data;
    private RecycleViewOnClickListenerTurismoIbitinga recycleViewOnClickListener;

    public CustomAdapterTurismoIbitinga(Context context, List<DataModelTurismoIbitinga> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout_menu_feira,parent,false);

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

    public void setRecycleViewOnClickListener(RecycleViewOnClickListenerTurismoIbitinga r){
        recycleViewOnClickListener = r;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = (ImageView) itemView.findViewById(R.id.imgMenuFeira);

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