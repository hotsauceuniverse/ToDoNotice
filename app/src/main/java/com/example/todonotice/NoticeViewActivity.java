package com.example.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeViewActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private WriteData writeData;
    ImageView moreBtn;
    TextView viewTitle;
    TextView viewContent;

    @Override
    public void onCreate(Bundle saveInstateState) {
        super.onCreate(saveInstateState);
        setContentView(R.layout.diary_view);

        viewTitle = findViewById(R.id.view_title);
        viewContent = findViewById(R.id.view_content);

        moreBtn = findViewById(R.id.more_button);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu(view);
            }
        });
        diaryView();
    }

    private void popupMenu(View anchorView) {
        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), anchorView);
        getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit_text:
                        Intent intent = new Intent(getApplicationContext(), WriteEditActivity.class);
                        intent.putExtra("id", writeData.getId());
                        Log.d("id   ", "id   " + writeData.getId());

                        intent.putExtra("title", writeData.getTitle());
                        Log.d("title   ", "title   " + writeData.getTitle());

                        intent.putExtra("content", writeData.getContent());
                        Log.d("content   ", "content   " + writeData.getContent());

                        startActivity(intent);
                        break;

                    case R.id.delete_text:
                        String beforeTime = String.valueOf(writeData.getId());
                        mDBHelper.DeleteDiary(beforeTime);
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void diaryView() {
        mDBHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        writeData = new WriteData();
        writeData.setId(intent.getIntExtra("id", -1));
        writeData.setTitle(intent.getStringExtra("title"));
        writeData.setContent(intent.getStringExtra("content"));
        viewTitle.setText(writeData.getTitle());
        viewContent.setText(writeData.getContent());
    }
}
