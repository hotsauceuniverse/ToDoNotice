package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ToDoListCell extends AppCompatActivity {

    TextView DateTv, HourTv, MinTv, TimeTv, TodoTv;

    @Override
    public void onCreate(Bundle saveInstateState) {
        super.onCreate(saveInstateState);
        setContentView(R.layout.todolist_cell);

        DateTv = findViewById(R.id.date_tv);
        HourTv = findViewById(R.id.hour_tv);
        MinTv = findViewById(R.id.min_tv);
        TimeTv = findViewById(R.id.time_tv);
        TodoTv = findViewById(R.id.todo_tv);

        Intent receive_intent = getIntent();
        String todoString = receive_intent.getStringExtra("todo");
        String hourString = receive_intent.getStringExtra("hour");
        String minString = receive_intent.getStringExtra("min");
        String amString = receive_intent.getStringExtra("am");
        String pmString = receive_intent.getStringExtra("pm");

        TodoTv.setText(todoString);
        HourTv.setText(hourString);
        MinTv.setText(minString);

        // amString 또는 pmString 중 하나를 선택하여 TimeTv에 설정
        // 데이터 타입을 비교할때는 ==
        // String 처럼 Class의 값을 비교할때는 equals
        if (!amString.equals("")) {
            TimeTv.setText("am");
        } else {
            TimeTv.setText("pm");
        }
    }

    public void onClick(View view) {
        finish();
    }
}
