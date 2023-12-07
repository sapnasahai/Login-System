package com.example.loginpage;

import static androidx.core.view.GravityCompat.apply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    MaterialButton login_btn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);



        //

        login_btn = (MaterialButton) findViewById(R.id.sign_in_btn1);
        DB = new DBHelper(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = DB.checkUsernamePassword(user,pass);
                     if(checkuserpass==true){

                         Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                         startActivity(intent);
                         //finish();
                     } else{
                         Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                     }

                }

            }
        });


    }
}