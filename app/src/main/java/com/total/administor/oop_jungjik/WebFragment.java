package com.total.administor.oop_jungjik;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


public class WebFragment extends Fragment {
    private static final String Html = "html";



    private WebView webView;
    private WebSettings webSettings;
    private  String html = "http://freshman.postech.ac.kr/";
    private User user;
    private Content content;
    private int Day = 0;
    private int index;
    TimeChecker mTimeChecker = new TimeChecker();
    double interval;




    public static WebFragment newInstance(User user, Content content, int index)
    {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putSerializable("User", user);
        args.putSerializable("Content", content);
        args.putInt("Index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
            content = (Content) getArguments().getSerializable("Content");
            index = (int) getArguments().getInt("Index");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTimeChecker.setStartTime();

        View view = inflater.inflate(R.layout.fragment_web,container,false);
        webView = (WebView) view.findViewById(R.id.simpleWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

       // DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
       // webView.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels));
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webView.loadUrl("http://psiki.iptime.org:57210/Contents"+Integer.toString(index)+".html");
       // webView.clearView();
       // webView.measure(10,10);

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();

            mTimeChecker.setEndTime();
            interval = mTimeChecker.calcTerm();
            // Content A = (Content) getArguments().getSerializable("Content");
            if(content.getMinute()*60+content.getSeond()<=interval)
            {
                Toast.makeText(getContext(), String.valueOf(interval), Toast.LENGTH_SHORT).show();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };
                WatchRequest watchrequest = new WatchRequest(user.getID(), content.getName() ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(watchrequest);


            }
}




}

