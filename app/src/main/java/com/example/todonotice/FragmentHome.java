package com.example.todonotice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHome extends Fragment {

    private Toolbar noticeToolbar, todolistToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout notice_pre = rootView.findViewById(R.id.notice_pre);

        LinearLayout todolist_pre = rootView.findViewById(R.id.todolist_pre);


        notice_pre.setOnClickListener(new View.OnClickListener() {

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

                FragmentNotice fragmentNotice = new FragmentNotice();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentNotice); // R.id.content_frame는 FragmentNotice를 추가할 컨테이너 ID
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        todolist_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ToDoList toolBar, BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showToDoListToolbar();
                mainActivity.showToDoListBottomNavigation();

                todolistToolbar = mainActivity.findViewById(R.id.todolist_toolbar);
                mainActivity.setSupportActionBar(todolistToolbar);
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle("할 일");
                }

                FragmentToDoList fragmentToDoList = new FragmentToDoList();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentToDoList);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }

}
