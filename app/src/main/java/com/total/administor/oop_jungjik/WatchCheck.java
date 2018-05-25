package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WatchCheck extends AppCompatActivity {


    ArrayList<Content> contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_check);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");

        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.watchLinearLayout);


        for(int i=0;i<contents.size();i++)
        {
            TextView text = new TextView(this);
            text.setTag(i);
            text.setTypeface(typeface);
            text.setTextSize(27);
            text.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100,20,0,0);

            text.setText(contents.get(i).getName()+"			"+Integer.toString(contents.get(i).getMinute())+"분 "+Integer.toString(contents.get(i).getSeond())+"초");
            text.setTextColor(Color.BLACK);
            text.setLayoutParams(params);
            linearLayout.addView(text,i);

        }


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WatchCheck.this, MainActivity.class);
        intent.putExtra("Contents",contents);

        setResult(1,intent);
        finish();


        super.onBackPressed();
    }
}
