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

public class WriteActivity extends AppCompatActivity {

    ImageView close_btn;
    TextView upload_btn;
    EditText context_area;
    EditText title_area;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_activity);

        title_area = (EditText) findViewById(R.id.title_area);
        context_area = (EditText) findViewById(R.id.context_area);

        // 글쓰기 창 닫기
        close_btn = (ImageView) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDBHelper = new DBHelper(this);
        // 업로드 버튼 활성화
        upload_btn = (TextView) findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Insert DataBase
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    mDBHelper.InsertDiary(title_area.getText().toString(), context_area.getText().toString(), currentTime);
                    Log.d("   aaa", "   aaa" + title_area.getText().toString());
                    Log.d("   bbb", "   bbb" + context_area.getText().toString());

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    Toast.makeText(WriteActivity.this, "추가", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        // EditText에 입력이 변경 될 때 리스너 설정
        editContext();
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
                updateUploadButton();
            }
        });
    }

    void updateUploadButton() {
        boolean isTextNotEmpty = context_area.getText().length() > 0;
        boolean isTitleNotEmpty = title_area.getText().length() > 0;

        Log.e("isTitleNotEmpty", "isTitleNotEmpty" + isTitleNotEmpty);

        if (isTitleNotEmpty && !isTextNotEmpty) {
            // Case 1: isTitleNotEmpty만 작성되어 있음
            upload_btn.setEnabled(true);
            upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        } else if (isTitleNotEmpty && isTextNotEmpty) {
            // Case 2: isTitleNotEmpty과 isTextNotEmpty이 작성되어 있음
            upload_btn.setEnabled(true);
            upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink));
        } else if (isTextNotEmpty && !isTitleNotEmpty) {
            // Case 3: isTextNotEmpty만 작성되어 있음
            upload_btn.setEnabled(true);
            upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        } else {
            // 기타 경우
            upload_btn.setEnabled(false);
            upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
        }
    }
}

