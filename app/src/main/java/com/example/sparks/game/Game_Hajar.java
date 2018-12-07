package com.example.sparks.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.sparks.game.Registration_Login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Game_Hajar extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    public static final String ID= "id";
    String IN_CARD_LOAD="http://sabkuchhbechde.ga/teenpatti/hazar_res.php";
    String BID_URL="http://sabkuchhbechde.ga/teenpatti/bid_details.php";
    SharedPreferences sharedpreferences;
    LinearLayout in_page,out_page,linearLayout;
    PopupWindow popupWindow,popupWindow1;
    RelativeLayout relativeLayout;
    Button select_card,select_money,bid;
    TextView username;
    ImageView image_in,image_out;
    ToggleButton toggleButton;
    Timer repeatTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__hajar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String user=sharedpreferences.getString("email","");
        final String id=sharedpreferences.getString("id","");

        toggleButton=findViewById(R.id.in_out);
        relativeLayout=findViewById(R.id.r1);
        linearLayout=findViewById(R.id.linear1);
        select_card=findViewById(R.id.select_card);
        select_money=findViewById(R.id.select_money);
        bid=findViewById(R.id.bid);
        image_in=findViewById(R.id.image_in);
        image_out=findViewById(R.id.image_out);
        username=findViewById(R.id.user);
        username.setText(user);

        in_page=findViewById(R.id.l3);
        out_page=findViewById(R.id.l4);


        repeatTask = new Timer();
        repeatTask.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_in_page();

                  //      load_out_page();

                        Toast.makeText(Game_Hajar.this, "hi", Toast.LENGTH_SHORT).show();

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String card=select_card.getText().toString();
                final String money=select_money.getText().toString();
                final String in_out=toggleButton.getText().toString();

                if (card.equals("CARD")){
                    Toast.makeText(Game_Hajar.this, "Select any Card", Toast.LENGTH_SHORT).show();
                }else if (money.equals("MONEY")){
                    Toast.makeText(Game_Hajar.this, "Select Money", Toast.LENGTH_SHORT).show();
                    }else {

                StringRequest request=new StringRequest(Request.Method.POST, BID_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Game_Hajar.this, response, Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Game_Hajar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>param=new HashMap<>();
                        param.put("id",id);
                        param.put("in_out",in_out);
                        param.put("card_number",card);
                        param.put("money",money);
                        return param;
                    }
                };

                RequestQueue queue=Volley.newRequestQueue(Game_Hajar.this);
                queue.add(request);


            }}
        });

        select_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instantiate the popup.xml layout file
               popup();
            }
        });
        select_money.setOnClickListener(new View.OnClickListener() {
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

    private void load_in_page() {

        StringRequest request=new StringRequest(Request.Method.GET, IN_CARD_LOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);
                        for (int i=0;i<array.length();i++) {
                            JSONObject object = array.getJSONObject(i);
                            String i_o=object.getString("i_o");
                            String card=object.getString("category");

                            if (i_o.trim().equals("in")){

                            Glide.with(getApplicationContext()).load(card)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(image_in);}
                                    else {
                                Glide.with(getApplicationContext()).load(card)
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(image_out);}


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




        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        two_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(two_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        three_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(three_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        five_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(five_hundred.getText().toString());
                popupWindow1.dismiss();
            }
        });
        one_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(one_thousand.getText().toString());
                popupWindow1.dismiss();
            }
        });
        two_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(two_thousand.getText().toString());
                popupWindow1.dismiss();
            }
        });
        five_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_money.setText(five_thousand.getText().toString());
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

        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        //close the popup wind

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Game_Hajar.this,Second_Page.class);
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();//  <<-------ENSURE onStop()


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repeatTask.cancel();
    }
}
