package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsWebView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);

//        WebView webView = findViewById(R.id.news_webView);
//        Intent intent = getIntent();
//        if (intent != null) {
//            String urlToLoad = intent.getStringExtra("urlToLoad");
//            if (urlToLoad != null) {
//                // URL을 웹뷰에 로드
//                webView.loadUrl(urlToLoad);
//            }
//        }
    }
}
