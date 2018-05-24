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


public class WebFragment extends Fragment {
    private static final String Html = "html";


    private WebView webView;
    private WebSettings webSettings;
    private  String html = "http://freshman.postech.ac.kr/";
    private int Day = 0;

    public static WebFragment newInstance(String adress)
    {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(Html, adress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            html = getArguments().getString(Html);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


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

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(html);


        return view;
    }
}
