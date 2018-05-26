package com.total.administor.oop_jungjik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idtext = (EditText) findViewById(R.id.idtext);
        final EditText passwordtext = (EditText) findViewById(R.id.passwordtext);
        final EditText nametext = (EditText) findViewById(R.id.nametext);
        final EditText codetext = (EditText) findViewById(R.id.codetext);
        final Button registerbutton = (Button) findViewById(R.id.registerbutton);


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = idtext.getText().toString();
                final String userpassword = passwordtext.getText().toString();
                final String username = nametext.getText().toString();
                final String code = codetext.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success =  jsonResponse.getBoolean("Success");
                            if(success &&userid.length()==8&&isNumber(userid)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공하였습니다.")
                                        .setPositiveButton("확인",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        RegisterActivity.this.startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        )
                                        .create().show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create().show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userid,userpassword,username,code,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

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
