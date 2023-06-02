package com.example.todonotice;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    Fragment profile_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profile_fragment = new Fragment();

        // view 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_view,profile_fragment).commit();

        // bottom nav 객처 선언
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // bottom nav 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_4 :
                        // replace(Fragment를 띄워줄 frameLayout. 교체할 fragment 객체)
                        getSupportFragmentManager().beginTransaction().replace(R.id.profile_view, profile_fragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });

    }

}

// profile 버튼 탭 시, 화면 안불러와짐
// 수정 필요
