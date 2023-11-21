package com.example.todonotice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder>{

    ArrayList<LocalDate> dayList;
    Dialog dialog;

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

//                    int iYear = day.getYear(); // 년
//                    int iMonth = day.getMonthValue(); // 월
//                    int iDay = day.getDayOfMonth(); // 일
//
//                    String yearMonthDay = iYear + "년" + iMonth + "월" + iDay + "일";
//
//                    Toast.makeText(holder.itemView.getContext(), yearMonthDay, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void showDialog() {
//        dialog.show();
//
//        final Calendar calendarInstance = Calendar.getInstance();
//        int hour = calendarInstance.get(Calendar.HOUR_OF_DAY);
//        int min = calendarInstance.get(Calendar.MINUTE);
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                if (timePicker.isShown()) {
//                    calendarInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    calendarInstance.set(Calendar.MINUTE, minute);
//                }
//            }
//        };
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(dialog.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, onTimeSetListener, hour, min, true);
//        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        timePickerDialog.show();

        Button noBtn = dialog.findViewById(R.id.no_btn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button yesBtn = dialog.findViewById(R.id.yes_btn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
