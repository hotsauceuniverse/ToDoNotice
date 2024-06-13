package com.example.todonotice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private ArrayList<WriteData> mWriteData;
    private Context mContext;
    private DBHelper mDBHelper;
    private FragmentNoticeOutline fragmentNoticeOutline;
    public static final int REQUEST_CODE_NOTICE = 102;

    // Alt + Insert
    // Constructor
    // 생성자에서 데이터 리스트 객체를 전달받음
    public NoticeAdapter(ArrayList<WriteData> mWriteData, Context mContext, FragmentNoticeOutline fragment) {
        this.mWriteData = mWriteData;
        this.mContext = mContext;
        this.fragmentNoticeOutline = fragment;
        mDBHelper = new DBHelper(mContext);
    }

    public void setWriteData(ArrayList<WriteData> writeData) {
        this.mWriteData = writeData;
        notifyDataSetChanged();
    }

    // Implement Methods
    // 아이템 뷰를 위한 뷰홀더 객체 생성 후 리턴
    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice, parent, false);

        return new ViewHolder(holder);
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        holder.titleTv.setText(mWriteData.get(position).getTitle());
        holder.contentTv.setText(mWriteData.get(position).getContent());
        holder.dateTv.setText(mWriteData.get(position).getWriteDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 리스트 클릭한 아이템 위치
                int curPos = holder.getAdapterPosition();
                WriteData writeData = mWriteData.get(curPos);

                Intent intent = new Intent(mContext, NoticeViewActivity.class);

                intent.putExtra("id", writeData.getId());
                intent.putExtra("title", writeData.getTitle());
                intent.putExtra("content", writeData.getContent());

                fragmentNoticeOutline.startActivityForResult(intent, REQUEST_CODE_NOTICE);
            }
        });
    }

    // 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mWriteData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private TextView dateTv;
        private TextView contentTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.title_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            contentTv = itemView.findViewById(R.id.content_tv);
        }
    }
}
