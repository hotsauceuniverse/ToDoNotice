package com.example.todonotice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WriteActivity extends AppCompatActivity {

    ImageView close_btn;
    TextView upload_btn;
    EditText context_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_activity);

        // 글쓰기 창 닫기
        close_btn = (ImageView) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 업로드 버튼 활성화
        upload_btn = (TextView) findViewById(R.id.upload_btn);
        context_area = (EditText) findViewById(R.id.context_area);
        upload_btn.setEnabled(false); // 초기에 버튼 비활성화
        upload_btn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    upload_btn.setEnabled(true);
                    upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.iphone_pink));
                } else {
                    upload_btn.setEnabled(false);
                    upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.android_top_bar));
                }
            }
        });
    }
}
