package com.example.todonotice;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        if (position % 2 == 1 && toDoItem != null) {
            holder.onBind(toDoItem);
            holder.todoBox.setBackgroundColor(ContextCompat.getColor(mContext, R.color.todolist_box));
        } else if (position % 2 != 1 && toDoItem != null) {
            holder.onBind(toDoItem);
            holder.todoBox.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 리스트 선택 기능
                Toast.makeText(mContext, "제거 완료" , Toast.LENGTH_SHORT).show();
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
