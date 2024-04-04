package com.example.todonotice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotice extends RecyclerView.Adapter<AdapterNotice.ViewHolder> {

    private ArrayList<WriteData> mWriteData;
    private Context mContext;
    private DBHelper mDBHelper;

    // Alt + Insert
    // Constructor
    // 생성자에서 데이터 리스트 객체를 전달받음
    public AdapterNotice(ArrayList<WriteData> mWriteData, Context mContext) {
        this.mWriteData = mWriteData;
        this.mContext = mContext;
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
    public AdapterNotice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice, parent, false);

        return new ViewHolder(holder);
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull AdapterNotice.ViewHolder holder, int position) {
        holder.titleTv.setText(mWriteData.get(position).getTitle());
        holder.contentTv.setText(mWriteData.get(position).getContent());
        holder.dateTv.setText(mWriteData.get(position).getWriteDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = holder.getAdapterPosition();  // 현재 리스트 클릭한 아이템 위치
                WriteData writeData = mWriteData.get(curPos);

                Log.e("   aaa", "writeData  " + writeData.id);
                String[] strChoiceItem = {"수정하기", "삭제하기"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("어떤 작업을 할까요?");
                builder.setItems(strChoiceItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (position == 0) {
                            // edit table
                            Intent intent = new Intent(mContext, WriteEditActivity.class);
                            intent.putExtra("id", writeData.getId());
                            Log.d("id   ", "id   " + writeData.getId());

                            intent.putExtra("title", writeData.getTitle());
                            Log.d("title   ", "title   " + writeData.getTitle());

                            intent.putExtra("content", writeData.getContent());
                            Log.d("content   ", "content   " + writeData.getContent());

                            mContext.startActivities(new Intent[]{intent});
                        } else if (position == 1) {
                            // delete table
                            String beforeTime = String.valueOf(writeData.getId());
                            mDBHelper.DeleteDiary(beforeTime);

                            // delete UI
                            mWriteData.remove(curPos);
                            notifyItemRemoved(curPos);
                            Toast.makeText(mContext, "제거 완료" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
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
