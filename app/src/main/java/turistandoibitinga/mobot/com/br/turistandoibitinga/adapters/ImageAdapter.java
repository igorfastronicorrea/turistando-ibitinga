package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import turistandoibitinga.mobot.com.br.turistandoibitinga.model.Fotos;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by igorf on 21/04/2017.
 */

public class ImageAdapter extends PagerAdapter {

    Context context;

   private List<Fotos> my_data;

    public ImageAdapter(Context context, List<Fotos>  my_data){
        this.context = context;
        this.my_data = my_data;
    }

    ImageAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return my_data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //super.instantiateItem(container, position);
        ImageView imageView = new ImageView(context);

        final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);

        Picasso.with(context).load(my_data.get(position).getFoto()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                photoViewAttacher.update();
            }

            @Override
            public void onError() {

            }
        });
        ((ViewPager) container).addView(imageView, 0);



        //Responsavel pelo efeito de Zoom
        //PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        //photoViewAttacher.update();

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}