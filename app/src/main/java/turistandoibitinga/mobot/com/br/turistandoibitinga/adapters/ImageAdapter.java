package turistandoibitinga.mobot.com.br.turistandoibitinga.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import turistandoibitinga.mobot.com.br.turistandoibitinga.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by igorf on 21/04/2017.
 */

public class ImageAdapter extends PagerAdapter {

    Context context;

   // MainActivity act;
    int[] GalImages;

    public ImageAdapter(Context context, int[] GalImages){
        this.context = context;
        this.GalImages = GalImages;
    }

    ImageAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //super.instantiateItem(container, position);
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);

        //Responsavel pelo efeito de Zoom
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.update();

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}