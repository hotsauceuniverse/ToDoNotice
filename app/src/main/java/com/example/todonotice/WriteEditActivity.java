package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteEditActivity extends AppCompatActivity {

    ImageView close_btn;
    TextView edit_btn;
    EditText context_area;
    EditText title_area;
    private DBHelper mDBHelper;
    WriteData writeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_edit_activity);

        title_area = (EditText) findViewById(R.id.title_area);
        context_area = (EditText) findViewById(R.id.context_area);
        edit_btn = (TextView) findViewById(R.id.edit_btn);

        // 글쓰기 창 닫기
        close_btn = (ImageView) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // EditText에 입력이 변경 될 때 리스너 설정
        editContext();

        mDBHelper = new DBHelper(this);
        updateDiary();
    }

    public void editContext() {
        context_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateEditButton();
            }
        });
    }

    void updateEditButton() {
        boolean isTextNotEmpty = context_area.getText().length() > 0;
        boolean isTitleNotEmpty = title_area.getText().length() > 0;

        Log.e("isTitleNotEmpty", "isTitleNotEmpty" + isTitleNotEmpty);

        if (isTitleNotEmpty && !isTextNotEmpty) {
            // Case 1: isTitleNotEmpty만 작성되어 있음
            edit_btn.setEnabled(true);
            edit_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        } else if (isTitleNotEmpty && isTextNotEmpty) {
            // Case 2: isTitleNotEmpty과 isTextNotEmpty이 작성되어 있음
            edit_btn.setEnabled(true);
            edit_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink));
        } else if (isTextNotEmpty && !isTitleNotEmpty) {
            // Case 3: isTextNotEmpty만 작성되어 있음
            edit_btn.setEnabled(true);
            edit_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        } else {
            // 기타 경우
            edit_btn.setEnabled(false);
            edit_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        }
    }

    public void updateDiary() {
        // 내용 가져오기
        Intent intent = getIntent();
        writeData = new WriteData();
        writeData.setTitle(intent.getStringExtra("title"));
        writeData.setContent(intent.getStringExtra("content"));
        title_area.setText(writeData.getTitle());
        context_area.setText(writeData.getContent());

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Update DataBase
                    String editTitle = title_area.getText().toString();
                    Log.d("111", "111   " + editTitle);
                    String editContent = context_area.getText().toString();
                    Log.d("222", "222   " + editContent);
                    String editCurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    Log.d("333", "333   " + editCurrentTime);
                    
                    // 수정하기가 업데이트 안됨
                    // 업데이트 부분 수정필요
                    if (writeData != null) {
                        String editBeforeTime = writeData.getWriteDate();
                        Log.d("444", "444   " + editBeforeTime);

                        mDBHelper.UpdateDiary(editTitle, editContent, editCurrentTime, editBeforeTime);

                    } else {
                        Log.e("555", "555");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}

