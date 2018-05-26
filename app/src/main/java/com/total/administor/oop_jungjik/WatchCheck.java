package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WatchCheck extends AppCompatActivity {


    private ArrayList<Content> contents;
    private User user;
    private int watchBit;
    private ArrayList<Integer> arrWatch=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_check);



        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");
        user = (User) getIntent().getSerializableExtra("User");

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.watchLinearLayout);
         final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    watchBit = jsonResponse.getInt("ViewRecord");
                    String isWatch = Integer.toBinaryString(watchBit);
                    Log.v("isWatch", isWatch);
                    Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
                    if(isWatch.length() > 0) {

                        for (int i = 0; i < isWatch.length(); i++) {
                            Log.v("isW", Integer.toString(isWatch.length()));
                            arrWatch.add(Character.getNumericValue(isWatch.charAt(isWatch.length() - i - 1)));
                        }
                        for(int i=0;i<contents.size();i++)
                        {
                            LinearLayout ll = new LinearLayout(WatchCheck.this);
                            //ll.setGravity(Gravity.TOP)
                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params1.weight = 1;
                            ll.setVerticalGravity(Gravity.TOP);
                            //ll.setPadding(0,50,0,0);
                            ll.setOrientation((LinearLayout.HORIZONTAL));
                            TextView text = new TextView(WatchCheck.this);
                            text.setTag(i);
                            text.setLayoutParams(params1);
                            text.setTypeface(typeface);

                            text.setTextSize(27);
                            text.setMaxWidth(findViewById(R.id.textView).getWidth());
                            text.setGravity(Gravity.LEFT);
                            //text.

                            text.setText(contents.get(i).getName());
                            ll.addView(text);


                            if (user.isEditor()) //
                            {
                                TextView text2 = new TextView(WatchCheck.this);
                                text2.setTag(i);
                                text2.setTypeface(typeface);
                                text2.setTextSize(27);
                                text2.setGravity(Gravity.RIGHT);
                                text2.setText(Integer.toString(contents.get(i).getMinute())+"분 "+Integer.toString(contents.get(i).getSeond())+"초          ");
                                params1.weight = 1;
                                text2.setLayoutParams(params1);
                                ll.addView(text2);
                           }



                            TextView text3 = new TextView(WatchCheck.this);
                            text3.setTag(i);
                            text3.setTypeface(typeface);
                            text3.setTextSize(27);
                            params1.weight = 1;
                            text3.setLayoutParams(params1);
                            text3.setGravity(Gravity.LEFT);
                            text3.setText("N");

                            if(arrWatch.size() >= i+1) {
                                if (arrWatch.get(i) == 1)
                                    text3.setText("Y");

                            }
                            ll.addView(text3);
                            params.setMargins(0,0,0,40);
                            //ll.Mar
                        ll.setLayoutParams(params);
                           // linearLayout.setLayoutParams(params1);
                            linearLayout.addView(ll);
                        }

                    }
                    else
                        arrWatch.add(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ConfirmRequest confirmRequest = new ConfirmRequest(user.getID(),responseListener);
        RequestQueue queue = Volley.newRequestQueue(WatchCheck.this);
        queue.add(confirmRequest);




    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WatchCheck.this, MainActivity.class);
        intent.putExtra("Contents",contents);
        //intent.putExtra("User",user);
        setResult(1,intent);
        finish();


        super.onBackPressed();
    }
}
