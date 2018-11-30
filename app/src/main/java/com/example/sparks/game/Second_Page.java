package com.example.sparks.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Second_Page extends AppCompatActivity {
    ImageView setting;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_Email = "email";
    SharedPreferences sharedpreferences;
    CardView hajar;
    TextView username;
   // private SoundPlayer soundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__page);
       // soundPlayer=new SoundPlayer(this);
        setting=findViewById(R.id.setting);
        username=findViewById(R.id.name);

        hajar=findViewById(R.id.card1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String user=sharedpreferences.getString("email","");

        username.setText(user);

        hajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Second_Page.this,Game_Hajar.class);
                startActivity(intent);
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
