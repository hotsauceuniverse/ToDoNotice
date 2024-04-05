package com.example.todonotice;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder>{

    ArrayList<LocalDate> dayList;
    Dialog dialog;
    Button CancelBtn, SaveBtn;
    EditText HourText, MinuteText, TodoText;
    RadioButton AmBtn, PmBtn;

    public CalendarAdapter(ArrayList<LocalDate> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        dialog = new Dialog(parent.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_todolist);

        return new CalenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {

        Drawable dateBackground = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.date_background);

        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if (day == null) {
            holder.dayTv.setText("");
        } else {
            holder.dayTv.setText(String.valueOf(day.getDayOfMonth()));

            // 오늘 날짜 색상 변경
            if (day.equals(CalendarUtil.selectDate)) {
                holder.dayTv.setTextColor(Color.parseColor("#ffffff"));
                holder.parentView.setBackground(dateBackground);
            }
        }

        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 달력 빈 곳 클릭 했을 때 앱 꺼짐 방지
                // NullPointerException 처리
                if (day!= null) {
                    // 모달창 띄우기
                    showDialog();
                }
            }
        });
    }

    private void showDialog() {
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CancelBtn = dialog.findViewById(R.id.cancel_btn);
        SaveBtn = dialog.findViewById(R.id.save_btn);
        HourText = dialog.findViewById(R.id.hour_et);
        MinuteText = dialog.findViewById(R.id.minute_et);
        AmBtn = dialog.findViewById(R.id.am_rb);
        PmBtn = dialog.findViewById(R.id.pm_rb);
        TodoText = dialog.findViewById(R.id.todo_et);

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String todoString = TodoText.getText().toString();
                String hourString = HourText.getText().toString();
                String minString = MinuteText.getText().toString();
                String amString = "";
                String pmString = PmBtn.getText().toString();

//              pmString의 값을 빈 문자열("")로 설정하고 있음
//              즉, AM이 선택된 경우 PM은 빈 문자열로 초기화됨
                if (AmBtn.isChecked()) {
                    amString = AmBtn.getText().toString();
                    pmString = ""; // AM이 선택된 경우 PM은 빈 문자열로 설정
                } else {
                    amString = ""; // PM이 선택된 경우 AM은 빈 문자열로 설정
                }
                ToDoItem toDoItem = new ToDoItem();
                Log.d("   aaa", "   aaa" + toDoItem);

                dialog.dismiss();
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
