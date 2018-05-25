package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.ArrayList;


public class MessageFragment extends Fragment {
    private WebView webView;
    private WebSettings webSettings;
    User user = new User(1,"1");
    ArrayList<Content> contents = new ArrayList<Content>();

    public static MessageFragment newInstance(User _user, ArrayList<Content> _contents)
    {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putSerializable("User", _user);
        args.putSerializable("Contents", _contents);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            user = (User) getArguments().getSerializable("User");
            contents = (ArrayList<Content>) getArguments().getSerializable("Contents");
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message,container,false);
        webView = (WebView) view.findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/school_song.html");






        //View view = inflater.inflate(R.layout.fragment_message,container,false);

        Button editorbutton = (Button) view.findViewById(R.id.editorbutton);
        Button checkbutton = (Button) view.findViewById(R.id.checkbutton);
        editorbutton.setText("컨텐츠 편집");
        checkbutton.setText("컨텐츠 시청 확인");


     if (!user.isEditor()) // 새내기
        {
            editorbutton.setVisibility(View.GONE);
            checkbutton.setVisibility(View.GONE);
        }
        editorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                intent.putExtra("Contents", contents);
                intent.putExtra("User", user);
               getActivity().startActivityForResult(intent,1);





            }
        });
     /*
        for (int i = 0; i < 4; i++) {
            final int index = i;
            Daybutton.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ContentsArrayActivity.class);
                    intent.putExtra("Contents", contents);
                    intent.putExtra("Day", index);
                    getActivity().startActivity(intent);
                }
            });
        }
        */

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        if (requestCode == editoractivity) {
            contents = (ArrayList<Content>) data.getSerializableExtra("Contents");
        }

    }*/


        return view;
    }


}
