package com.example.todonotice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//  Activity에서 Fragment로 옮길때 변경사항
//  1. onCreate() 메서드를 onCreateView()로 변경하여 프래그먼트 뷰를 생성하고 반환
//  2. findViewById() 호출을 rootView.findById()로 변경 ( rootView는 onCreateView() 메서드에서 반환한 뷰 )
//  3. openFileInput() 및 openFileOutput() 메서드를 requireContext().openFileInput() 및 requireContext().openFileOutput()로 변경
//  4. 파일 작성 모드를 Context.MODE_PRIVATE로 변경

public class FragmentToDoList extends Fragment {

    public String readDay = null;
    public String str = null;
    public CalendarView calendar;
    public Button save_button, edit_button, delete_button;
    public TextView diaryTextView, textView;
    public EditText contextEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todolist, container, false);

        calendar = rootView.findViewById(R.id.calendar);
        diaryTextView = rootView.findViewById(R.id.diaryTextView);
        save_button = rootView.findViewById(R.id.save_button);
        delete_button = rootView.findViewById(R.id.delete_button);
        edit_button = rootView.findViewById(R.id.edit_button);
        textView = rootView.findViewById(R.id.textView2);
        contextEditText = rootView.findViewById(R.id.contextEditText);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                save_button.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                edit_button.setVisibility(View.INVISIBLE);
                delete_button.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                textView.setText(str);
                save_button.setVisibility(View.INVISIBLE);
                edit_button.setVisibility(View.VISIBLE);
                delete_button.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
    }

    // 선택 날짜에 해당하는 일정 검색
    public void checkDay(int cYear, int cMonth, int cDay) {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";	// 저장할 파일 이름 설정
        FileInputStream fileInputStream;	// 파일 읽기 선언

        // 선택한 일정 파일 열고 데이터 읽기
        // fileData 배열에 저장
        try {
            fileInputStream = requireContext().openFileInput(readDay);

            byte[] fileData = new byte[fileInputStream.available()];
            fileInputStream.read(fileData);
            fileInputStream.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(str);

            save_button.setVisibility(View.INVISIBLE);
            edit_button.setVisibility(View.VISIBLE);
            delete_button.setVisibility(View.VISIBLE);

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    save_button.setVisibility(View.VISIBLE);
                    edit_button.setVisibility(View.INVISIBLE);
                    delete_button.setVisibility(View.INVISIBLE);
                    textView.setText(contextEditText.getText());
                }
            });

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_button.setVisibility(View.VISIBLE);
                    edit_button.setVisibility(View.INVISIBLE);
                    delete_button.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                }
            });

            if (textView.getText() == null) {
                textView.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_button.setVisibility(View.VISIBLE);
                edit_button.setVisibility(View.INVISIBLE);
                delete_button.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    참고 : https://c-an.tistory.com/13
    //    Activity.java에서는 MODE_NO_LOCALIZED_COLLATORS : Database open flag: when set, the database is opened without support for localized collator
    //    Fragment.java에서는 MODE_PRIVATE : File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = requireContext().openFileOutput(readDay, Context.MODE_PRIVATE);
            String content = "";
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = requireContext().openFileOutput(readDay, Context.MODE_PRIVATE);
            String content = contextEditText.getText().toString();
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}