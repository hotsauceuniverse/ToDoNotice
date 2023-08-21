package com.example.todonotice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// 출처 : https://stickode.tistory.com/61
public class AdapterWrite extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Uri> mData = null;
    private Context mContext = null;
    private ArrayList<WriteData> writeList;


    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_area;
        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            image_area = itemView.findViewById(R.id.image_area);
        }
    }


    public AdapterWrite(ArrayList<WriteData> writeList) {
        this.writeList = writeList;
    }


    // 생성자에서 데이터 리스트 객체, Context를 전달 받음
    public AdapterWrite(ArrayList<Uri> uriArrayList, Context applicationContext) {
        mData = uriArrayList;
        mContext = applicationContext;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // context에서 LayoutInflater 객체를 얻음

        View view = inflater.inflate(R.layout.write_multi_image, parent, false);    // recyclerView에 들어갈 아이템뷰의 레이아웃을 inflate
        AdapterWrite.ViewHolder viewHolder = new AdapterWrite.ViewHolder(view);

        return  viewHolder;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Uri image_uri = mData.get(position);

        Glide.with(mContext)
                .load(image_uri)
                .into(((AdapterWrite.ViewHolder) holder).image_area);

                // .into((ImageView) holder.itemView); -> 에러
                // holder.itemView를 ImageView로 캐스팅하려고 시도하면서 발생하는 문제
                // RecyclerView의 ViewHolder 객체에서 itemView는 해당 뷰홀더에 연결된 아이템의 레이아웃 뷰(View)를 의미
                // holder.itemView는 뷰 객체인 ConstraintLayout이 되는데, 여기에 ImageView를 직접로 캐스팅하려고 하면 ClassCastException가 발생
                // 해결 방법으로는 아이템 뷰 내부에서 ImageView의 아이디를 가져와 사용
                // -> holder를 AdapterWrite.ViewHolder로 캐스팅하고, 그 뒤에 image_area를 사용하여 ImageView에 이미지를 로드하도록 수정


        // 추가한 이미지 삭제
        ((AdapterWrite.ViewHolder) holder).image_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    mData.remove(clickedPosition); // 해당 이미지 삭제
                    notifyItemRemoved(clickedPosition);
                    notifyItemRangeChanged(clickedPosition, mData.size());

                    // WriteActivity의 외부함수 호출
                    // 출처 : https://itmining.tistory.com/15
                    ((WriteActivity)WriteActivity.mContext).updateUploadButton();
                }
            }
        });
    }


    // getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
