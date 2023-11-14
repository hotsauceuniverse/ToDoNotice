package com.example.todonotice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


//  Activity에서 Fragment로 옮길때 변경사항
//  1. onCreate() 메서드를 onCreateView()로 변경하여 프래그먼트 뷰를 생성하고 반환
//  2. findViewById() 호출을 rootView.findById()로 변경 ( rootView는 onCreateView() 메서드에서 반환한 뷰 )


public class FragmentToDoList extends Fragment {

    TextView monthYearText; // 년월 텍스트뷰
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todolist, container, false);

        // 초기화
        monthYearText = rootView.findViewById(R.id.monthYear_tv);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        // 현재 날짜 (now에서 API level 26 (current minSdk is 21) 올리기)
        CalendarUtil.selectDate = LocalDate.now();

        // 화면 설정
        setMonthView();


        return rootView;
    }

    // 날짜 타입 설정
    private String yearMonthFormDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.KOREAN);
        return date.format(formatter.withLocale(Locale.ENGLISH));
    }

    // 화면 설정
    private void setMonthView() {
        // 년월 텍스트뷰 셋팅
        monthYearText.setText(yearMonthFormDate(CalendarUtil.selectDate));

        // 해당 월 날짜 가져오기
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtil.selectDate);

        // 어뎁터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(dayList);

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
            int remainingDays = lastDay;    // 초기화
            for (int i = 1; i <= lastDay; i++) {
                dayList.add(firstDay.plusDays(i - 1));
                remainingDays--;
            }
            // 추가되지 않은 나머지 날짜에 대한 공백을 dayList에 추가하기
            while (remainingDays > 0) {
                dayList.add(null);
                remainingDays--;
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
}







