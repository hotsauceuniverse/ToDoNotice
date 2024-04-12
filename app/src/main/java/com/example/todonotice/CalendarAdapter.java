package com.example.todonotice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder> {

    ArrayList<LocalDate> dayList;
//    public static final int REQUEST_TODOLIST_FOR_INTENT = 101;
    private ArrayList<ToDoItem> toDoItems;

    public CalendarAdapter(ArrayList<LocalDate> dayList) {
        this.dayList = dayList;
    }

    public void setTodoData(ArrayList<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
        notifyDataSetChanged();
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
        holder.Bind(dayList.get(position));
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

        public void Bind(LocalDate day) {
            Drawable dateBackground = ContextCompat.getDrawable(itemView.getContext(), R.drawable.date_background);

            // 날짜 변수에 담기
            if (day == null) {
                dayTv.setText("");
            } else {
                dayTv.setText(String.valueOf(day.getDayOfMonth()));

                // 오늘 날짜 색상 변경
                if (day.equals(CalendarUtil.selectDate)) {
                    dayTv.setTextColor(Color.parseColor("#ffffff"));
                    parentView.setBackground(dateBackground);

                }
                // 날짜 클릭 이벤트
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("date   ", "date   " + day);
                        // 달력 빈 곳 클릭 했을 때 앱 꺼짐 방지
                        // NullPointerException 처리
                        if (day!= null) {
                            Intent intent = new Intent(itemView.getContext(), CalendarTodoList.class);
                            ((Activity) itemView.getContext()).startActivity(intent);
//                            ((Activity) itemView.getContext()).startActivityForResult(intent, REQUEST_TODOLIST_FOR_INTENT);
                        }
                    }
                });
            }
        }
    }
}