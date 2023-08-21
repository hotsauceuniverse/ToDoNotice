package com.example.todonotice;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {

    private static final String TAG = "Multiple Image";
    ArrayList<Uri> uriArrayList = new ArrayList<>();
    RecyclerView img_recyclerView;      // 이미지를 보여줄 recyclerView
    AdapterWrite adapterWrite;          // recyclerView에 적용시킬 어댑터
    ImageView close_btn;
    TextView upload_btn;
    EditText context_area;
    ImageView add_file;
    public static Context mContext;     // Activity 함수 호출

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
        RecyclerView recyclerView = findViewById(R.id.img_recyclerView);

        adapterWrite = new AdapterWrite(uriArrayList, getApplicationContext());
        recyclerView.setAdapter(adapterWrite);

        // RecyclerView에 이미지 추가될 때의 리스너 설정
        adapterWrite.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateUploadButton();
            }
        });

        // EditText에 입력이 변경 될 때 리스너 설정
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

        // 앨범으로 이동하는 버튼
        add_file = findViewById(R.id.add_file);
        add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);     // 다중 이미지를 가져올 수 있도록 세팅
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });
        img_recyclerView = findViewById(R.id.img_recyclerView);

        // Activity 함수 호출 (싱글톤 패턴 알아보기)
        mContext = this;
    }

    // AdapterWrite 함수 연결
    void updateUploadButton() {
        boolean isImagesAdded = uriArrayList.size() > 0;
        boolean isTextNotEmpty = context_area.getText().length() > 0;

        Log.e("isImagesAdded", "isImagesAdded" + isImagesAdded);
        Log.e("arrayList.size", "arrayList.size" + uriArrayList.size());

        upload_btn.setEnabled(isImagesAdded || isTextNotEmpty);
        upload_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), isImagesAdded || isTextNotEmpty ? R.color.iphone_pink : R.color.android_top_bar));
    }

    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 어떤 이미지도 선택하지 않은 경우
        if(data == null) {
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
            Log.e("no image", String.valueOf(data.getData()));
        } else {
            // 이미지를 하나라도 선택한 경우
            if(data.getClipData() == null) {    // 이미지를 하나만 선택한 경우
                Log.e("one image ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriArrayList.add(imageUri);

                adapterWrite = new AdapterWrite(uriArrayList, getApplicationContext());
                img_recyclerView.setAdapter(adapterWrite);
                img_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

            } else {    // 이미지를 여러장 선택한 경우
                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                if(clipData.getItemCount() > 10) {      // 선택한 이미지가 10장 초과인 경우
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();

                } else {    // 선택한 이미지가 1장 이상 10장 이하인 경우
                    Log.e(TAG, "multiple choice");

                    for(int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져옴

                        try {
                            uriArrayList.add(imageUri);     // uri를 list에 담는다
                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }

                    adapterWrite = new AdapterWrite(uriArrayList, getApplicationContext());
                    img_recyclerView.setAdapter(adapterWrite);  // recyclerView에 adapter 세팅
                    img_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true));    // recyclerView 수직 스크롤 적용

                    updateUploadButton();
                }
            }
        }
    }
}

