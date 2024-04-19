package com.example.todonotice;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class NewsWebView extends AppCompatActivity {

    WebView newsWebView;
    LottieAnimationView loadingAnimation;
    View webViewDim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);

        newsWebView = findViewById(R.id.news_webView);
        loadingAnimation = findViewById(R.id.loading_animation);
        webViewDim = findViewById(R.id.view_dim);

        String url = getIntent().getStringExtra("URL");

        Log.e("asdwww   ", "url   " + url);

        // https://stackoverflow.com/questions/7305089/how-to-load-external-webpage-in-webview
        // enable javascript
        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.getSettings().setDomStorageEnabled(true);   // "조선일보" 웹뷰 안뜨는 상황에서 추가한 코드, 로컬저장소 허용 여부

        newsWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingAnimation.setVisibility(View.VISIBLE);
                webViewDim.setVisibility(View.VISIBLE);
                newsWebView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingAnimation.setVisibility(View.INVISIBLE);
                webViewDim.setVisibility(View.INVISIBLE);
                newsWebView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d("111   ", "111   " + description);
                Log.d("222   ", "222   " + errorCode);
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError error) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("Asdwww   ","shouldOverrideUrlLoading   " + view.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        newsWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.e("asdwww   ","onConsoleMessage   " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        newsWebView.loadUrl(url);
    }
}
