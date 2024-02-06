package com.example.todonotice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private ArrayList<ToDoItem> todoList;

    public ToDoAdapter(ArrayList<ToDoItem> todoList) {
        this.todoList = todoList;
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
        private TextView TodoTimeView;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            TodoTextView = itemView.findViewById(R.id.todo_tv);
            TodoTimeView = itemView.findViewById(R.id.time_tv);
        }

        public void onBind(ToDoItem item) {
            TodoTextView.setText(item.getTodo());
            String textTime = item.getHour() + ":" + item.getMin();

            if (!item.getAm().equals("")) {
                textTime = textTime + " " + item.getAm();
            } else {
                textTime = textTime + " " + item.getPm();
            }
            TodoTextView.setText(textTime);
        }
    }
}
