package com.example.todonotice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(new CharSequence[]{"복사하기", "수정하기", "삭제하기"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Toast.makeText(getActivity().getApplicationContext(),"테스트 복사", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity().getApplicationContext(),"테스트 수정", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity().getApplicationContext(),"테스트 삭제", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.show();
    }
}

