package com.example.todonotice;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder>{

    ArrayList<LocalDate> dayList;

    public CalendarAdapter(ArrayList<LocalDate> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {

        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if (day == null) {
            holder.dayTv.setText("");
        } else {
            holder.dayTv.setText(String.valueOf(day.getDayOfMonth()));

            // 오늘 날짜 색상 변경
            if (day.equals(LocalDate.now())) {
                holder.dayTv.setTextColor(Color.parseColor("#FF3B7D"));
                holder.dayTv.setTypeface(null, Typeface.BOLD);
            } else {
                holder.dayTv.setTextColor(Color.BLACK);
                holder.dayTv.setTypeface(null, Typeface.NORMAL);
            }
        }

        // 텍스트 색상 지정 (토요일, 일요일)
        if ((position + 1) % 7 == 0) {  // 토요일
            holder.dayTv.setTextColor(Color.parseColor("#0099CC"));
        } else if (position == 0 || position % 7 == 0) {
            holder.dayTv.setTextColor(Color.parseColor("#CC0000"));
        }

        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 달력 빈 곳 클릭 했을 때 앱 꺼짐 방지
                // NullPointerException 처리
                if (day!= null) {
                    int iYear = day.getYear(); // 년
                    int iMonth = day.getMonthValue(); // 월
                    int iDay = day.getDayOfMonth(); // 일

                    String yearMonthDay = iYear + "년" + iMonth + "월" + iDay + "일";

                    Toast.makeText(holder.itemView.getContext(), yearMonthDay, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {
        TextView dayTv;

        View parentView;

        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTv = itemView.findViewById(R.id.date_tv);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }
}
