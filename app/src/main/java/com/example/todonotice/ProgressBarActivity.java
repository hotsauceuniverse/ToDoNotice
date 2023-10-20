package com.example.todonotice;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressBarActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saveInstateState) {
        super.onCreate(saveInstateState);
        setContentView(R.layout.progress_activity);

        // API 요청 완료 후 NewsActivity 시작
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // API 데이터를 가져오는 동작
                ((NewsActivity)NewsActivity.mContext).getNews();
            }
        }, 2000);
    }
}