package com.example.todonotice;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterWrite extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WriteData> writeList;

    public AdapterWrite(ArrayList<WriteData> writeList) {
        this.writeList = writeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
