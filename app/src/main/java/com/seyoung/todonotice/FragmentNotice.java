package com.seyoung.todonotice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentNotice extends Fragment {

    TextView diaryTitle;
    TextView diaryContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

        diaryTitle = rootView.findViewById(R.id.title_tv);
        diaryContent = rootView.findViewById(R.id.content_tv);

        Bundle bundle = getArguments();

        String titleMsg = bundle.getString("title");
        String contentMsg = bundle.getString("content");

        diaryTitle.setText(titleMsg);
        diaryContent.setText(contentMsg);

        return rootView;
    }
}

