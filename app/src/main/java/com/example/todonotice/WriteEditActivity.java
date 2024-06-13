package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        title_area = findViewById(R.id.title_area);
        context_area = findViewById(R.id.context_area);
        edit_btn = findViewById(R.id.edit_btn);

        // 글쓰기 창 닫기
        close_btn = (ImageView) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updateDiary();
        editContext();  // EditText에 입력이 변경 될 때 리스너 설정
        edit_btn.setClickable(false);    // 최초 진입 시, 버튼 클릭 안되게 설정
    }

    public void updateDiary() {
        // 내용 가져오기
        mDBHelper = new DBHelper(this);
        Intent intent = getIntent();
        writeData = new WriteData();
        writeData.setId(intent.getIntExtra("id", -1));
        writeData.setTitle(intent.getStringExtra("title"));
        writeData.setContent(intent.getStringExtra("content"));

        title_area.setText(writeData.getTitle());
        context_area.setText(writeData.getContent());

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update DataBase
                String editTitle = title_area.getText().toString();
                Log.d("111", "111   " + editTitle);
                String editContent = context_area.getText().toString();
                Log.d("222", "222   " + editContent);
                String editCurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Log.d("333", "333   " + editCurrentTime);

                // 수정할 데이터의 id 가져오기
                int id = writeData.getId();
                Log.d("editID   ", "editID   " + id);

                // 수정하기
                mDBHelper.UpdateDiary(id, editTitle, editContent, editCurrentTime);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void editContext() {
        title_area.addTextChangedListener(new TextWatcher() {
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
        String currentTitle = "";
        String currentText = "";

        String isTitleNotEmpty = title_area.getText().toString();
        Log.e("isTitleNotEmpty   ", "isTitleNotEmpty   " + isTitleNotEmpty);

        String isTextNotEmpty = context_area.getText().toString();
        Log.e("isTextNotEmpty   ", "isTextNotEmpty   " + isTextNotEmpty);

        int gray = ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar);
        int iphone_pink = ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink);

        if (!TextUtils.isEmpty(isTitleNotEmpty) &&
            !TextUtils.isEmpty(isTextNotEmpty) &&
            !isTitleNotEmpty.equals(currentTitle) &&
            !isTextNotEmpty.equals(currentText)) {
            edit_btn.setClickable(true);
            edit_btn.setTextColor(iphone_pink);
        } else {
            edit_btn.setClickable(false);
            edit_btn.setTextColor(gray);
        }
    }
}

