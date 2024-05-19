package com.example.todonotice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class FragmentHome extends Fragment {

    Toolbar noticeToolbar;
    LinearLayout news_pre_1;
    LinearLayout news_pre_2;
    LinearLayout news_pre_3;
    LinearLayout notice_pre_1;
    LinearLayout notice_pre_2;
    TextView subTv1;
    TextView subTv2;
    TextView contentTv1;
    TextView contentTv2;
    TextView todolist_pre_1;
    TextView todolist_pre_2;
    private DBHelper mDBHelper;
    private DBHelper2 mDBHelper2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        todolist_pre_1 = rootView.findViewById(R.id.action_1_btn);
        todolist_pre_2 = rootView.findViewById(R.id.action_2_btn);
        subTv1 = rootView.findViewById(R.id.sub_tv_1);
        subTv2 = rootView.findViewById(R.id.sub_tv_2);
        contentTv1 = rootView.findViewById(R.id.content_tv_1);
        contentTv2 = rootView.findViewById(R.id.content_tv_2);
        notice_pre_1 = rootView.findViewById(R.id.sub_con_1);
        notice_pre_2 = rootView.findViewById(R.id.sub_con_2);
        news_pre_1 = rootView.findViewById(R.id.news_lay_st);
        news_pre_2 = rootView.findViewById(R.id.news_lay_nd);
        news_pre_3 = rootView.findViewById(R.id.news_lay_rd);

        DBSearch();
        todolistClickEvent();
        noticeClickEvent();
        newsClickEvent();

        return rootView;
    }

    // 데이터 조회 수정필요
    public void DBSearch() {
        mDBHelper = new DBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, content FROM DIARYLIST_TEXT_SEQ ORDER BY id DESC LIMIT 2", null);
        Log.d("cursor   ", "cursor   " + cursor);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getCount();
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");

            if (titleIndex >= 0 && contentIndex >= 0) {
                for (int i = 0; i < count; i++) {
                    String title = cursor.getString(titleIndex);
                    String content = cursor.getString(contentIndex);

                    Log.d("DB   ", "title :   " + title + "   content :   " + content);

                    cursor.moveToNext();
                }
            } else {
                Log.d("fail   ", "fail   ");
            }
        }
    }

    public void todolistClickEvent() {
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
    }

    public void noticeClickEvent() {
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
    }

    public void newsClickEvent() {
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
    }
}
