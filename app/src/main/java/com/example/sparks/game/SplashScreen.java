package com.example.sparks.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.sparks.game.Registration_Login.Login;

import static com.example.sparks.game.Registration_Login.Login.KEY_Email;

public class SplashScreen extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if (sharedpreferences.contains(KEY_Email)) {
                        Intent intent = new Intent(getApplicationContext(),Second_Page.class);

                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),Login.class);

                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
