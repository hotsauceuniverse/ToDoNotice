package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ToDoListCell extends AppCompatActivity {

    TextView DateTv;
    TextView HourTv;
    TextView MinTv;
    TextView TimeTv;
    TextView TodoTv;

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

//        activity에서 fragment로 데이터 전달 방법
//        FragmentToDoList fragmentToDoList = new FragmentToDoList();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.date_cell, fragmentToDoList).commit();
//
//
//        String[] date = new String[] {todoString, hourString, minString, amString, pmString};
//        Bundle bundle = new Bundle();
//        bundle.putStringArray("date", date);
//        fragmentToDoList.setArguments(bundle);

//        ==========================================================================================

//        Intent receive_intent = getIntent();
//        String todoString = receive_intent.getStringExtra("todo");
//        String hourString = receive_intent.getStringExtra("hour");
//        String minString = receive_intent.getStringExtra("min");
//        String amString = receive_intent.getStringExtra("am");
//        String pmString = receive_intent.getStringExtra("pm");
//
//	      -> 둘중 하나
//
//        // ToDoItem 객체 받기
//        ToDoItem toDoItem = (ToDoItem) getIntent().getSerializableExtra("todoItem");
//
//        // ToDoItem 객체에서 데이터 추출
//        String todoString = toDoItem.getTodo();
//        String hourString = toDoItem.getHour();
//        String minString = toDoItem.getMin();
//        String amString = toDoItem.getAm();
//        String pmString = toDoItem.getPm();

    }

    public void onClick(View view) {
        finish();
    }
}
