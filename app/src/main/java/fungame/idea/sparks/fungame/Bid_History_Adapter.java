package fungame.idea.sparks.fungame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import fungame.idea.sparks.fungame.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bid_History_Adapter extends BaseAdapter {
    Context context;
    List<Manno_DataModel> list;
    LayoutInflater inflater;
    String DELETE_HISTORY="http://sabkuchhbechde.ga/teenpatti/delete_history.php";

    public Bid_History_Adapter(Context context, List<Manno_DataModel> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
            view=inflater.inflate(R.layout.list_item_bid_details,null);
        TextView card=view.findViewById(R.id.txt_bid_details_card);
        TextView coin=view.findViewById(R.id.txt_bid_details_coin);
        Button delete =view.findViewById(R.id.delete_bid);

        Manno_DataModel dm=list.get(i);


        card.setText(dm.getCard_number());
        coin.setText(dm.getCard_money());
        final String id=dm.getCard_id();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, DELETE_HISTORY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            list.remove(list.get(i)); // remove the item from the data list
                            notifyDataSetChanged();
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "Some Problem", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>param=new HashMap<>();
                        param.put("id",id);


                        return param;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);




            }
        });



        return view;
    }
}
