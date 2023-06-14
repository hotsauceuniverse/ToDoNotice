package com.example.todonotice;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private ToDoListFragment toDoListFragment = new ToDoListFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main_home);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.item_1:
                    transaction.replace(R.id.content_frame, homeFragment).commitAllowingStateLoss();
                    break;

                case R.id.item_2:
                    transaction.replace(R.id.content_frame, toDoListFragment).commitAllowingStateLoss();
                    break;

//                case R.id.item_3:
//                    transaction.replace(R.id.content_frame, toDoListFragment).commitAllowingStateLoss();
//                    break;

                case R.id.item_4:
                    transaction.replace(R.id.content_frame, profileFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

}
