package com.example.todonotice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CalendarTodoList extends AppCompatActivity {

    private ToDoItem toDoItems;
    private Intent timeIntent;
    private DBHelper2 mDBHelper2;
    TextView timeSaveBtn;
    EditText TodoText;
    EditText HourText;
    EditText MinuteText;
    EditText PlaceText;
    EditText MemoText;
    ImageView backBtn;
    TimePicker timePicker;
    Button todoSaveBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_todolist);

        TodoText = findViewById(R.id.todo_title_et);
        HourText = findViewById(R.id.hour_et);
        MinuteText = findViewById(R.id.minute_et);
        PlaceText = findViewById(R.id.todo_place_et);
        MemoText = findViewById(R.id.todo_memo);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        InsertTodo();
        editTodo();
        todoSaveBtn.setClickable(false);    // 최초 진입 시, 버튼 클릭 안되게 설정
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
                timeSaveBtn = dialog.findViewById(R.id.save_btn);
                timeSaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timeIntent = new Intent();
                        timeIntent.putExtra("hour", hour);
                        timeIntent.putExtra("min", min);

                        if (timeIntent != null) {
                            int hour = timeIntent.getIntExtra("hour", 0);
                            int min = timeIntent.getIntExtra("min", 0);

                            HourText.setText(String.valueOf(hour));
                            MinuteText.setText(String.valueOf(min));
                        }
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
        todoSaveBtn = findViewById(R.id.todo_save_btn);
        mDBHelper2 = new DBHelper2(this);
        toDoItems = new ToDoItem();

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selected");
        Log.d("selectedDateStr   ", "selectedDateStr   " + selectedDate);

        todoSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert DB
                String todoString = TodoText.getText().toString();
                String hourString = HourText.getText().toString();
                String minString = MinuteText.getText().toString();
                String placeString = PlaceText.getText().toString();
                String memoString = MemoText.getText().toString();

                mDBHelper2.InsertToDoList(todoString, hourString, minString, placeString, memoString, selectedDate);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void editTodo() {
        TodoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateSaveButton();
            }
        });

        HourText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateSaveButton();
            }
        });

        MinuteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateSaveButton();
            }
        });

        PlaceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateSaveButton();
            }
        });
    }

    private void updateSaveButton() {
        boolean isTitleNotEmpty = TodoText.getText().length() > 0;
        boolean isHourNotEmpty = HourText.getText().length() > 0;
        boolean isMinNotEmpty = MinuteText.getText().length() > 0;
        boolean isPlaceNotEmpty = PlaceText.getText().length() > 0;

        int gray = ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar);
        int iphone_pink = ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink);
        Drawable shapeDrawableOff = getResources().getDrawable(R.drawable.profile_edit_button_off);
        Drawable shapeDrawableOn = getResources().getDrawable(R.drawable.profile_edit_button_on);
        
        if (isTitleNotEmpty && isHourNotEmpty && isMinNotEmpty && isPlaceNotEmpty) {
            // Case 1. 전부 작성되어 있음
            todoSaveBtn.setClickable(true);
            todoSaveBtn.setBackgroundColor(iphone_pink);
            todoSaveBtn.setBackground(shapeDrawableOn);
        } else {
            // Case 2. 그 외 경우
            todoSaveBtn.setClickable(false);
            todoSaveBtn.setBackgroundColor(gray);
            todoSaveBtn.setBackground(shapeDrawableOff);
        }
    }
}
