package fungame.idea.sparks.fungame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import fungame.idea.sparks.fungame.R;

import java.util.List;

public class Manno_Adapter  extends BaseAdapter {
    Context context;
    List list;
    LayoutInflater inflater;

    public Manno_Adapter(Context context, List<String> list) {
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
        view=inflater.inflate(R.layout.card_image,null);
        ImageView image=view.findViewById(R.id.card_image);

        String c=list.get(i).toString();

         Glide.with(context).load(c)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(image);

        return view;
    }
}
