package com.example.todonotice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper2 extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "todoList.db";

    public DBHelper2(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성 될 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS TODOLIST_TEXT_SEQ (id INTEGER PRIMARY KEY AUTOINCREMENT, todo TEXT NOT NULL, hour TEXT NOT NULL, min TEXT NOT NULL, am TEXT NOT NULL, pm TEXT NOT NULL, toDoItem TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT (할 일 목록 조회)
    public ArrayList<ToDoItem> getTodoListData() {
        ArrayList<ToDoItem> toDoItem = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TODOLIST_TEXT_SEQ ORDER BY toDoItem DESC", null);
        if (cursor.getCount() != 0) {
            // 조회한 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String todo = cursor.getString(cursor.getColumnIndexOrThrow("todo"));
                String hour = cursor.getString(cursor.getColumnIndexOrThrow("hour"));
                String min = cursor.getString(cursor.getColumnIndexOrThrow("min"));
                String am = cursor.getString(cursor.getColumnIndexOrThrow("am"));
                String pm = cursor.getString(cursor.getColumnIndexOrThrow("pm"));

                ToDoItem data = new ToDoItem();
                data.setId(id);
                data.setTodo(todo);
                data.setHour(hour);
                data.setMin(min);
                data.setAm(am);
                data.setPm(pm);
                toDoItem.add(data);
            }
        }
        cursor.close();
        return toDoItem;
    }

//    // INSERT (할 일 목록 추가)
//    public void InsertToDoList(String _todo, String _hour, String _min, String _am, String _pm) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO TODOLIST_TEXT_SEQ (todo, hour, min, am, pm) VALUES ('" + _todo + "', '" + _hour + "', '" + _min + "', '" + _am + "', '" + _pm + "');");
//    }

    // INSERT (할 일 목록 추가)
    public void InsertToDoList(String _todo, String _hour, String _min, String _day) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("todo", _todo);
        values.put("hour", _hour);
        values.put("min", _min);
//        values.put("am", _am);
//        values.put("pm", _pm);
        values.put("day", _day);
        values.put("toDoItem", ""); // 'toDoItem' 열의 값을 빈 문자열로 설정
        db.insert("TODOLIST_TEXT_SEQ", null, values);
    }


    // UPDATE (할 일 목록 수정)
    public void UpdateToDoList(String _todo, String _hour, String _min, String _am, String _pm, String _writeDate, String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TODOLIST_TEXT_SEQ SET title = '" + _todo + "', content = '" + _hour + "', min = '" + _min + "', am = '" + _am + "', pm = '" + _pm + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");   // 기준값 id
    }

    // DELETE (할 일 목록 수정)
    public void DeleteToDoList(String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TODOLIST_TEXT_SEQ WHERE id = '" + _beforeDate + "'");
    }
}
