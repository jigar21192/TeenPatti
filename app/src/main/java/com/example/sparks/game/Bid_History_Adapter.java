package com.example.sparks.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Bid_History_Adapter extends BaseAdapter {
    Context context;
    List<String> list;
    LayoutInflater inflater;

    public Bid_History_Adapter(Context context, List<String> list) {
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
            view=inflater.inflate(R.layout.list_item_bid_details,null);
        TextView card=view.findViewById(R.id.txt_bid_details_card);
        TextView coin=view.findViewById(R.id.txt_bid_details_coin);
        Button delete =view.findViewById(R.id.delete_bid);

        card.setText(list.get(i));
        coin.setText(list.get(i));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
