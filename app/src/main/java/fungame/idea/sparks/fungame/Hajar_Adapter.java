package fungame.idea.sparks.fungame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class Hajar_Adapter extends BaseAdapter {
    Context context;
    List list;
    LayoutInflater inflater;

    public Hajar_Adapter(Context context, List<Hajar_Data_Model> list) {
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.list_hajar,null);
        ImageView image1=view.findViewById(R.id.image1);

        ImageView image2=view.findViewById(R.id.image2);
        ImageView image3=view.findViewById(R.id.image3);

        Hajar_Data_Model model= (Hajar_Data_Model) list.get(i);


            Glide.with(context).load(model.getCategory())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image1);


            Glide.with(context).load(model.getCategory1())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image2);


            Glide.with(context).load(model.getCategory2())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image3);



        return view;
    }
}
