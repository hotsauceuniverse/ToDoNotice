package com.seyoung.todonotice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class ToDoListCell extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saveInstateState) {
        super.onCreate(saveInstateState);
        setContentView(R.layout.todolist_cell);
    }
}
