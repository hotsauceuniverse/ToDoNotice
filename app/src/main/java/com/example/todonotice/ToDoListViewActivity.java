package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ToDoListViewActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FOR_INTENT = 101;
    private DBHelper2 mDBHelper2;
    private ToDoItem toDoItem;
    ImageView moreBtn;
    TextView todoTitleView;
    TextView hourView;
    TextView minuteView;
    TextView todoPlaceView;
    TextView todoMemView;

    @Override
    public void onCreate(Bundle saveInstateState) {
        super.onCreate(saveInstateState);
        setContentView(R.layout.calendar_todolist_view);
        todoTitleView = findViewById(R.id.todo_title_view);
        hourView = findViewById(R.id.hour_view);
        minuteView = findViewById(R.id.minute_view);
        todoPlaceView = findViewById(R.id.todo_place_view);
        todoMemView = findViewById(R.id.todo_memo_view);

        moreBtn = findViewById(R.id.more_button);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu(view);
            }
        });
        todolistView();
    }

    private void popupMenu(View anchorView) {
        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), anchorView);
        getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit_text:
                        Intent intent = new Intent(getApplicationContext(), CalendarTodoListEdit.class);
                        intent.putExtra("id", toDoItem.getId());
                        Log.d("id   ", "id   " + toDoItem.getId());

                        intent.putExtra("todoTitle", toDoItem.getTodo());
                        Log.d("title   ", "title   " + toDoItem.getTodo());

                        intent.putExtra("todoHour", toDoItem.getHour());
                        Log.d("hour   ", "hour   " + toDoItem.getHour());

                        intent.putExtra("todoMin", toDoItem.getMin());
                        Log.d("min   ", "min   " + toDoItem.getMin());

                        intent.putExtra("todoPlace", toDoItem.getPlace());
                        Log.d("place   ", "place   " + toDoItem.getPlace());

                        intent.putExtra("todoMemo", toDoItem.getMemo());
                        Log.d("memo   ", "memo   " + toDoItem.getMemo());

                        // 수정된 데이터 넘겨주기
                        startActivityForResult(intent, REQUEST_CODE_FOR_INTENT);
                        break;

                    case R.id.delete_text:
                        String beforeTime = String.valueOf(toDoItem.getId());
                        mDBHelper2.DeleteToDoList(beforeTime);
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2번 받아야 데이터가 수정이 됨
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FOR_INTENT) {
            setResult(RESULT_OK);
            finish();
        }
    }

    public void todolistView() {
        mDBHelper2 = new DBHelper2(getApplicationContext());
        Intent intent = getIntent();
        toDoItem = new ToDoItem();
        toDoItem.setId(intent.getIntExtra("id", -1));
        toDoItem.setTodo(intent.getStringExtra("todoTitle"));
        toDoItem.setHour(intent.getStringExtra("todoHour"));
        toDoItem.setMin(intent.getStringExtra("todoMin"));
        toDoItem.setPlace(intent.getStringExtra("todoPlace"));
        toDoItem.setMemo(intent.getStringExtra("todoMemo"));

        todoTitleView.setText(toDoItem.getTodo());
        hourView.setText(toDoItem.getHour());
        minuteView.setText(toDoItem.getMin());
        todoPlaceView.setText(toDoItem.getPlace());
        todoMemView.setText(toDoItem.getMemo());
    }
}
