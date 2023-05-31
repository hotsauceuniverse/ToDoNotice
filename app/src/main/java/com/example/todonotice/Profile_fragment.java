package com.example.todonotice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Profile_fragment extends Fragment implements View.OnClickListener {

    ImageView profile_image;

    @Override
    public void onClick(View view) {

    }

    public Profile_fragment() {

    }

    public Profile_fragment newInstance() {
        Profile_fragment fragment = new Profile_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater(R.layout.profile_fragment, container, false);

        profile_image = view.findViewById(R.id.profile_image);
    }


}
