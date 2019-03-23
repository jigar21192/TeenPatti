package fungame.idea.sparks.fungame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Janno_Game extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    public static final String ID= "id";

    ProgressDialog pd;
    String c_id1;
    String BID_DETAILS="http://jmfungame.com/janno_history.php";
    String USER_DETAILS="http://jmfungame.com/user_details.php";
    String LIST_RES="http://jmfungame.com/janno_res2.php";
    String IN_CARD_LOAD="http://jmfungame.com/janno_res.php";
    String BID_URL="http://jmfungame.com/janno_bid.php";
    String Last_Card="http://jmfungame.com/janno_last_card.php";
    String AUTO="http://jmfungame.com/auto.php";
    String TYPE="http://jmfungame.com/time.php";
    SharedPreferences sharedpreferences;
    LinearLayout j_in_page,j_out_page,j_linearLayout;
    PopupWindow j_popupWindow,j_popupWindow1,j_popupWindow2;
    ConstraintLayout j_relativeLayout;
    Button j_select_card,j_select_coin,j_bid,j_history_bid;
    TextView j_username,j_balance,j_counter_1,j_counter_2;
    String name,balance, id,card_id,card_number,card_coin,type;

    List<String> in_list;
    List<String>out_list;
    List<Manno_DataModel>bid_history;
    ListView j_lv_in,j_lv_out;
    ImageView j_image_in,j_image_out,j_image_last,j_outcard,j_incard;
    Timer j_repeatTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janno_game);
        pd=new ProgressDialog(Janno_Game.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String user=sharedpreferences.getString("email","");
        id=sharedpreferences.getString("id","");
        j_history_bid=findViewById(R.id.j_history_bid);
       // counter_1=findViewById(R.id.m_txt8);
      //  counter_2=findViewById(R.id.m_txt9);

        j_lv_in=findViewById(R.id.j_lv_in);
        j_lv_out=findViewById(R.id.j_lv_out);
        j_relativeLayout=findViewById(R.id.j_r1);
        j_linearLayout=findViewById(R.id.linear1);
        j_select_card=findViewById(R.id.j_select_card);
        j_select_coin=findViewById(R.id.j_select_coin);
        j_bid=findViewById(R.id.j_bid);
        j_image_in=findViewById(R.id.j_image_in);
        j_image_out=findViewById(R.id.j_image_out);
        j_username=findViewById(R.id.j_user);
        j_balance=findViewById(R.id.j_balance);
      //  j_image_last=findViewById(R.id.j_image_last);
        j_outcard=findViewById(R.id.j_image_out1);
        j_incard=findViewById(R.id.j_image_in1);


        j_in_page=findViewById(R.id.j_l6);
        j_out_page=findViewById(R.id.j_l7);






        j_repeatTask = new Timer();
        j_repeatTask.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // load_auto();
                        load_last_card();
                        load_in_page();
                        /*in_list=new ArrayList<>();
                        out_list=new ArrayList<>();*/
                        bid_history=new ArrayList<>();
                        list_view_details();
                        load_type();



                        // load_out_page();

                        StringRequest stringRequest=new StringRequest(Request.Method.POST, USER_DETAILS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        name = object.getString("name");
                                        balance = object.getString("janno_coin");


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                j_balance.setText(balance);
                                j_username.setText(name);


                            }



                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


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
                        RequestQueue requestQueue= Volley.newRequestQueue(Janno_Game.this);
                        requestQueue.add(stringRequest);




                    }
                });
            }
        }, 0, 5000);


        j_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String card=j_select_card.getText().toString();
                final String coin=j_select_coin.getText().toString();

                if (card.equals("CARD")){
                    Toast.makeText(Janno_Game.this, "Select any Card", Toast.LENGTH_SHORT).show();
                }else if (coin.equals("COIN")){
                    Toast.makeText(Janno_Game.this, "Select Money", Toast.LENGTH_SHORT).show();
                }else {
                    pd.setMessage("loading...");
                    pd.show();

                    StringRequest request=new StringRequest(Request.Method.POST, BID_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().equals("success")) {
                                pd.dismiss();
                                Toast.makeText(Janno_Game.this, "BID Success", Toast.LENGTH_SHORT).show();
                            }else if (response.trim().equals("Increase_Money")){
                                pd.dismiss();
                                Toast.makeText(Janno_Game.this, "Increase Your Coin", Toast.LENGTH_SHORT).show();

                            }else if(response.trim().equals("Wait For Game Start")){

                                pd.dismiss();
                                Toast.makeText(Janno_Game.this, "Game Not Started", Toast.LENGTH_SHORT).show();

                            }else {
                                pd.dismiss();
                                Toast.makeText(Janno_Game.this, "Problem On BID", Toast.LENGTH_SHORT).show();


                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(Janno_Game.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>param=new HashMap<>();
                            param.put("id",id);
                            param.put("in_out",card);
                            param.put("money",coin);
                            return param;
                        }
                    };

                    RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
                    queue.add(request);


                }}
        });

        j_history_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_history();
            }
        });

        j_select_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });
        j_select_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_money();
            }
        });

    }

    private void load_type() {
        StringRequest request1=new StringRequest(Request.Method.GET, TYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RRRRRRRhhhh",">>>>>>>"+response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        type = object.getString("text");
                        Log.e("hhhh",">>>>>>>"+type);
                        if (type.equals("New_game")){
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

                            in_list=new ArrayList<>();
                            out_list=new ArrayList<>();
                            in_list.clear();
                            out_list.clear();

                            j_lv_in.setAdapter(null);
                            j_lv_out.setAdapter(null);
                            j_image_in.setImageResource(R.drawable.blank_card);
                            j_image_out.setImageResource(R.drawable.blank_card);




                        }else if (type.equals("not")){




                        }else {
                              LayoutInflater li = getLayoutInflater();
                            //Getting the View object as defined in the customtoast.xml file
                            View layout = li.inflate(R.layout.custome_toast,(ViewGroup) findViewById(R.id.custom_toast_layout));
                            TextView txt=layout.findViewById(R.id.custom_toast_message);
                            txt.setText(type);
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
                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
        queue.add(request1);
    }

    private void load_auto() {
        StringRequest request1=new StringRequest(Request.Method.GET, AUTO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
        queue.add(request1);


    }





    private void load_last_card() {
        StringRequest request2=new StringRequest(Request.Method.GET, Last_Card, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        String lastid=object.getString("lastid");
                        String incard=object.getString("incard");
                        String outcard=object.getString("outcard");

                       /* Glide.with(getApplicationContext()).load(lastid)
                                .thumbnail(0.5f)
                                .override(80,90)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(j_image_last);*/
                        Glide.with(getApplicationContext()).load(incard)
                                .thumbnail(0.5f)
                                .override(80,90)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(j_incard);
                        Glide.with(getApplicationContext()).load(outcard)
                                .thumbnail(0.5f)
                                .override(80,90)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(j_outcard);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        })
       /* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                return param;
            }
        }*/;

        RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
        queue.add(request2);


    }



    public void popup_history() {

        LayoutInflater layoutInflater = (LayoutInflater) Janno_Game.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.history_bid_details,null);

        final ListView lv=(ListView)customView.findViewById(R.id.lv_history);
        final Button close=(Button)customView.findViewById(R.id.list_close_btn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_popupWindow2.dismiss();
                bid_history.clear();
            }
        });
        StringRequest stringRequest3=new StringRequest(Request.Method.POST, BID_DETAILS, new Response.Listener<String>() {
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
                Bid_History_Adapter adapter=new Bid_History_Adapter(Janno_Game.this,bid_history);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


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
        RequestQueue requestQueue=Volley.newRequestQueue(Janno_Game.this);
        requestQueue.add(stringRequest3);





        //instantiate popup window
        j_popupWindow2 = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        j_popupWindow2.showAtLocation(j_relativeLayout, Gravity.CENTER, 0, 0);


    }


    private void list_view_details() {
        StringRequest request4=new StringRequest(Request.Method.GET, LIST_RES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Res",">>>>"+response);

                in_list=new ArrayList<>();
                out_list=new ArrayList<>();

                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        String i_o=object.getString("i_o");




                        if (i_o.trim().equals("in")){

                            String card=object.getString("category");
                            in_list.add(card);

                            Manno_Adapter adapter=new Manno_Adapter(Janno_Game.this,in_list);
                            j_lv_in.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        else if (i_o.trim().equals("out")){

                            String card=object.getString("category");
                            out_list.add(card);

                            Manno_Adapter adapter=new Manno_Adapter(Janno_Game.this,out_list);
                            j_lv_out.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else{

                         /*
                            in_list.clear();
                            out_list.clear();
                            Manno_Adapter adapter=new Manno_Adapter(Janno_Game.this,in_list);
                            j_lv_in.setAdapter(adapter);
                            Manno_Adapter adapter1=new Manno_Adapter(Janno_Game.this,out_list);
                            j_lv_out.setAdapter(adapter1);*/

                        }




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Janno_Game.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
        queue.add(request4);

    }


    private void load_in_page() {

        StringRequest request5=new StringRequest(Request.Method.GET, IN_CARD_LOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        String c_id = object.getString("c_id");

                        String i_o = object.getString("i_o");
                        String card = object.getString("category");
                        String counter = object.getString("counter");
                      //  String msg = object.getString("msg");

                    Log.e("Image",">>>>>>>"+i_o);


                        if (i_o.trim().equals("in")) {
                            //counter_2.setText(counter);


                            Glide.with(getApplicationContext()).load(card)
                                    .thumbnail(0.5f)
                                    .override(80,90)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(j_image_in);
                        } else {

                          //  counter_1.setText(counter);

                            Glide.with(getApplicationContext()).load(card)
                                    .thumbnail(0.5f)
                                    .override(80,90)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(j_image_out);
                        }
                   /*     if (msg.equals("new_game")){
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

                        }*/


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Janno_Game.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        })
       /* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                return param;
            }
        }*/;

        RequestQueue queue=Volley.newRequestQueue(Janno_Game.this);
        queue.add(request5);


    }

    private void popup_money() {
        LayoutInflater layoutInflater = (LayoutInflater) Janno_Game.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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




        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(hundred.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        two_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(two_hundred.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        three_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(three_hundred.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        five_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(five_hundred.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        one_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(one_thousand.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        two_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(two_thousand.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        five_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(five_thousand.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        ten_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_coin.setText(ten_thousand.getText().toString());
                j_popupWindow1.dismiss();
            }
        });
        close_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_popupWindow1.dismiss();
            }
        });


        //instantiate popup window
        j_popupWindow1 = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        j_popupWindow1.showAtLocation(j_relativeLayout, Gravity.CENTER, 0, 0);

    }


    private void popup() {
        LayoutInflater layoutInflater = (LayoutInflater) Janno_Game.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.in_out_card,null);

        Button in=customView.findViewById(R.id.in_card_image);
        final Button out=customView.findViewById(R.id.out_card_image);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText("in");
                j_popupWindow.dismiss();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText("out");
                j_popupWindow.dismiss();
            }
        });
        /*final Button one=customView.findViewById(R.id.no1);
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
        ten.setText("10");

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(one.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(two.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(three.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(four.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(five.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(six.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(seven.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(eight.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(nine.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(ten.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        j_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(j_card.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        q_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(q_card.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        k_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j_select_card.setText(k_card.getText().toString());
                j_popupWindow.dismiss();
            }
        });
        close_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j_popupWindow.dismiss();
            }
        });
*/
        //instantiate popup window
        j_popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        j_popupWindow.showAtLocation(j_relativeLayout, Gravity.CENTER, 0, 0);

        //close the popup wind

    }

    @Override
    protected void onPause() {
        super.onPause();
        j_repeatTask.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Janno_Game.this,Second_Page.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        j_repeatTask.cancel();
        //  <<-------ENSURE onStop()

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent=new Intent(Janno_Game.this,Janno_Game.class);
        startActivity(intent);
        finish();

    }


}
