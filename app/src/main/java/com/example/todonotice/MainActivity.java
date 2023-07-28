package com.example.todonotice;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentToDoList fragmentToDoList = new FragmentToDoList();
    private NoticeActivity fragmentNotice = new NoticeActivity();
    private FragmentProfile fragmentProfile = new FragmentProfile();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main_activity);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.item_1:
                    transaction.replace(R.id.content_frame, fragmentHome).commitAllowingStateLoss();
                    break;

                case R.id.item_2:
                    transaction.replace(R.id.content_frame, fragmentToDoList).commitAllowingStateLoss();
                    break;

                case R.id.item_3:
                    transaction.replace(R.id.content_frame, fragmentNotice).commitAllowingStateLoss();
                    break;

                case R.id.item_4:
                    transaction.replace(R.id.content_frame, fragmentProfile).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void showNoticeToolbar() {
        Toolbar toolbar = findViewById(R.id.notice_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("게시판");
        }
    }

    public void showNoticeBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.item_3);
    }

    public void showToDoListToolbar() {
        Toolbar toolbar = findViewById(R.id.todolist_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("할 일");
        }
    }

    public void showToDoListBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.item_2);
    }
}
