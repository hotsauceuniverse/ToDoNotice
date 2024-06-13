package com.example.todonotice;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

//  Activity에서 Fragment로 옮길때 변경사항
//  1. onCreate() 메서드를 onCreateView()로 변경하여 프래그먼트 뷰를 생성하고 반환
//  2. findViewById() 호출을 rootView.findById()로 변경 ( rootView는 onCreateView() 메서드에서 반환한 뷰 )

public class FragmentToDoList extends Fragment {

    public static final int REQUEST_CODE_FOR_INTENT = 101;
    private LinearLayoutManager linearLayoutManager;
    TextView monthYearText; // 년월 텍스트뷰
    RecyclerView recyclerView, todoListRecyclerview;
    private ArrayList<ToDoItem> toDoItems;
    private ArrayList<LocalDate> dayList;
    private CalendarAdapter adapter;
    private ToDoAdapter toDoAdapter;
    ImageView preBtn, nextBtn;
    private View rootView;
    private DBHelper2 mDBHelper2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_todolist, container, false);

        // 초기화
        monthYearText = rootView.findViewById(R.id.monthYear_tv);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        preBtn = rootView.findViewById(R.id.pre_btn);
        nextBtn = rootView.findViewById(R.id.next_btn);

        // 현재 날짜 (now에서 API level 26 (current minSdk is 21) 올리기)
        CalendarUtil.selectDate = LocalDate.now();

        // 화면 설정
        setMonthView();

        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectDate = CalendarUtil.selectDate.minusMonths(1);
                setMonthView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectDate = CalendarUtil.selectDate.plusMonths(1);
                setMonthView();
            }
        });

        setInit();
        // 최초 진입 시, 해당 리스트 안보이게 설정
        hideTodoList();
        return rootView;
    }

    // 날짜 타입 설정
    private String yearMonthFormDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }

    // 화면 설정
    private void setMonthView() {
        // 년월 텍스트뷰 셋팅
        monthYearText.setText(yearMonthFormDate(CalendarUtil.selectDate));

        // 해당 월 날짜 가져오기
        dayList = daysInMonthArray(CalendarUtil.selectDate);

        // 어뎁터 데이터 적용
        adapter = new CalendarAdapter(dayList, this);

        // 레이아웃 설정(열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);

        // 레이아웃 적용
        recyclerView.setLayoutManager(manager);

        // 어뎁터 적용
        recyclerView.setAdapter(adapter);
    }

    // 날짜 생성
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> dayList = new ArrayList<>();

        // LocalDate에서 연도와 월 가져오기
        YearMonth yearMonth = YearMonth.from(date);

        // 해당 월 마지막 날짜 가져오기 (예 : 29, 30, 31)
        int lastDay = yearMonth.lengthOfMonth();

        // 해당 월의 첫 번째 날 가져오기 (예 : 4월 1일)
        LocalDate firstDay = CalendarUtil.selectDate.withDayOfMonth(1);

        // 월의 첫 번째 날 요일 가져오기 (예 : 월 = 1, 일 = 7)
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        // 첫 주에 공백을 추가하는 대신, 1일이 일요일로 시작하는 경우
        // 마지막 주에서 남은 날짜를 추가하기
        if (dayOfWeek == 7) {   // 1일이 일요일로 시작하는 경우
            int day = lastDay;    // 초기화 (남아있는 일 수를 알기위해)
            for (int i = 1; i <= lastDay; i++) {
                dayList.add(firstDay.plusDays(i - 1));
                day--;
            }
            // 추가되지 않은 나머지 날짜에 대한 공백을 dayList에 추가하기
            while (day > 0) {
                dayList.add(null);
                day--;
            }
        } else {
            // 그 외의 경우에는 이전과 같이 첫 주에 공백을 추가하기
            // 날짜 생성 (6주 = 42)
            for (int i = 1; i < 42; i++) {
                if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                    dayList.add(null);
                } else {
                    dayList.add(firstDay.plusDays(i - dayOfWeek - 1));
                }
            }
        }
        return dayList;
    }

    private void setInit() {
        // TodoListRecyclerview 초기화
        todoListRecyclerview = rootView.findViewById(R.id.todolist_recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        todoListRecyclerview.setLayoutManager(linearLayoutManager);
        todoListRecyclerview.setAdapter(toDoAdapter);

        toDoItems = new ArrayList<>();

        loadTodoList(CalendarUtil.selectDate);
        recyclerView.smoothScrollToPosition(0);
    }

    public void loadTodoList(LocalDate selectedDate) {
        // DB로부터 해당 날짜의 todo 항목 가져오기
        mDBHelper2 = new DBHelper2(getActivity());
        toDoItems = mDBHelper2.getTodoListData(selectedDate);

        Log.d("loadTodoList   ", "loadTodoList   :    " + toDoItems.toString());

        // 할 일 목록 시간 순서대로 저장
        // https://stackoverflow.com/questions/50562960/sort-recyclerview-by-date
        Collections.sort(toDoItems, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem time1, ToDoItem time2) {
                String time_1 = time1.getHour() + ":" + time1.getMin();
                Log.d("time_1   ", "time_1   " + time_1);

                String time_2 = time2.getHour() + ":" + time2.getMin();
                Log.d("time_2   ", "time_2   " + time_2);

                return time_1.compareTo(time_2);
            }
        });

        // 데이터가 있으면 RecyclerView 보이기
        todoListRecyclerview.setVisibility(View.VISIBLE);
        if (toDoAdapter == null) {
            toDoAdapter = new ToDoAdapter(toDoItems, getContext(), this);
            todoListRecyclerview.setHasFixedSize(true);
            todoListRecyclerview.setAdapter(toDoAdapter);
        } else {
            toDoAdapter.setTodoData(toDoItems);
            toDoAdapter.notifyDataSetChanged();
        }
    }

    public void hideTodoList() {
        // 데이터가 없으면 RecyclerView 가리기
        todoListRecyclerview.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode   ", "resultCode   " + resultCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_INTENT) {
                Log.d("onActivityResult   ", "onActivityResult   ");
                loadTodoList(CalendarUtil.selectDate);
                setMonthView();
            } else {
                Log.d("fail   ", "fail   " + resultCode);
            }
        } else {
            Log.d("fail   ", "fail   ");
        }
    }
}