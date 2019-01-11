package fungame.idea.sparks.fungame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Second_Page extends AppCompatActivity {
    ImageView setting;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    String USER_DETAILS="http://jmfungame.com/user_details.php";
    SharedPreferences sharedpreferences;
    CardView hajar,manno,janno;
    TextView username,bal;
    String name,balance,id;
   // private SoundPlayer soundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__page);
       // soundPlayer=new SoundPlayer(this);
        setting=findViewById(R.id.setting);
        username=findViewById(R.id.name);
        bal=findViewById(R.id.balance);
        hajar=findViewById(R.id.card1);
        manno=findViewById(R.id.card2);
        janno=findViewById(R.id.card3);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String user=sharedpreferences.getString("email","");
        id=sharedpreferences.getString("id","");


        StringRequest stringRequest=new StringRequest(Request.Method.POST, USER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        name=object.getString("name");
                        balance=object.getString("coin");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bal.setText(balance);
                username.setText(name);


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Second_Page.this, "Server Problem", Toast.LENGTH_SHORT).show();


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
        RequestQueue requestQueue=Volley.newRequestQueue(Second_Page.this);
        requestQueue.add(stringRequest);




        hajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Second_Page.this,Game_Hajar.class);
                startActivity(intent);
                finish();


            }
        });
        manno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Second_Page.this,Manno_Game.class);
                startActivity(intent);
                finish();


            }
        });
        janno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Second_Page.this, "Work In Progress", Toast.LENGTH_SHORT).show();
               /* Intent intent=new Intent(Second_Page.this,Game_Hajar.class);
                startActivity(intent);
                finish();*/


            }
        });


   /*     if (!soundPlayer.myPlayer.isPlaying()){
            soundPlayer.hitsound();
        }*/

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(Second_Page.this,Setting_Activity.class);
            startActivity(intent);
            finish();



            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //soundPlayer.oversound();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // soundPlayer.hitsound();
    }
}
