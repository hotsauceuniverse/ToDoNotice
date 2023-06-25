package com.example.todonotice;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


// Fragment와 Activity에서 버튼이벤트를 발생시키는것은 조금 다르다. (Fragment는 android:onClick)를 사용x)
// 프래그먼트에서는 OnClickListener를 상속받아서 구현해줘야함.
// onClick메소드를 오버라이드 해줘야함.

public class FragmentProfile extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;
    private ImageView profileImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("profile");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImageView = view.findViewById(R.id.profile_image);
        Log.d("test123", "test123");

        // 사진 업로드
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });
        return view;
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("프로필 이미지 선택");
        builder.setItems(new CharSequence[]{"카메라", "앨범"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
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

    // 앨범 열기
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    // 이미지 선택 결과 처리
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK) {                     // 이미지 선택 결과가 OK(성공)인 경우에만 처리
            if(requestCode == REQUEST_CAMERA && data != null) {         // 요청 코드가 REQUEST_CAMERA이고, 데이터가 null이 아닌 경우, 즉 카메라로부터 이미지를 가져온 경우
                // 카메라로부터 이미지를 가져온 경우
                Bundle extras = data.getExtras();                       // data.getExtras()를 사용하여 이미지 데이터를 가져옴
                Bitmap imageBitmap = (Bitmap) extras.get("data");       // Bitmap 형태로 캐스트하여 imageBitmap 변수에 저장
                profileImageView.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_GALLERY && data != null) {    // 요청 코드가 REQUEST_GALLERY이고, 데이터가 null이 아닌 경우, 즉 갤러리에서 이미지를 선택한 경우
                // 갤러리에서 이미지를 선택한 경우
                Uri selectedImage = data.getData();                         // data.getData()를 사용하여 선택한 이미지의 Uri를 가져옴
                performCrop(selectedImage);                                 // performCrop() 메서드를 호출하여 선택한 이미지를 크롭하며, selectedImage를 인자로 전달
            } else if (requestCode == CROP_FROM_CAMERA && data != null) {   // 요청 코드가 CROP_FROM_CAMERA이고, 데이터가 null이 아닌 경우, 즉 크롭된 이미지를 받아온 경우
                // 크롭된 이미지를 받아온 경우
                Bundle extras = data.getExtras();                           // data.getExtras()를 사용하여 크롭된 이미지 데이터를 가져옴
                Bitmap croppedBitmap = (Bitmap) extras.get("data");         // Bitmap 형태로 캐스트하여 croppedBitmap 변수에 저장
                profileImageView.setImageBitmap(croppedBitmap);
            }
        }
    }

    // 이미지 크롭
    private void performCrop(Uri imageUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageUri, "image/*");     // imageUri는 크롭할 이미지의 Uri이며, "image/*"는 모든 이미지 타입을 지정

            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, CROP_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            // 크롭 앱이 설치되어 있지 않은 경우 처리할 내용을 추가
            Toast.makeText(requireContext(), "이미지 크롭 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }


}
