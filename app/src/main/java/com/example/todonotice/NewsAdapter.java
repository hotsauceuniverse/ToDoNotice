package com.example.todonotice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

// fresco : 이미지 관리 라이브러리 (메모리 관리 좋음)
// volley : http 통신하는 라이브러리

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsData> mDataset;

    public NewsAdapter(List<NewsData> myDataset, Context context) {
        mDataset = myDataset;
        Fresco.initialize(context);
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cell,parent,false);
        ViewHolder viewHolder = new ViewHolder(linearLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsData newsData = mDataset.get(position);

        holder.title_Tv.setText(newsData.getTitle());
        holder.contents_Tv.setText(newsData.getContent());

        Uri uri = Uri.parse(newsData.getUrlToImage());  // Fresco
        holder.news_Iv.setImageURI(uri);    // 이미지 셋팅
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_Tv;
        public TextView contents_Tv;
        public SimpleDraweeView news_Iv;

        public ViewHolder(@NonNull View view) {
            super(view);
            title_Tv = view.findViewById(R.id.title_tv);
            contents_Tv = view.findViewById(R.id.contents_tv);
            news_Iv = view.findViewById(R.id.news_iv);
        }
    }
}

