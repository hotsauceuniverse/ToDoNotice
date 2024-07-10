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
        upload_btn = findViewById(R.id.upload_btn);
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

//                    Toast.makeText(WriteActivity.this, "추가", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        editContext();  // EditText에 입력이 변경 될 때 리스너 설정
        upload_btn.setClickable(false);    // 최초 진입 시, 버튼 클릭 안되게 설정
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
                updateUploadButton();
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
                updateUploadButton();
            }
        });
    }

    void updateUploadButton() {
        boolean isTitleNotEmpty = title_area.getText().length() > 0;
        boolean isTextNotEmpty = context_area.getText().length() > 0;

        Log.e("isTitleNotEmpty", "isTitleNotEmpty" + isTitleNotEmpty);

        int gray = ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar);
        int iphone_pink = ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink);

        if (isTitleNotEmpty && isTextNotEmpty) {
            upload_btn.setClickable(true);
            upload_btn.setTextColor(iphone_pink);
        } else {
            upload_btn.setClickable(false);
            upload_btn.setTextColor(gray);
        }
    }
}

