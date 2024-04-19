package com.example.todonotice;

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
    private ArrayList<ToDoItem> toDoItems;
    private CalenderViewHolder selectedItemHolder = null;
    private LocalDate day;

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
        Drawable dateBackground = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.date_background);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int calCurPos = holder.getAdapterPosition();
                Log.d("calCurPos", "calCurPos" + calCurPos);

                if (selectedItemHolder != null) {
                    // 이전에 선택한 값 null
                    selectedItemHolder.parentView.setBackground(null);
                }

                if (calCurPos == holder.getAdapterPosition()) {
                    holder.parentView.setBackground(dateBackground);
                    // 새로 클릭한 itemView selectedItemHolder에 저장
                    selectedItemHolder = holder;
                    selectedItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(holder.itemView.getContext(), CalendarTodoList.class);
                            (holder.itemView.getContext()).startActivity(intent);
                        }
                    });
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

        public void Bind(LocalDate day) {
            // 날짜 변수에 담기
            if (day == null) {
                dayTv.setText("");
            } else {
                dayTv.setText(String.valueOf(day.getDayOfMonth()));
                // 오늘 날짜 색상 변경
                if (day.equals(CalendarUtil.selectDate)) {
                    dayTv.setTextColor(Color.parseColor("#1473E6"));
                }
            }
        }
    }
}