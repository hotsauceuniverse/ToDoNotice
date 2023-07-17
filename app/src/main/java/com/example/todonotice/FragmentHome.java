package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout notice_pre = rootView.findViewById(R.id.notice_pre);

        notice_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                FragmentNotice fragmentNotice = new FragmentNotice();
//                transaction.replace(R.id.home_layout, fragmentNotice);
//                transaction.addToBackStack(null);
//
//                transaction.commit();

                // notice_pre 탭 시, notice 뷰 업데이트
                Intent intent = new Intent(getActivity(), NoticeActivity.class);

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View v  = inflater.inflate(R.layout.main_activity, null);
                BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottom_nav);

                startActivity(intent);

            }
        });

        return rootView;
    }

}




//  // notice_pre 탭 시, notice 뷰 업데이트
//  // notice_toolbar 업데이트
//  toolbar = findViewById(R.id.notice_toolbar);
//
//  // 하단 navigationBar 업데이트
//  // main_activity에서 navigationBar 가져오기
//  LayoutInflater inflater = LayoutInflater.from(this);
//  View view = inflater.inflate(R.layout.main_activity, null);
//  BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav);


//    // 툴바 설정
//    Toolbar toolbar = findViewById(R.id.notice_toolbar);
//    setSupportActionBar(toolbar);
//
//    // 하단 네비게이션 바 설정
//    BottomNavigationView bottomNavigationView = ((MainActivity) getParent()).findViewById(R.id.bottom_nav);
//    bottomNavigationView.setSelectedItemId(R.id.navigation_notice);
//
//    // FragmentNotice 표시
//    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    FragmentNotice fragmentNotice = new FragmentNotice();
//    transaction.replace(R.id.notice_layout, fragmentNotice);
//    transaction.commit();