package com.example.todonotice;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FragmentNoticeOutline extends Fragment {

    public static final int REQUEST_CODE_FOR_INTENT = 100;
    public static final int EDIT_OK = 101;
    private RecyclerView recyclerView;
    private ArrayList<WriteData> writeData;
    private DBHelper mDBHelper;
    private AdapterNotice mAdapter;
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

        setInit();

        return rootView;
    }

    private void setInit() {
        // onCreateView에 있는 내용 가져옴
        recyclerView = rootView.findViewById(R.id.notice_list_recycle);
        writeData = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // arrayList 초기화
        writeData = new ArrayList<>();

        loadRecentDB();
        recyclerView.smoothScrollToPosition(0);

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
    }

    private void loadRecentDB() {
        // DBHelper를 통해 데이터베이스에서 최근 데이터를 가져오기
        mDBHelper = new DBHelper(getActivity());
        writeData = mDBHelper.getWriteData(); // 데이터베이스에서 데이터를 가져와 writeData 리스트 업데이트

        if (mAdapter == null) {
            // Adapter가 null인 경우 새로운 Adapter를 생성하고 리사이클러뷰에 설정
            mAdapter = new AdapterNotice(writeData, getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            // Adapter가 이미 존재하는 경우 데이터를 업데이트하고 어댑터를 갱신
            mAdapter.setWriteData(writeData);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(getContext(),"수신 성공",Toast.LENGTH_SHORT).show();
            Log.d("ok", "ok" + resultCode);
            if (requestCode == REQUEST_CODE_FOR_INTENT) {
                loadRecentDB();
            } else if (requestCode == EDIT_OK) {
                loadRecentDB();
            }
         } else {
            Toast.makeText(getContext(),"수신 실패",Toast.LENGTH_SHORT).show();
            Log.d("fail", "fail" + resultCode);
        }
    }
}
