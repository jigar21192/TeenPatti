package com.example.sparks.game.Registration_Login;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sparks.game.R;
import com.example.sparks.game.Second_Page;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


  EditText email,password;
  Button login;
  String Login_URL="http://sabkuchhbechde.ga/teenpatti/login.php";
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public static final String ID = "id";
    public static final String KEY_Email = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        email=findViewById(R.id.login_input_email);
        password=findViewById(R.id.login_input_password);
        login=findViewById(R.id.btn_login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail=email.getText().toString();
                final String pass=password.getText().toString();

                StringRequest request=new StringRequest(Request.Method.POST, Login_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("password_wrong")){
                            Toast.makeText(Login.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(Login.this, "Id"+response, Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(KEY_Email, mail );
                            editor.putString(ID, response );

                            editor.commit();
                            Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Login.this,Second_Page.class);
                            startActivity(intent);

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>param=new HashMap<>();
                        param.put("email",mail);
                        param.put("password",pass);
                        return param;
                    }
                };

                RequestQueue queue=Volley.newRequestQueue(Login.this);
                queue.add(request);


            }
        });

    }
    }




