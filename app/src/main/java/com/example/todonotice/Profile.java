package com.example.todonotice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Profile extends AppCompatActivity {

    Fragment profile_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profile_fragment = new Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_view,profile_fragment).commit();




    }

}
