package com.example.todonotice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "diaryList.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성 될 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS DIARYLIST_TEXT_SEQ (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT (일기 목록 조회)
    public ArrayList<WriteData> getWriteData() {
        ArrayList<WriteData> writeData = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DIARYLIST_TEXT_SEQ ORDER BY writeDate DESC", null);
        if (cursor.getCount() != 0) {
            // 조회한 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                WriteData data = new WriteData();
                data.setId(id);
                data.setTitle(title);
                data.setContent(content);
                data.setWriteDate(writeDate);
                writeData.add(data);
            }
        }
        cursor.close();
        return writeData;
    }

    // INSERT (일기 목록 추가)
    public void InsertDiary(String _title, String _content, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DIARYLIST_TEXT_SEQ (title, content, writeDate) VALUES ('" + _title + "', '" + _content + "', '" + _writeDate + "');");
    }

    // UPDATE (일기 목록 수정)
//    public void UpdateDiary(String _title, String _content, String _writeDate, String _beforeDate) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("UPDATE DIARYLIST_TEXT_SEQ SET title = '" + _title + "', content = '" + _content + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");   // 기준값 id
//    }

    // UPDATE (일기 목록 수정)
    public void UpdateDiary(int id, String _title, String _content, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", _title);
        values.put("content", _content);
        values.put("writeDate", _writeDate);
        db.update("DIARYLIST_TEXT_SEQ", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // DELETE (일기 목록 수정)
    public void DeleteDiary(String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DIARYLIST_TEXT_SEQ WHERE id = '" + _beforeDate + "'");
    }
}
