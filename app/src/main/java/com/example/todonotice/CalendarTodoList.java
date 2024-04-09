package com.example.todonotice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarTodoList extends AppCompatActivity {

    private TextView todoSaveBtn;
    private DBHelper2 mDBHelper2;
    EditText HourText, MinuteText, TodoText;
    TextView dayTv;
    ImageView backBtn;
    TimePicker timePicker;
    private Intent timeIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_todolist);

        HourText = findViewById(R.id.hour_et);
        MinuteText = findViewById(R.id.minute_et);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        timePicker();
    }

    public void timePicker() {

        // 최초 키패드 올라오지 않게
        HourText.setFocusable(false);
        HourText.setFocusableInTouchMode(false);

        MinuteText.setFocusable(false);
        MinuteText.setFocusableInTouchMode(false);

        HourText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        MinuteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });
    }

    private void showBottomSheet() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_timepicker);

        timePicker = dialog.findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                int setHourValue = hour;
                Log.d("hour   ", "hour   " + hour);

                int setMinValue = min;
                Log.d("min   ", "min   " + min);

                todoSaveBtn = dialog.findViewById(R.id.save_btn);
                todoSaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timeIntent = new Intent();
                        timeIntent.putExtra("hour", hour);
                        timeIntent.putExtra("min", min);

                        InsertTodo();

                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void InsertTodo() {

        if (timeIntent != null) {
            int hour = timeIntent.getIntExtra("hour", 0);
            int min = timeIntent.getIntExtra("min", 0);

            HourText.setText(String.valueOf(hour));
            MinuteText.setText(String.valueOf(min));
        }




//        mDBHelper2 = new DBHelper2(this);
//
//        String todoString = TodoText.getText().toString();
//        String hourString = HourText.getText().toString();
//        String minString = MinuteText.getText().toString();
//        String dayString = dayTv.getText().toString();
//
//        todoSaveBtn = findViewById(R.id.todo_save_btn);
//        todoSaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDBHelper2.InsertToDoList(todoString, hourString, minString, dayString);
//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//            }
//        });
    }
}
