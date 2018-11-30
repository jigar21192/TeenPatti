package com.example.sparks.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparks.game.Registration_Login.Login;

public class Setting_Activity extends AppCompatActivity {
    private SoundPlayer soundPlayer;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Sound sound1;
    Switch sound;
    TextView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

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





}
