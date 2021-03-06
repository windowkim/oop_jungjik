package com.total.administor.oop_jungjik;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

    private ArrayList<Content> contents = new ArrayList<Content>();
    private User user;

    private final int editorcontentactivity = 1;
    private int Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        final TextView text = (TextView) findViewById(R.id.text);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
        text.setTypeface(typeface);

        TextView temp = (TextView) findViewById(R.id.text);
        temp.setTypeface(typeface);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                intent.putExtra("Contents",contents);

                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollview);
        LinearLayout categoryLinearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");
        user = (User) getIntent().getSerializableExtra("User");


        for (int i = 0; i < contents.size(); i++){
            final Button categoryButton = new Button (this);
            final int index = i;
            categoryButton.setTag(i);
            categoryButton.setText(contents.get(i).getName());
            categoryButton.setGravity(Gravity.CENTER);
            categoryButton.setPadding(0,0,20,9);
            categoryButton.setTypeface(typeface);
            categoryButton.setTextSize(20);
            categoryButton.setBackgroundResource(R.drawable.long_button);
            categoryLinearLayout.addView(categoryButton);

            //categoryButton.setBackgroundColor(Color.parseColor("#FFFF4081"));
            categoryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(EditorActivity.this,EditContentActivity.class);
                    intent.putExtra("Content",contents.get(index));
                    intent.putExtra("User",user);
                    Index = index;
                    EditorActivity.this.startActivityForResult(intent,editorcontentactivity);
                }
            });
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        if (requestCode == editorcontentactivity) {
            Content pcontent= (Content) data.getSerializableExtra("Content");
            contents.get(Index).setDay(pcontent.getDay(),pcontent.getIntDay());
            contents.get(Index).setMinute(pcontent.getMinute());
            contents.get(Index).setSecond(pcontent.getSeond());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditorActivity.this, MainActivity.class);
        intent.putExtra("Contents",contents);

        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}
