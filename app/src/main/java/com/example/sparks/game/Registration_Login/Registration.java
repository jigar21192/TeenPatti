package com.example.sparks.game.Registration_Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sparks.game.MainActivity;
import com.example.sparks.game.R;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    EditText name,email,password,confpassword;
    Button register,loginPage;
    RadioGroup gender;
    RadioButton genderradioButton;
    String na,mail,pass,confpass,selectgender,finalpass;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=findViewById(R.id.signup_input_name);
        email=findViewById(R.id.signup_input_email);
        password=findViewById(R.id.signup_input_password);
        confpassword=findViewById(R.id.signup_input_confpassword);
        gender=findViewById(R.id.gender_radio_group);
        register=findViewById(R.id.btn_signup);
        loginPage=findViewById(R.id.btn_link_login);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                na=name.getText().toString();
                mail=email.getText().toString();
                pass=password.getText().toString();
                confpass=confpassword.getText().toString();
                int selectedId = gender.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(Registration.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    selectgender=genderradioButton.getText().toString();
                }
                isvalidate();



                Log.e("Ans",">>>>>>>"+na+mail+finalpass+selectgender);
            }
        });


    }

    private void isvalidate() {
        if (na.isEmpty()){
            Toast.makeText(Registration.this, "Enter Name", Toast.LENGTH_SHORT).show();
        }else if (mail.isEmpty()){
            Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_SHORT).show();
        }else if(!EMAIL_ADDRESS_PATTERN.matcher(mail).matches()){
            Toast.makeText(Registration.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();

        }else if (pass.isEmpty()){
            Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_SHORT).show();
        }

        else if (pass.equals(confpass)){
            finalpass=pass;
        }else
        {
            Toast.makeText(Registration.this, "Confirm Password Does Not Match", Toast.LENGTH_SHORT).show();
        }
    }
}
