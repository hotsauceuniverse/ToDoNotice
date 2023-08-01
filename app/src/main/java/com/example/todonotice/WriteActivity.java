package com.example.todonotice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WriteActivity extends AppCompatActivity {

    ImageView close_btn;
    TextView upload_btn;
    EditText context_area;
    ImageView add_file;
    private static final int REQUEST_GALLERY = 22;

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

        context_area.addTextChangedListener(new TextWatcher() {
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

        // 글쓰기 사진 업로드
        add_file = findViewById(R.id.add_file);
        add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeOpenGallery();
            }
        });
    }

    private void writeOpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picure"), REQUEST_GALLERY);
    }

    // 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_GALLERY && data != null) {
                Uri selectedImage = data.getData();
            }
        }
    }
}

