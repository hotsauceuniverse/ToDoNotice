package com.example.todonotice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {

    Toolbar noticeToolbar;
    LinearLayout news_pre_1;
    LinearLayout news_pre_2;
    LinearLayout news_pre_3;
    LinearLayout notice_pre_1;
    LinearLayout notice_pre_2;
    TextView subTv1;
    TextView subTv2;
    TextView contentTv1;
    TextView contentTv2;
    TextView todolist_pre_1;
    TextView todolist_pre_2;
    TextView todolistFirst;
    TextView todolistSecond;
    TextView newsFirst;
    ImageView newsImgFirst;
    TextView newsSecond;
    ImageView newsImgSecond;
    TextView newsThird;
    ImageView newsImgThird;
    private DBHelper mDBHelper;
    private DBHelper2 mDBHelper2;
    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        todolist_pre_1 = rootView.findViewById(R.id.action_1_btn);
        todolist_pre_2 = rootView.findViewById(R.id.action_2_btn);
        todolistFirst = rootView.findViewById(R.id.todolist_first);
        todolistSecond = rootView.findViewById(R.id.todolist_second);
        subTv1 = rootView.findViewById(R.id.sub_tv_1);
        subTv2 = rootView.findViewById(R.id.sub_tv_2);
        contentTv1 = rootView.findViewById(R.id.content_tv_1);
        contentTv2 = rootView.findViewById(R.id.content_tv_2);
        notice_pre_1 = rootView.findViewById(R.id.sub_con_1);
        notice_pre_2 = rootView.findViewById(R.id.sub_con_2);
        news_pre_1 = rootView.findViewById(R.id.news_lay_st);
        news_pre_2 = rootView.findViewById(R.id.news_lay_nd);
        news_pre_3 = rootView.findViewById(R.id.news_lay_rd);
        newsFirst = rootView.findViewById(R.id.news_first);
        newsImgFirst = rootView.findViewById(R.id.news_img_first);
        newsSecond = rootView.findViewById(R.id.news_second);
        newsImgSecond = rootView.findViewById(R.id.news_img_second);
        newsThird = rootView.findViewById(R.id.news_third);
        newsImgThird = rootView.findViewById(R.id.news_img_third);

        queue = Volley.newRequestQueue(getContext());

        noticeDBSearch();
        todolistDBSearch();
        newsApiSearch();
        todolistClickEvent();
        noticeClickEvent();
        newsClickEvent();

        return rootView;
    }

    // 데이터 조회 수정필요
    public void noticeDBSearch() {
        mDBHelper = new DBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, content FROM DIARYLIST_TEXT_SEQ ORDER BY id DESC LIMIT 2", null);
        Log.d("cursor", "cursor " + cursor);

        String defaultTitle = "나의 일기가 없습니다.";
        String defaultContent = "나의 일기가 없습니다.";

        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");

            if (titleIndex >= 0 && contentIndex >= 0) {
                for (int i = 0; i < 2; i++) {
                    String title;
                    String content;

                    if (i == 0) {
                        title = cursor.getString(titleIndex);
                        content = cursor.getString(contentIndex);

                        subTv1.setText(title);
                        contentTv1.setText(content);

                        Log.d("DB   ", "title:   " + title + "   content:   " + content);
                    } else if (i == 1) {
                        if (cursor.moveToNext()) {
                            title = cursor.getString(titleIndex);
                            content = cursor.getString(contentIndex);
                        } else {
                            title = defaultTitle;
                            content = defaultContent;
                        }

                        subTv2.setText(title);
                        contentTv2.setText(content);

                        Log.d("DB   ", "title:   " + title + "   content:   " + content);
                    }
                }
            } else {
                Log.d("fail   ", "fail   ");
            }

            cursor.close();
        } else {
            // 최초 일기 데이터가 없을 경우 or 첫번째 데이터만 있을 경우 가상의 문구 insert
            subTv1.setText(defaultTitle);
            contentTv1.setText(defaultContent);
            subTv2.setText(defaultTitle);
            contentTv2.setText(defaultContent);
        }
    }

    public void todolistDBSearch() {
        // 오늘 날짜를 감지해 오늘 일정만 보여주기
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        mDBHelper2 = new DBHelper2(getContext());
        SQLiteDatabase db = mDBHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT todo, hour, min FROM TODOLIST_TEXT_SEQ WHERE writeDate = ? ORDER BY id DESC", new String[]{todayStr});

        String defaultTodo = "오늘은 약속이 없어요.";

        if (cursor != null && cursor.moveToNext()) {
            int todoIndex = cursor.getColumnIndex("todo");
            int hourIndex = cursor.getColumnIndex("hour");
            int minIndex = cursor.getColumnIndex("min");

            if (todoIndex >= 0 && hourIndex >= 0 && minIndex >= 0) {
                for (int i = 0; i < 2; i++) {
                    String todo;
                    String hour;
                    String min;

                    // 문구 : 오늘은 hour시 min분에 todo 약속이 있어요 :)
                    if (i == 0) {
                        todo = cursor.getString(todoIndex);
                        hour = cursor.getString(hourIndex);
                        min = cursor.getString(minIndex);

                        todolistFirst.setText("오늘 " + hour + "시 " + min + "분에 " + todo + "약속이 있어요.");
                    } else if (i == 1) {
                        if (cursor.moveToNext()) {
                            todo = cursor.getString(todoIndex);
                            hour = cursor.getString(hourIndex);
                            min = cursor.getString(minIndex);

                            todolistSecond.setText("오늘 " + hour + "시 " + min + "분에 " + todo + "약속이 있어요. :)");
                        } else {
                            todo = defaultTodo;
                            todolistSecond.setText(todo);
                        }
                    }
                }
            } else {
                Log.d("fail   ", "fail   ");
            }
            cursor.close();
        } else {
            // 오늘 날짜가 아니면 해당 핃드에 defaultTodo문구 노출
            todolistFirst.setText(defaultTodo);
            todolistSecond.setText(defaultTodo);
        }
    }

    public void newsApiSearch() {
        String url = "https://newsapi.org/v2/top-headlines?country=kr&apiKey=ce563d897b6c46a6b2e5ee5f32a22b1f";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("articles");

                    int count = 0;

                    for (int i = 0; i < array.length() && count < 3; i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String title = obj.getString("title");
                        String imageUrl = obj.optString("urlToImage", null);

                        if (imageUrl != null && !imageUrl.equals("null") && !imageUrl.startsWith("http://") && !imageUrl.startsWith("/")) {
                            count++;
                            if (isAdded()) {
                                switch (count) {
                                    case 1:
                                        newsFirst.setText(title);
                                        Glide.with(getContext()).load(imageUrl).into(newsImgFirst);
                                        break;
                                    case 2:
                                        newsSecond.setText(title);
                                        Glide.with(getContext()).load(imageUrl).into(newsImgSecond);
                                        break;
                                    case 3:
                                        newsThird.setText(title);
                                        Glide.with(getContext()).load(imageUrl).into(newsImgThird);
                                        break;
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void todolistClickEvent() {
        todolist_pre_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();;
                mainActivity.showToDoListBottomNavigation();

                FragmentToDoList fragmentToDoList = new FragmentToDoList();
                // Fragment todoList로 교체
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentToDoList);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        todolist_pre_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ToDoList toolBar, BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showToDoListBottomNavigation();

                FragmentToDoList fragmentToDoList = new FragmentToDoList();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentToDoList);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void noticeClickEvent() {
        notice_pre_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Notice toolBar, BottomNavigationBar 변경
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showNoticeToolbar();
                mainActivity.showNoticeBottomNavigation();

                noticeToolbar = mainActivity.findViewById(R.id.notice_toolbar);
                mainActivity.setSupportActionBar(noticeToolbar);
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle("게시판");
                }

                FragmentNoticeOutline fragmentNotice = new FragmentNoticeOutline();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentNotice);    // R.id.content_frame는 FragmentNotice를 추가할 컨테이너 ID
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        notice_pre_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showNoticeToolbar();
                mainActivity.showNoticeBottomNavigation();

                noticeToolbar = mainActivity.findViewById(R.id.notice_toolbar);
                mainActivity.setSupportActionBar(noticeToolbar);
                ActionBar actionBar = mainActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle("게시판");
                }

                FragmentNoticeOutline fragmentNotice = new FragmentNoticeOutline();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragmentNotice);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void newsClickEvent() {
        // 뉴스 레이아웃으로 이동 (fragment -> activity)
        news_pre_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        news_pre_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        news_pre_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
