package com.total.administor.oop_jungjik;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

    private ArrayList<Content> contents = new ArrayList<Content>();
    private final int editorcontentactivity = 1;
    private int Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        contents = (ArrayList<Content>) getIntent().getSerializableExtra("Contents");
        final TextView text = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("Contents",contents);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollview);
        LinearLayout categoryLinearLayout = (LinearLayout)findViewById(R.id.linearlayout);

        for (int i = 0; i < contents.size(); i++){
            final Button categoryButton = new Button (this);
            final int index = i;
            categoryButton.setTag(i);
            categoryButton.setText(contents.get(i).getName());
            categoryLinearLayout.addView(categoryButton);
            categoryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(EditorActivity.this,EditContentActivity.class);
                    intent.putExtra("Content",contents.get(index));
                    Index = index;
                    EditorActivity.this.startActivityForResult(intent,editorcontentactivity);
                    Log.e("Tag",""+index);
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
            Toast.makeText(getApplicationContext(),pcontent.getDay(),Toast.LENGTH_SHORT).show();
        }
    }
}