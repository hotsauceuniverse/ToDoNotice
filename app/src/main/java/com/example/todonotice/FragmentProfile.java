package com.example.todonotice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

// Fragment와 Activity에서 버튼이벤트를 발생시키는것은 조금 다르다. (Fragment는 android:onClick)를 사용x)
// 프래그먼트에서는 OnClickListener를 상속받아서 구현해줘야함.
// onClick메소드를 오버라이드 해줘야함.

public class FragmentProfile extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;
    private CircleImageView profileImageView;
    private int Default_profile = R.drawable.default_profile;
    Button profile_edit_button;
    private  EditText nicknameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = rootView.findViewById(R.id.profile_image);
        profileImageView.setImageResource(Default_profile);

        // 사진 업로드
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });

        // 프로필 버튼 색상 변경
        nicknameEditText = rootView.findViewById(R.id.nickname);
        profile_edit_button = rootView.findViewById(R.id.profile_edit_button);
        updateButtonColor();

        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateButtonColor();
            }
        });
        return rootView;
    }

    private void updateButtonColor() {
        String nickname = nicknameEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(nickname)) {
            int iphone_pink = ContextCompat.getColor(getContext(), R.color.iphone_pink);
            profile_edit_button.setBackgroundColor(iphone_pink);
        } else {
            int gray = ContextCompat.getColor(getContext(), R.color.android_top_bar);
            profile_edit_button.setBackgroundColor(gray);
        }
    }


    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("프로필 이미지 선택");
        builder.setItems(new CharSequence[]{"카메라", "앨범", "기본 이미지"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                    case 2:
                        profileImageView.setImageResource(Default_profile);
                        break;
                }
            }
        });
        builder.show();
    }

    // 카메라 열기
    private void openCamera() {
        int cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 부여된 경우 카메라를 열 수 있는 로직을 실행
            launchCamera();
        } else {
            // 권한이 부여되지 않은 경우 권한을 요청
            String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, REQUEST_CAMERA_PERMISSION_CODE);
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    // 앨범 열기
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되었으므로 카메라를 열 수 있는 로직을 실행
                launchCamera();
            } else {
                // 권한이 거부되었거나 취소된 경우 처리할 내용을 추가
                Toast.makeText(requireContext(), "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 이미지 선택 결과 처리
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 이미지 선택 결과가 OK인 경우에만 처리
        if(resultCode == getActivity().RESULT_OK) {

            if(requestCode == REQUEST_CAMERA && data != null) {
                // 카메라로부터 이미지를 가져온 경우, data.getExtras()를 사용하여 이미지 데이터를 가져옴
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                performCrop(getImageUri(getActivity(), imageBitmap));

            } else if (requestCode == REQUEST_GALLERY && data != null) {
                // 갤러리에서 이미지를 선택한 경우, data.getData()를 사용하여 선택한 이미지의 Uri를 가져옴
                Uri selectedImage = data.getData();
                performCrop(selectedImage);

            } else if (requestCode == CROP_FROM_CAMERA && data != null) {
                // 크롭된 이미지를 받아온 경우, data.getExtras()를 사용하여 크롭된 이미지 데이터를 가져옴
                Bundle extras = data.getExtras();
                Bitmap croppedBitmap = (Bitmap) extras.get("data");
                profileImageView.setImageBitmap(croppedBitmap);
            }
        }
    }

    // 이미지 크롭 https://g-y-e-o-m.tistory.com/48
    private void performCrop(Uri imageUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageUri, "image/*");     // imageUri는 크롭할 이미지의 Uri이며, "image/*"는 모든 이미지 타입을 지정

            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("outputX", 256);    // crop한 이미지의 x축 크기, 결과물의 크기
            cropIntent.putExtra("outputY", 256);    // crop한 이미지의 y축 크기
            cropIntent.putExtra("aspectX", 1);      // crop 박스의 x축 비율, 1&1이면 정사각형
            cropIntent.putExtra("aspectY", 1);      // crop 박스의 y축 비율
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, CROP_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        String filePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(filePath);
    }
}

