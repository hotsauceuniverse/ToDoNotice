package com.example.todonotice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotice extends RecyclerView.Adapter<AdapterNotice.ViewHolder> {

    private ArrayList<NoticeData> arrayList;

    // Alt + Insert
    // Constructor
    public AdapterNotice(ArrayList<NoticeData> arrayList) {
        this.arrayList = arrayList;
    }

    // Implement Methods
    @NonNull
    @Override
    public AdapterNotice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotice.ViewHolder holder, int position) {
        holder.profile_img.setImageResource(arrayList.get(position).getProfile_img());
        holder.user_id_tv.setText(arrayList.get(position).getUser_id_tv());
        holder.date_tv.setText(arrayList.get(position).getDate_tv());
        holder.like_iv.setImageResource(arrayList.get(position).getLike_iv());
        holder.comment_button.setImageResource(arrayList.get(position).getComment_button());
        holder.more_button.setImageResource(arrayList.get(position).getMore_button());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 아이템 클릭 시 처리할 로직 작성
            }
        });
    }

    @Override
    public int getItemCount() {
        // 글 추가할 때 리스트 뷰가 추가되는 부분
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView user_id_tv;
        AppCompatTextView date_tv;
        protected ImageView profile_img;
        protected ImageView like_iv;
        protected ImageView comment_button;
        protected ImageView more_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id_tv = itemView.findViewById(R.id.user_id_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            profile_img = itemView.findViewById(R.id.profile_img);
            like_iv = itemView.findViewById(R.id.like_iv);
            comment_button = itemView.findViewById(R.id.comment_button);
            more_button = itemView.findViewById(R.id.more_button);

        }
    }
}
