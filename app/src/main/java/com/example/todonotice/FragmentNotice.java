package com.example.todonotice;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentNotice extends Fragment {

    ImageView more_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

        // more button
        more_button = rootView.findViewById(R.id.more_button);
        more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuPickerDialog();
            }
        });
        return rootView;
    }

    private void showMenuPickerDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_menu_custom, null);

        TextView menuCopy = dialogView.findViewById(R.id.copy_btn);
        TextView menuEdit = dialogView.findViewById(R.id.edit_btn);
        TextView menuDelete = dialogView.findViewById(R.id.delete_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        ViewGroup.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = 20;
        params.height = 20;
        alertDialog.show();

        // 복사하기 버튼 클릭 리스너
        menuCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "복사하기 버튼 클릭", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        // 수정하기 버튼 클릭 리스너
        menuEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "수정하기 버튼 클릭", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        // 삭제하기 버튼 클릭 리스너
        menuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "삭제하기 버튼 클릭", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }
}

