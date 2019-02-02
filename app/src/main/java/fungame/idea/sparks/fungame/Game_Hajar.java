package fungame.idea.sparks.fungame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import fungame.idea.sparks.fungame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Game_Hajar extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    public static final String ID= "id";
    String CARD_Total="http://jmfungame.com/hazar_total";
    String HISTORY_BID="http://jmfungame.com/hazar_history";
    String CARD_LOAD="http://jmfungame.com/hazar_res2.php";
    String BID_URL="http://jmfungame.com/hazar_bid.php";
    String LIST_LOAD="http://jmfungame.com/hazar_res1.php";
    String USER_DETAILS="http://jmfungame.com/user_details.php";
    SharedPreferences sharedpreferences;
    List<Hajar_Data_Model> list;
    List<Hajar_Data_Model> list1;
    List<Hajar_Data_Model> list2;
    List<Manno_DataModel>bid_history;
    LinearLayout in_page,out_page,linearLayout;
    PopupWindow popupWindow,popupWindow1,h_popupWindow2;
    RelativeLayout relativeLayout;
    Button select_card,select_coin,bid,history_bid;
    TextView h_username,h_balance,card_total;
    ImageView image_hajar_1,image_hajar_2,image_hajar_3;
    String name,balance, id,card_id,card_number,card_coin;
    ListView lv_hajar1,lv_hajar2,lv_hajar3;
    Timer repeatTask;
    Hajar_Data_Model model;
    ProgressDialog pd;
    String digit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__hajar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String user=sharedpreferences.getString("email","");
         id=sharedpreferences.getString("id","");

        card_total=findViewById(R.id.card_total);
        relativeLayout=findViewById(R.id.r1);
        linearLayout=findViewById(R.id.linear1);
        select_card=findViewById(R.id.select_card);
        select_coin=findViewById(R.id.select_coin);
        h_username=findViewById(R.id.h_username);
        history_bid=findViewById(R.id.history_bid);
        h_balance=findViewById(R.id.h_balance);
        bid=findViewById(R.id.bid);
        lv_hajar1=findViewById(R.id.lv_hajar1);
        /*lv_hajar2=findViewById(R.id.lv_hajar2);
        lv_hajar3=findViewById(R.id.lv_hajar3);*/
        image_hajar_1=findViewById(R.id.image_hajar_1);
        image_hajar_2=findViewById(R.id.image_hajar_2);
        image_hajar_3=findViewById(R.id.image_hajar_3);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pd=new ProgressDialog(Game_Hajar.this);




        repeatTask = new Timer();
        repeatTask.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            load_card();

                            load_list();
                        card_total();
                        bid_history=new ArrayList<>();
                        list=new ArrayList<>();
                   /*     list1=new ArrayList<>();
                        list2=new ArrayList<>();*/




                  //      load_out_page();



                        StringRequest stringRequest=new StringRequest(Request.Method.POST, USER_DETAILS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        name = object.getString("name");
                                        balance = object.getString("hazar_coin");

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                h_balance.setText(balance);
                                h_username.setText(name);


                            }



                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Game_Hajar.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


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
                        RequestQueue requestQueue=Volley.newRequestQueue(Game_Hajar.this);
                        requestQueue.add(stringRequest);


        bid.setOnClickListener(new View.OnClickListener() {
                            @Override
                                public void onClick(View view) {

                final String card=select_card.getText().toString();
                final String coin=select_coin.getText().toString();


                if (card.equals("CARD")){
                    Toast.makeText(Game_Hajar.this, "Select any Card", Toast.LENGTH_SHORT).show();
                }else if (coin.equals("COIN")){
                    Toast.makeText(Game_Hajar.this, "Select Money", Toast.LENGTH_SHORT).show();
                    }else {
                    pd.setMessage("loading");
                    pd.show();
                StringRequest request=new StringRequest(Request.Method.POST, BID_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            pd.dismiss();
                            Toast.makeText(Game_Hajar.this, "BID Success", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("Increase_Money")){
                            pd.dismiss();
                            Toast.makeText(Game_Hajar.this, "Increase Your Coin", Toast.LENGTH_SHORT).show();

                        }else if(response.trim().equals("Wait For Game Start")){

                            pd.dismiss();
                            Toast.makeText(Game_Hajar.this, "Game Not Started", Toast.LENGTH_SHORT).show();

                        }else {
                            pd.dismiss();
                            Toast.makeText(Game_Hajar.this, "Problem On BID", Toast.LENGTH_SHORT).show();


                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(Game_Hajar.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>param=new HashMap<>();
                        param.put("id",id);
                        param.put("card_number",card);
                        param.put("money",coin);
                        return param;
                    }
                };

                RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
                queue.add(request);


            }}
        });
        history_bid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popup_history();
                            }
                        });
        select_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instantiate the popup.xml layout file
               popup();
            }
        });
        select_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instantiate the popup.xml layout file
                popup_money();
            }
        });

                    }
                });
            }
        }, 0, 5000);

    }

    private void card_total() {
        StringRequest request=new StringRequest(Request.Method.GET, CARD_Total, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        digit=object.getString("digit");

                        card_total.setText(digit);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Game_Hajar.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        })
       ;

        RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
        queue.add(request);


    }



    private void popup_history() {
            LayoutInflater layoutInflater = (LayoutInflater) Game_Hajar.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.history_bid_details,null);

            final ListView lv=(ListView)customView.findViewById(R.id.lv_history);
            final Button close=(Button)customView.findViewById(R.id.list_close_btn);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    h_popupWindow2.dismiss();
                    bid_history.clear();
                }
            });
            StringRequest stringRequest3=new StringRequest(Request.Method.POST, HISTORY_BID, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array=new JSONArray(response);
                        for (int i=0;i<array.length();i++) {
                            JSONObject object = array.getJSONObject(i);
                            card_id=object.getString("id");
                            card_number=object.getString("card_number");
                            card_coin=object.getString("money");

                            Manno_DataModel dm=new Manno_DataModel();
                            dm.setCard_id(card_id);
                            dm.setCard_number(card_number);
                            dm.setCard_coin(card_coin);

                            bid_history.add(dm);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bid_History_Adapter adapter=new Bid_History_Adapter(Game_Hajar.this,bid_history);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }



            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Game_Hajar.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


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
            RequestQueue requestQueue=Volley.newRequestQueue(Game_Hajar.this);
            requestQueue.add(stringRequest3);





            //instantiate popup window
            h_popupWindow2 = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            h_popupWindow2.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);


        }

        private void load_list() {
        StringRequest request4=new StringRequest(Request.Method.GET, LIST_LOAD, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        model = new Hajar_Data_Model();
                        JSONArray array1 = array.getJSONArray(i);
                        for (int j = 0; j < array1.length(); j++) {
                            JSONObject object=array1.getJSONObject(j);

                            String cc = object.getString("cc");

                            Log.e("Res", ">>>>>>>" + cc );
                            //  String category=object.getString("category");

                            if (cc.equals("1")) {
                                String category = object.getString("category");
                                model.setCategory(category);
                            }if (cc.equals("2")) {
                                String category1 = object.getString("category");
                                model.setCategory1(category1);
                            }
                            if (cc.equals("3")) {
                                String category2 = object.getString("category");
                                model.setCategory2(category2);
                            }






                           /* if (cc.equals("1")) {

                                String category = object.getString("category");
                                Log.e("Res", ">>>>>>>" + cc + category);
                                model.setCc(cc);
                                model.setCategory(category);

                                list.add(model);
                                Hajar_Adapter adapter = new Hajar_Adapter(Game_Hajar.this, list);
                                lv_hajar1.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                            if (cc.equals("2")) {

                                String category = object.getString("category");
                                model.setCc(cc);
                                model.setCategory(category);

                                list1.add(model);
                                Hajar_Adapter adapter = new Hajar_Adapter(Game_Hajar.this, list1);
                                lv_hajar2.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                            if (cc.equals("3")) {

                                String category = object.getString("category");
                                model.setCc(cc);
                                model.setCategory(category);

                                list2.add(model);
                                Hajar_Adapter adapter = new Hajar_Adapter(Game_Hajar.this, list2);
                                lv_hajar3.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
*/
                        }
                        list.add(model);
                        Hajar_Adapter adapter = new Hajar_Adapter(Game_Hajar.this, list);
                        lv_hajar1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }



                        }
                        catch (JSONException e) {
                                e.printStackTrace();
                             }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Game_Hajar.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
        queue.add(request4);

    }

  /*  private void load_out_page() {

        StringRequest request=new StringRequest(Request.Method.GET, IN_CARD_LOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        String card=object.getString("category");

                        Glide.with(getApplicationContext()).load(card)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(image_out);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Game_Hajar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
       *//* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();

                return param;
            }
        }*//*;

        RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
        queue.add(request);


    }*/

    private void load_card() {

        StringRequest request=new StringRequest(Request.Method.GET, CARD_LOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                image_hajar_1.setImageResource(R.drawable.blank_card);
                image_hajar_2.setImageResource(R.drawable.blank_card);
                image_hajar_3.setImageResource(R.drawable.blank_card);

                try {
                    JSONArray array=new JSONArray(response);
                        for (int i=0;i<array.length();i++) {
                            JSONObject object = array.getJSONObject(i);
                          //  Log.e("Hajar_Res",">>>>>>>"+object);
                          //  String i_o = object.getString("i_o");
                            String cc = object.getString("count");
                            String cards = object.getString("category");

                            if (cc.trim().equals("1")) {

                                Glide.with(getApplicationContext()).load(cards)
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(image_hajar_1);
                                }

                                if (cc.trim().equals("2")) {
                                    Glide.with(getApplicationContext()).load(cards)
                                            .thumbnail(0.5f)
                                            .crossFade()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(image_hajar_2);


                                }


                           if (cc.trim().equals("3")) {
                                Glide.with(getApplicationContext()).load(cards)
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(image_hajar_3);

                               LayoutInflater li = getLayoutInflater();
                               //Getting the View object as defined in the customtoast.xml file
                               View layout = li.inflate(R.layout.custome_toast,(ViewGroup) findViewById(R.id.custom_toast_layout));
                               TextView txt=layout.findViewById(R.id.custom_toast_message);
                               txt.setText("New Game Start");
                               Toast toast = new Toast(getApplicationContext());
                               toast.setDuration(Toast.LENGTH_SHORT);
                               toast.setGravity(Gravity.TOP, 0, 0);
                               toast.setView(layout);
                               toast.show();
                            }


                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Game_Hajar.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        })
       /* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();

                return param;
            }
        }*/;

        RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
        queue.add(request);


    }

    private void popup_money() {
        LayoutInflater layoutInflater = (LayoutInflater) Game_Hajar.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.amount_page,null);

        final Button hundred=customView.findViewById(R.id.no100);
        final Button two_hundred=customView.findViewById(R.id.no200);
        final Button three_hundred=customView.findViewById(R.id.no300);
        final Button five_hundred=customView.findViewById(R.id.no500);
        final Button one_thousand=customView.findViewById(R.id.no1000);
        final Button two_thousand=customView.findViewById(R.id.no2000);
        final Button five_thousand=customView.findViewById(R.id.no5000);
        final Button ten_thousand=customView.findViewById(R.id.no10000);

        final Button close_amount=customView.findViewById(R.id.close_amount);

        two_thousand.setVisibility(View.GONE);
        five_thousand.setVisibility(View.GONE);
        ten_thousand.setVisibility(View.GONE);




        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        two_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(two_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        three_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(three_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        five_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(five_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        one_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(one_thousand.getText().toString());
                popupWindow1.dismiss();
            }
        });
        two_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(two_thousand.getText().toString());
                popupWindow1.dismiss();
            }
        });
        five_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_coin.setText(five_thousand.getText().toString());
                popupWindow1.dismiss();
            }
        });
        close_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow1.dismiss();
            }
        });
        //instantiate popup window
        popupWindow1 = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow1.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

    }


    private void popup() {
        LayoutInflater layoutInflater = (LayoutInflater) Game_Hajar.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.card_number,null);

        final Button one=customView.findViewById(R.id.no1);
        final Button two=customView.findViewById(R.id.no2);
        final Button three=customView.findViewById(R.id.no3);
        final Button four=customView.findViewById(R.id.no4);
        final Button five=customView.findViewById(R.id.no5);
        final Button six=customView.findViewById(R.id.no6);
        final Button seven=customView.findViewById(R.id.no7);
        final Button eight=customView.findViewById(R.id.no8);
        final Button nine=customView.findViewById(R.id.no9);
        final Button ten=customView.findViewById(R.id.no10);
        final Button j_card=customView.findViewById(R.id.j_card);
        final Button q_card=customView.findViewById(R.id.q_card);
        final Button k_card=customView.findViewById(R.id.k_card);
        final Button close_card=customView.findViewById(R.id.close_card);

        j_card.setVisibility(View.GONE);
        q_card.setVisibility(View.GONE);
        k_card.setVisibility(View.GONE);



        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            select_card.setText(one.getText().toString());
            popupWindow.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(two.getText().toString());
                popupWindow.dismiss();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(three.getText().toString());
                popupWindow.dismiss();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(four.getText().toString());
                popupWindow.dismiss();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(five.getText().toString());
                popupWindow.dismiss();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(six.getText().toString());
                popupWindow.dismiss();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(seven.getText().toString());
                popupWindow.dismiss();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(eight.getText().toString());
                popupWindow.dismiss();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(nine.getText().toString());
                popupWindow.dismiss();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_card.setText(ten.getText().toString());
                popupWindow.dismiss();
            }
        });
        close_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
            }
        });


        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        //close the popup wind

    }
    @Override
    protected void onPause() {
        super.onPause();
        repeatTask.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Game_Hajar.this,Second_Page.class);
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        repeatTask.cancel();
        //  <<-------ENSURE onStop()

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent=new Intent(Game_Hajar.this,Game_Hajar.class);
        startActivity(intent);
        finish();

    }
}
