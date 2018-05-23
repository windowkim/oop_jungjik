package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;

public class ContentsArrayActivity extends AppCompatActivity {

    private ArrayList<Content> contents = new ArrayList<Content>();
    private int Day=0, Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_array);

        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");
        Day = getIntent().getIntExtra("Day",1);
        TextView dayciew = (TextView) findViewById(R.id.dayview);
        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollview);
        LinearLayout categoryLinearLayout = (LinearLayout)findViewById(R.id.linearlayout);

        for (int i = 0; i < contents.size(); i++){
            if(Day==contents.get(i).getIntDay()) {
                final Button categoryButton = new Button (this);
                final int index = i;
                categoryButton.setTag(i);
                categoryButton.setText(contents.get(i).getName());
                categoryLinearLayout.addView(categoryButton);
                categoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ContentsArrayActivity.this, ContentActivity.class);
                        intent.putExtra("Content", contents.get(index));
                        Index = index;
                        ContentsArrayActivity.this.startActivity(intent);
                    }
                });
            }
        }
        dayciew.setText(String.valueOf(Day+1)+" 일차 컨텐츠");

    }
}
