package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");

        LinearLayout

        for(int i=0;i<contents.size();i++)
        {
            TextView text = new TextView(this);
            text.setTag(i);
            text.setText(contents.get(i).getName());

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
