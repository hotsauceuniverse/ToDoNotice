package com.example.todonotice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private ArrayList<ToDoItem> todoList;
    private Context mContext;
    private DBHelper2 mDBHelper2;

    public ToDoAdapter(ArrayList<ToDoItem> todoList, Context mContext) {
        this.todoList = todoList;
        this.mContext = mContext;
        mDBHelper2 = new DBHelper2(mContext);
    }

    public void setTodoData(ArrayList<ToDoItem> toDoItem) {
        this.todoList = toDoItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToDoAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_cell,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ToDoViewHolder holder, int position) {
        ToDoItem toDoItem = todoList.get(position);
        if (toDoItem != null) {
            holder.onBind(toDoItem);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = holder.getAdapterPosition();
                ToDoItem item = todoList.get(curPos);
                SQLiteDatabase db = mDBHelper2.getReadableDatabase();

                String[] strChoiceItem = {"수정하기", "삭제하기"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("어떤 작업을 할까요?");
                builder.setItems(strChoiceItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            // Data Base open id값 기준
                            Cursor cursor = db.rawQuery("SELECT * FROM TODOLIST_TEXT_SEQ WHERE id = ?", new String[]{String.valueOf(item.getId())});
                            if (cursor != null && cursor.moveToFirst()) {
                                // 열 인덱스 가져오기
                                int idIndex = cursor.getColumnIndex("id");
                                int todoIndex = cursor.getColumnIndex("todo");
                                int hourIndex = cursor.getColumnIndex("hour");
                                int minIndex = cursor.getColumnIndex("min");
                                int placeIndex = cursor.getColumnIndex("place");
                                int memoIndex = cursor.getColumnIndex("memo");

                                // 열 인덱스로 데이터 추출
                                int id = cursor.getInt(idIndex);
                                String todoTitle = cursor.getString(todoIndex);
                                String todoHour = cursor.getString(hourIndex);
                                String todoMin = cursor.getString(minIndex);
                                String todoPlace = cursor.getString(placeIndex);
                                String todoMemo = cursor.getString(memoIndex);

                                // Intent에 데이터 추가
                                Intent intent = new Intent(mContext, CalendarTodoListEdit.class);
                                intent.putExtra("id", id);
                                Log.d("id   ", "id   " + id);

                                intent.putExtra("todoTitle", todoTitle);
                                Log.d("title   ", "title   " + todoTitle);

                                intent.putExtra("todoHour", todoHour);
                                Log.d("hour   ", "hour   " + todoHour);

                                intent.putExtra("todoMin", todoMin);
                                Log.d("min   ", "min   " + todoMin);

                                intent.putExtra("todoPlace", todoPlace);
                                Log.d("place   ", "place   " + todoPlace);

                                intent.putExtra("todoMemo", todoMemo);
                                Log.d("memo   ", "memo   " + todoMemo);

                                mContext.startActivities(new Intent[]{intent});
                            }
                        } else if (i == 1) {
                            // delete table
                            String beforeTime = String.valueOf(item.getId());
                            mDBHelper2.DeleteToDoList(beforeTime);

                            // delete UI
                            todoList.remove(curPos);
                            notifyItemRemoved(curPos);
                            Toast.makeText(mContext, "제거" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addItem(ToDoItem toDoItem) {
        todoList.add(toDoItem);
        notifyItemInserted(todoList.size() - 1);
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView TodoTextView;
        private TextView hourTv;
        private TextView minTv;
        private LinearLayout todoBox;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            TodoTextView = itemView.findViewById(R.id.todo_tv);
            hourTv = itemView.findViewById(R.id.hour_tv);
            minTv = itemView.findViewById(R.id.min_tv);
            todoBox = itemView.findViewById(R.id.todo_box);
        }

        public void onBind(ToDoItem item) {
            TodoTextView.setText(item.getTodo());
            hourTv.setText(item.getHour());
            minTv.setText(item.getMin());
        }
    }
}