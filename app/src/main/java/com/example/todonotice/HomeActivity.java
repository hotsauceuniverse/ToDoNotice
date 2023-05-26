package com.example.todonotice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private BackPressHandler backPressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        backPressHandler = new BackPressHandler(this);  // 뒤로가기 버튼 이벤트
    }

    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed();
    }

}
