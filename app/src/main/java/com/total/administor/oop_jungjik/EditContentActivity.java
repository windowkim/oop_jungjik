package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class EditContentActivity extends AppCompatActivity {

    private Content content = new Content();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);

        user = (User) getIntent().getSerializableExtra("User");

        getSupportActionBar().setTitle(content.getName());

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");

        content = (Content) getIntent().getSerializableExtra("Content");
        Button editorbutton = (Button) findViewById(R.id.editorbutton);
        TextView daytext = (TextView) findViewById(R.id.dayview);
        daytext.setTypeface(typeface);
        TextView timetext = (TextView) findViewById(R.id.timeview);
        timetext.setTypeface(typeface);
        final Spinner dayspinner = (Spinner) findViewById(R.id.dayspinner);
        final Spinner minutespinner = (Spinner) findViewById(R.id.minutespinner);
        final Spinner secondspinner = (Spinner) findViewById(R.id.secondspinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.Day, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayspinner.setAdapter(adapter1);
        dayspinner.setSelection(content.getIntDay());
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.Minute, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutespinner.setAdapter(adapter2);
        minutespinner.setSelection(content.getMinute());
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.Second, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondspinner.setAdapter(adapter3);
        secondspinner.setSelection(content.getSeond()/5);

        editorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content.setDay((String) dayspinner.getSelectedItem(),dayspinner.getSelectedItemPosition());
                content.setMinute(Integer.parseInt((String) minutespinner.getSelectedItem()));
                content.setSecond(Integer.parseInt((String) secondspinner.getSelectedItem()));

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("Success");
                            if(success)
                            {
                                Toast.makeText(getApplicationContext(),"변경에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"변경에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditorRequest editorRequest = new EditorRequest(content.getName(),(String)dayspinner.getSelectedItem(),(String) minutespinner.getSelectedItem(),(String)secondspinner.getSelectedItem(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditContentActivity.this);
                queue.add(editorRequest);





                Intent intent = new Intent();
                intent.putExtra("Content", content);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
