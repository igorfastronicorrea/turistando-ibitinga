package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import turistandoibitinga.mobot.com.br.turistandoibitinga.RecycleViewOnClickListenerTurismoFotos;
import turistandoibitinga.mobot.com.br.turistandoibitinga.model.ImagemDataTurismo;

/**
 * Created by mobot on 02/04/2017.
 */

public class CustomAdapterFotosTurismo extends RecyclerView.Adapter<CustomAdapterFotosTurismo.ViewHolder> {

    private Context context;
    private List<ImagemDataTurismo> my_data;
    private RecycleViewOnClickListenerTurismoFotos recycleViewOnClickListener;

    public CustomAdapterFotosTurismo(Context context, List<ImagemDataTurismo> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fotos_turismo, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Picasso.with(context).load(my_data.get(position).getFoto()).resize(120, 60).into(holder.foto_1);
        //Picasso.with(context).load(my_data.get(position).getFoto()).into(holder.foto_1);
        Glide.with(context).load(my_data.get(position).getFoto()).into(holder.foto_1);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListenerTurismoFotos r) {
        recycleViewOnClickListener = r;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView foto_1;

        public ViewHolder(View itemView) {
            super(itemView);
            foto_1 = (ImageView) itemView.findViewById(R.id.fotosTurismo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recycleViewOnClickListener != null) {
                recycleViewOnClickListener.onClickListener(v, getPosition());
            }

        }
    }

}