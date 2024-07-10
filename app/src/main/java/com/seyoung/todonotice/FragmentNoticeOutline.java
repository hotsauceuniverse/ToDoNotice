package com.seyoung.todonotice;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FragmentNoticeOutline extends Fragment {

    public static final int REQUEST_CODE_FOR_INTENT = 100;
    private RecyclerView recyclerView;
    private ArrayList<WriteData> writeData;
    private DBHelper mDBHelper;
    private NoticeAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    ImageView write_button;
    private View rootView; // Fragment의 rootView를 저장하는 변수
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice_outline, container, false);

        // swipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadRecentDB();
            }
        });

        // WriteActivity View 이동
        write_button = rootView.findViewById(R.id.write_button);
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteActivity.class);
                // 새 액티비티를 열어줌과 동시에 결과 값을 전달해야함
                // REQUEST_CODE_FOR_INTENT을 통해 내가 보낸 요청이 맞는지 판단
                startActivityForResult(intent, REQUEST_CODE_FOR_INTENT);
            }
        });

        setInit();
        return rootView;
    }

    private void setInit() {
        recyclerView = rootView.findViewById(R.id.notice_list_recycle);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);  // 다른 탭 이동 후 다시 진입 시, 화면 미노출되서 adapter 다시 연결

        // arrayList 초기화
        writeData = new ArrayList<>();

        loadRecentDB();
        recyclerView.smoothScrollToPosition(0);
    }

    private void loadRecentDB() {
        // DBHelper를 통해 데이터베이스에서 최근 데이터를 가져오기
        mDBHelper = new DBHelper(getActivity());
        writeData = mDBHelper.getWriteData(); // 데이터베이스에서 데이터를 가져와 writeData 리스트 업데이트

        if (mAdapter == null) {
            // Adapter가 null인 경우 새로운 Adapter를 생성하고 리사이클러뷰에 설정
            mAdapter = new NoticeAdapter(writeData, getContext(), this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(mAdapter);
        } else {
            // Adapter가 이미 존재하는 경우 데이터를 업데이트하고 어댑터를 갱신
            mAdapter.setWriteData(writeData);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode   ", "resultCode   " + resultCode);

        if (resultCode == RESULT_OK) {
            Log.d("ok   ", "ok   " + resultCode);
            if (requestCode == REQUEST_CODE_FOR_INTENT || requestCode == NoticeAdapter.REQUEST_CODE_NOTICE) {
                loadRecentDB();
            } else {
                Log.d("fail   ", "fail   " + resultCode);
            }
        } else {
            Log.d("fail   ", "fail   ");
        }
    }
}
