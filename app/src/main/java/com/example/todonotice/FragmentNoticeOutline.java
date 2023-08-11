package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FragmentNoticeOutline extends Fragment {
    private ArrayList<NoticeData> arrayList;
    private AdapterNotice adapterNotice;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    FrameLayout write_button;
    private View rootView; // Fragment의 rootView를 저장하는 변수
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice_outline, container, false);

        recyclerView = rootView.findViewById(R.id.notice_list_recycle);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // arrayList 초기화
        arrayList = new ArrayList<>();
        // 샘플데이터 추가(삭제예정)
        arrayList.add(new NoticeData(R.drawable.default_profile, R.drawable.ic_like_on, R.drawable.ic_comment_gray, R.drawable.ic_more_gray, "User123", "2023-08-10"));
        arrayList.add(new NoticeData(R.drawable.default_profile, R.drawable.ic_like_on, R.drawable.ic_comment_gray, R.drawable.ic_more_gray, "User456", "2023-08-11"));
        arrayList.add(new NoticeData(R.drawable.default_profile, R.drawable.ic_like_on, R.drawable.ic_comment_gray, R.drawable.ic_more_gray, "User789", "2023-08-12"));
        arrayList.add(new NoticeData(R.drawable.default_profile, R.drawable.ic_like_on, R.drawable.ic_comment_gray, R.drawable.ic_more_gray, "User012", "2023-08-13"));

        adapterNotice = new AdapterNotice(arrayList);
        recyclerView.setAdapter(adapterNotice);

        write_button = rootView.findViewById(R.id.write_button);

        // WriteActivity View 이동
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });

        // swipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }
}
