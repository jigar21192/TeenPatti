package fungame.idea.sparks.fungame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fungame.idea.sparks.fungame.R;

import fungame.idea.sparks.fungame.Registration_Login.Login;

public class Setting_Activity extends AppCompatActivity {
    private SoundPlayer soundPlayer;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String CHANGE_PASS="http://jmfungame.com/chagepass.php";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout linearLayout;
    private Sound sound1;
    PopupWindow m_popupWindow2;
    Switch sound;
    TextView logout,change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        change_password=findViewById(R.id.change_password);
        linearLayout=findViewById(R.id.l7);
        editor = sharedPreferences.edit();
        soundPlayer=new SoundPlayer(this);
        sound1=new Sound();

        sound=findViewById(R.id.soundswitch);
        logout=findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Intent logout=new Intent(Setting_Activity.this,Login.class);
                startActivity(logout);
                finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = (LayoutInflater) Setting_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.change_password_layout,null);

                final Button change=(Button)customView.findViewById(R.id.btn_change_password);

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, CHANGE_PASS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array=new JSONArray(response);
                                    for (int i=0;i<array.length();i++) {
                                        JSONObject object = array.getJSONObject(i);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }



                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Setting_Activity.this,"Please Check Internet Connection", Toast.LENGTH_SHORT).show();


                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>param=new HashMap<>();
                                param.put("id","");
                                param.put("old_pass","");
                                param.put("new_pass","");



                                return param;
                            }
                        };
                        RequestQueue requestQueue=Volley.newRequestQueue(Setting_Activity.this);
                        requestQueue.add(stringRequest);




                        m_popupWindow2.dismiss();
                    }
                });


                //instantiate popup window
                m_popupWindow2 = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //display the popup window
                m_popupWindow2.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);



            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sound.isChecked()){
                    soundPlayer.hitsound();
                  //  startService(new Intent(Setting_Activity.this,Sound.class));
                    Toast.makeText(Setting_Activity.this, "Sound On", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(Setting_Activity.this, "Sound Off", Toast.LENGTH_SHORT).show();
                    soundPlayer.oversound();
                 //   stopService(new Intent(Setting_Activity.this,Sound.class));


                }
            }
        });

        }
    @Override
    protected void onStop() {
        super.onStop();        //  <<-------ENSURE onStop()
        soundPlayer.oversound();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent intent=new Intent(Setting_Activity.this,Second_Page.class);
       startActivity(intent);
    }





}
