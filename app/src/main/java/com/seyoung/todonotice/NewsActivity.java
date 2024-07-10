package com.seyoung.todonotice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsActivity extends AppCompatActivity {

    ImageView close_btn;
    private RecyclerView newsRecyclerView;
    private RecyclerView.Adapter newsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;
    LottieAnimationView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newsRecyclerView = findViewById(R.id.news_recyclerView);

        // progressbar custom
        // NewsActivity 내에서 ProgressBar를 표시하고 숨기기
        loadingAnimation = findViewById(R.id.loading_animation);

        newsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(layoutManager);

        // 1. 화면 로딩 -> 뉴스 정보 받기
        queue = Volley.newRequestQueue(this);
        Log.d("queue", "queue" + queue);

        loadingAnimation.setVisibility(View.VISIBLE);

        getNews();
    }

    // 네트워크 요청을 보내고, 응답을 받은 후 JSON 데이터를 처리
    public void getNews() {

        String url = "https://newsapi.org/v2/top-headlines?country=kr&apiKey=ce563d897b6c46a6b2e5ee5f32a22b1f";
                      
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loadingAnimation.setVisibility(View.INVISIBLE);

                Log.d("news1", "news1" + response);

                // JSON의 형태로 직접 가져오기
                try {
                    // JSONString -> JSON
                    JSONObject jsonObject = new JSONObject(response);

                    // JSON 객체 안의 JSON객체들을 담고있는 JSONArray객체 get
                    JSONArray arrayArticles = jsonObject.getJSONArray("articles");

                    // response -> NewsData Class 분류
                    List<NewsData> news = new ArrayList<>();

                    for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                        JSONObject obj = arrayArticles.getJSONObject(i);

                        Log.d("news2", "news2" + obj.toString());

                        NewsData newsData = new NewsData();
                        newsData.setTitle(obj.getString("title"));
                        Log.d("title   ", "title   " + obj.get("title"));

                        newsData.setUrlToImage(obj.getString("urlToImage"));
                        Log.d("urlToImage   ", "urlToImage   " + obj.get("urlToImage"));

                        newsData.setUrl(obj.getString("url"));
                        Log.d("url   ", "url   " + obj.get("url"));

                        // 처음부터 UrlToImage가 null인 경우와 http://로 시작하는 경우와 특정경로로 시작하는 기사를 배열에서 제외
                        if (!newsData.getUrlToImage().equals("null") &&
                                !newsData.getUrlToImage().startsWith("http://") &&
                                !newsData.getUrlToImage().startsWith("/")) {
                            news.add(newsData);
                        }
                        Log.d("newsData", "newsData" + newsData);
                    }

                    // news_cell의 가장 상위 LinearLayout을 탭 할 때 넘기는 방법 (tag쓰고 position값 가져오기)
                    newsAdapter = new NewsAdapter(news, NewsActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view.getTag() != null) {
                                int position = (int)view.getTag();
                                Log.d("position   ", "position   " + position);

                                // 뉴스 웹 뷰로 이동
                                if (news != null && position < news.size()) {
                                    ((NewsAdapter)newsAdapter).getNews(position);
                                    Intent intent = new Intent(NewsActivity.this, NewsWebView.class);
                                    intent.putExtra("URL", news.get(position).getUrl());
                                    startActivity(intent);
                                }
                            }
                        }
                    });

                    // 2. 정보 -> 어뎁터 넘기기
                    newsRecyclerView.setAdapter(newsAdapter);
                    Log.d("newsAdapter", "newsAdapter" + newsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("newsData", "error" + error.toString());
                Log.d("newsData", "error" + error.networkResponse.toString());

                loadingAnimation.setVisibility(View.INVISIBLE);
            }
        }){
            // BasinNetwork.performRequest results "Unexpected response code 403 for 에러코드
            // header가 없어서 생기는 문제 -> onErrorResponse 여기로 떨어짐
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
