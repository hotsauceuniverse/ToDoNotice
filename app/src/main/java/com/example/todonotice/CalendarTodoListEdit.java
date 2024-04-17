package com.example.todonotice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class CalendarTodoListEdit extends AppCompatActivity {

    private ToDoItem toDoItem;
    private DBHelper2 mDBHelper2;
    private Intent timeIntent;
    EditText todoTitleEdit;
    EditText hourEdit;
    EditText minuteEdit;
    EditText todoPlaceEdit;
    EditText todoMemoEdit;
    Button todoEditBtn;
    ImageView backBtn;
    TimePicker editTimePicker;
    TextView timeEditBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_todolist_edit);

        todoTitleEdit = findViewById(R.id.todo_title_edit);
        hourEdit = findViewById(R.id.hour_edit);
        minuteEdit = findViewById(R.id.minute_edit);
        todoPlaceEdit = findViewById(R.id.todo_place_edit);
        todoMemoEdit = findViewById(R.id.todo_memo_edit);
        todoEditBtn = findViewById(R.id.todo_edit_btn);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateTodoList();
        EditTimePicker();
    }

    public void updateTodoList() {
        mDBHelper2 = new DBHelper2(this);
        Intent intent = getIntent();
        toDoItem = new ToDoItem();
        toDoItem.setId(intent.getIntExtra("id", -1));
        toDoItem.setTodo(intent.getStringExtra("todoTitle"));
        toDoItem.setHour(intent.getStringExtra("todoHour"));
        toDoItem.setMin(intent.getStringExtra("todoMin"));
        toDoItem.setPlace(intent.getStringExtra("todoPlace"));
        toDoItem.setMemo(intent.getStringExtra("todoMemo"));

        todoTitleEdit.setText(toDoItem.getTodo());
        hourEdit.setText(toDoItem.getHour());
        minuteEdit.setText(toDoItem.getMin());
        todoPlaceEdit.setText(toDoItem.getPlace());
        todoMemoEdit.setText(toDoItem.getMemo());

        todoEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Update DataBase
                    String editTodo = todoTitleEdit.getText().toString();
                    String editHour = hourEdit.getText().toString();
                    String editMin = minuteEdit.getText().toString();
                    String editPlace = todoPlaceEdit.getText().toString();
                    String editMemo = todoMemoEdit.getText().toString();

                    int id = toDoItem.getId();

                    if (id != -1) {
                        mDBHelper2.UpdateToDoList(id, editTodo, editHour, editMin, editPlace, editMemo);
                        
                        // UI 업데이트 기능 처리 필요
                        // 다시 수정 필요
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    public void EditTimePicker() {
        hourEdit.setFocusable(false);
        hourEdit.setFocusableInTouchMode(false);

        minuteEdit.setFocusable(false);
        minuteEdit.setFocusableInTouchMode(false);
        
        hourEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });
        
        minuteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });
    }

    private void showBottomSheet() {
        final Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.bottom_timepicker);

        editTimePicker = editDialog.findViewById(R.id.time_picker);
        editTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int editHour, int editMin) {
                timeEditBtn = editDialog.findViewById(R.id.save_btn);
                timeEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timeIntent = new Intent();
                        timeIntent.putExtra("editHour", editHour);
                        timeIntent.putExtra("editMin", editMin);

                        if (timeIntent != null) {
                            int hour = timeIntent.getIntExtra("editHour", 0);
                            int min = timeIntent.getIntExtra("editMin", 0);

                            hourEdit.setText(String.valueOf(hour));
                            minuteEdit.setText(String.valueOf(min));

                            editDialog.dismiss();
                        }
                    }
                });
            }
        });
        editDialog.show();
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    
    // 버튼 색상 변경 추가하기
}

