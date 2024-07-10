package com.seyoung.todonotice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    public static final int REQUEST_CODE_FOR_INTENT = 101;
    ArrayList<LocalDate> dayList;
    private CalenderViewHolder selectedItemHolder;
    private DBHelper2 mDBHelper2;
    private Context mContext;
    private FragmentToDoList fragmentToDoList;
    private LocalDate today = LocalDate.now();  // 오늘 날짜 저장

    public CalendarAdapter(ArrayList<LocalDate> dayList, FragmentToDoList fragmentToDoList) {
        this.dayList = dayList;
        this.fragmentToDoList = fragmentToDoList;
        this.mContext = fragmentToDoList.getContext();
        this.mDBHelper2 = new DBHelper2(mContext);
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
        Log.d("day   ", "day   " + dayList.get(position));

        int calCurPos = holder.getAdapterPosition();
        Log.d("calCurPos   ", "calCurPos   " + calCurPos);
        Log.d("position   ", "position   " + dayList.get(calCurPos));

        // 달력 점 찍기
        // DB에서 해당 날짜에 할 일이 있는지 확인
        LocalDate currentDate = dayList.get(position);
        // 현재 날짜에 해당하는 데이터가 있는지 확인
        boolean hasData = checkDate(currentDate);

        // 데이터가 있는 경우, 점을 표시
        if (hasData) {
            holder.scheduleDot.setVisibility(View.VISIBLE);
        } else {
            holder.scheduleDot.setVisibility(View.INVISIBLE);
        }

        Drawable dateBackground = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.date_background);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 달력 빈곳(null) 클릭 불가
                if (dayList.get(calCurPos) != null) {
                    if (selectedItemHolder != null) {
                        // 이전에 선택한 날짜 배경값 null
                        selectedItemHolder.parentView.setBackground(null);
                        // 이전에 선택한 날짜(포지션 값) 변경
                        notifyItemChanged(selectedItemHolder.getAdapterPosition());
                    }

                    if (calCurPos == holder.getAdapterPosition()) {
                        holder.parentView.setBackground(dateBackground);
                        // 새로 클릭한 itemView selectedItemHolder에 저장
                        selectedItemHolder = holder;

                        // FragmentToDoList 메서드 호출해서, 선택한 날짜에 데이터 유무 확인
                        LocalDate selectDate = dayList.get(calCurPos);
                        boolean data = checkDate(selectDate);

                        if (data) {
                            fragmentToDoList.loadTodoList(selectDate);
                        } else {
                            fragmentToDoList.hideTodoList();
                        }

                        selectedItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 선택한 날짜 넘겨줘야 함
                                Intent intent = new Intent(holder.itemView.getContext(), CalendarTodoList.class);
                                intent.putExtra("selected", dayList.get(calCurPos).toString());
                                Log.d("asdwww   ", "asdwww   " + dayList.get(calCurPos).toString());
                                fragmentToDoList.startActivityForResult(intent, REQUEST_CODE_FOR_INTENT);
                            }
                        });
                    }
                }
            }
        });
    }

    // 해당 날짜에 데이터가 있는지 확인
    private boolean checkDate(LocalDate currentData) {
        // currentDate가 null이면 데이터가 없는 것
        if (currentData == null) {
            return false;
        }

        boolean hasData = false;
        SQLiteDatabase db = mDBHelper2.getReadableDatabase();

        // 해당 날짜에 대한 정보 DB에서 검색
        String select = "SELECT * FROM TODOLIST_TEXT_SEQ WHERE writeDate = ?";
        Cursor cursor = db.rawQuery(select, new String[]{currentData.toString()});

        // 검색 결과가 있으면 데이터를 표시
        if (cursor != null) {
            hasData = cursor.getCount() > 0;
            cursor.close();
        }
        return hasData;
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {

        TextView dayTv;
        View parentView;
        View scheduleDot;

        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTv = itemView.findViewById(R.id.date_tv);
            parentView = itemView.findViewById(R.id.parentView);
            scheduleDot = itemView.findViewById(R.id.dot);
        }

        public void Bind(LocalDate day) {
            // 날짜 변수에 담기
            if (day == null) {
                dayTv.setText("");
            } else {
                dayTv.setText(String.valueOf(day.getDayOfMonth()));
                // 오늘 날짜 색상 변경
                // 오늘 날짜가 아닌 날 + 오늘 날짜 + 오늘 날짜가 아닌 날 클릭 시, 후자 오늘 날짜가 아닌 날에 텍스트 컬러 안되게 처리
                if (day.equals(today)) {
                    dayTv.setTextColor(Color.parseColor("#1473E6"));
                    Log.d("dayTv   ", "dayTv   " + day.equals(CalendarUtil.selectDate));
                } else {
                    dayTv.setTextColor(Color.BLACK);
                }
            }
        }
    }
}