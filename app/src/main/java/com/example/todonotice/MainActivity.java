package com.example.todonotice;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentToDoList fragmentToDoList = new FragmentToDoList();
    private FragmentNotice fragmentNotice = new FragmentNotice();
    private FragmentProfile fragmentProfile = new FragmentProfile();

    static BottomNavigationView bottom_nav;

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

}