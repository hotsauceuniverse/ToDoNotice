package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHome extends Fragment {

    private Toolbar noticeToolbar, todolistToolbar;
    private LinearLayout news_pre_1, news_pre_2, news_pre_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout notice_pre_1 = rootView.findViewById(R.id.sub_con_1);
        LinearLayout notice_pre_2 = rootView.findViewById(R.id.sub_con_2);

        TextView todolist_pre_1 = rootView.findViewById(R.id.action_1_btn);
        TextView todolist_pre_2 = rootView.findViewById(R.id.action_2_btn);

        news_pre_1 = rootView.findViewById(R.id.news_lay_st);
        news_pre_2 = rootView.findViewById(R.id.news_lay_nd);
        news_pre_3 = rootView.findViewById(R.id.news_lay_rd);

        notice_pre_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Notice toolBar, BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showNoticeToolbar();
                mainActivity.showNoticeBottomNavigation();

                noticeToolbar = mainActivity.findViewById(R.id.notice_toolbar);
                mainActivity.setSupportActionBar(noticeToolbar);
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle("게시판");
                }

                FragmentNoticeOutline fragmentNotice = new FragmentNoticeOutline();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentNotice);    // R.id.content_frame는 FragmentNotice를 추가할 컨테이너 ID
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        notice_pre_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showNoticeToolbar();
                mainActivity.showNoticeBottomNavigation();

                noticeToolbar = mainActivity.findViewById(R.id.notice_toolbar);
                mainActivity.setSupportActionBar(noticeToolbar);
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle("게시판");
                }

                FragmentNoticeOutline fragmentNotice = new FragmentNoticeOutline();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentNotice);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        todolist_pre_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();;
                mainActivity.showToDoListBottomNavigation();

                FragmentToDoList fragmentToDoList = new FragmentToDoList();
                // Fragment todoList로 교체
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentToDoList);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        todolist_pre_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ToDoList toolBar, BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showToDoListBottomNavigation();

                FragmentToDoList fragmentToDoList = new FragmentToDoList();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentToDoList);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // 뉴스 레이아웃으로 이동 (fragment -> activity)
        news_pre_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        news_pre_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        news_pre_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
