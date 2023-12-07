package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    SharedPreferences sharedPreferences;
     EditText username, password,re_password;
     MaterialButton sign_in ,signup;
     DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        re_password = (EditText)  findViewById(R.id.re_password);


        //for get userid in homepage(whiteboard)
        sharedPreferences = getSharedPreferences("HomeActivity",MODE_PRIVATE);
        intent = new Intent(MainActivity.this, HomeActivity.class);
        if (sharedPreferences.contains("user") && sharedPreferences.contains("pass")){
            startActivity(intent);
        }//


        signup = (MaterialButton) findViewById(R.id.signup_btn);
        sign_in = (MaterialButton) findViewById(R.id.sign_in_btn);
        DB = new DBHelper(this);

        //admin and admin

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = re_password.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkUsername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert) {
                                 // for take username on whiteboard
                                sharedPreferences.edit()
                                        .putString("user", user)
                                        .apply();
                                //


                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}