package com.example.todonotice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;

public class DBHelper2 extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "todoList.db";

    public DBHelper2(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성 될 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS TODOLIST_TEXT_SEQ (id INTEGER PRIMARY KEY AUTOINCREMENT, todo TEXT NOT NULL, hour TEXT NOT NULL, min TEXT NOT NULL, place TEXT, memo TEXT, writeDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT (할 일 목록 조회)
    public ArrayList<ToDoItem> getTodoListData() {
        ArrayList<ToDoItem> toDoItem = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TODOLIST_TEXT_SEQ ORDER BY id DESC", null);
        if (cursor.getCount() != 0) {
            // 조회한 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String todo = cursor.getString(cursor.getColumnIndexOrThrow("todo"));
                String hour = cursor.getString(cursor.getColumnIndexOrThrow("hour"));
                String min = cursor.getString(cursor.getColumnIndexOrThrow("min"));
                String place = cursor.getString(cursor.getColumnIndexOrThrow("place"));
                String memo = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                ToDoItem data = new ToDoItem();
                data.setId(id);
                data.setTodo(todo);
                data.setHour(hour);
                data.setMin(min);
                data.setPlace(place);
                data.setMemo(memo);
                data.setWriteDate(writeDate);
                toDoItem.add(data);
            }
        }
        cursor.close();
        return toDoItem;
    }

    public void InsertToDoList(String _todo, String _hour, String _min, String _place, String _memo, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();

        // ContentValues를 사용하여 데이터 삽입
        ContentValues values = new ContentValues();
        values.put("todo", _todo);
        values.put("hour", _hour);
        values.put("min", _min);
        values.put("place", _place);
        values.put("memo", _memo);
        values.put("writeDate", _writeDate); // 사용자가 지정한 날짜 추가

        // 데이터베이스에 삽입
        db.insert("TODOLIST_TEXT_SEQ", null, values);
    }

    // UPDATE (할 일 목록 수정)
    public void UpdateToDoList(int id, String _todo, String _hour, String _min, String _place, String _memo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("todo", _todo);
        values.put("hour", _hour);
        values.put("min", _min);
        values.put("place", _place);
        values.put("memo", _memo);
        db.update("TODOLIST_TEXT_SEQ", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // DELETE (할 일 목록 수정)
    public void DeleteToDoList(String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TODOLIST_TEXT_SEQ WHERE id = '" + _beforeDate + "'");
    }
}
