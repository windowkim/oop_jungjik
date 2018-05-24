package com.total.administor.oop_jungjik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText idtext = (EditText) findViewById(R.id.idtext);
        final EditText passwordtext = (EditText) findViewById(R.id.passwordtext);
        final Button loginbutton = (Button) findViewById(R.id.loginbutton);
        final TextView registerbutton = (TextView) findViewById(R.id.registerview);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerintent);
                finish();
            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid = idtext.getText().toString();
                final String userpassword = passwordtext.getText().toString();
                if(isNumber(userid)) { // 지울것
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("로그인에 성공하였습니다.")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            User user = (User) getIntent().getSerializableExtra("User");
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("User", user);

                                            LoginActivity.this.startActivity(intent);
                                            finish();
                                        }
                                    }
                            )
                            .create().show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("로그인에 실패하였습니다.")
                            .setNegativeButton("다시 시도", null)
                            .create().show();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userid = jsonResponse.getString("userID");
                                String userpassword = jsonResponse.getString("userPassword");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 성공하였습니다.")
                                        .setPositiveButton("확인",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        LoginActivity.this.startActivity(intent);
                                                    }
                                                }
                                        )
                                        .create().show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create().show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userid,userpassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }


    public static boolean isNumber(String str) {
        boolean check = true;
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) {
                check = false;
                break;
            }
        }
        return check;
    }
}
