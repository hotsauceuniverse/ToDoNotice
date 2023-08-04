package com.example.todonotice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class IntroActivity extends AppCompatActivity {

    TextView test;
    Button Kakao_Button;
    private static final String TAG = "KakaoLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        // home view test
        TextView test = (TextView) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();   //  introView 종료
            }
        });

        Log.d("getKeyHash", "" + getKeyHash(IntroActivity.this));


        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken,Throwable,Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG,"CallBack Method");
                // oAuthToken != null 이면 로그인 성공
                if(oAuthToken != null) {
                    // 토근이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패
                    updateKakaoLoginUi();
                } else {
                    // 로그인 실패
                    Log.e(TAG, "invoke: login fail");
                }
                return null;
            }
        };

        // 로그인 버튼 클릭 리스너
        Kakao_Button = findViewById(R.id.kakao_login);
        Kakao_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 해당 기기에 카카오톡이 설치되어 있는지 확인
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(IntroActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(IntroActivity.this, callback);
                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                } else {
                    // 카카오톡이 설치 되어 있지 않다면
                    UserApiClient.getInstance().loginWithKakaoTalk(IntroActivity.this, callback);
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateKakaoLoginUi() {
        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if(user != null) {
                    // 유저의 아이디
                    Log.d(TAG, "invoke : id = " + user.getId());
                    // 유저의 이메일
                    Log.d(TAG, "invoke : e-mail = " + user.getKakaoAccount().getEmail());
                    // 유저의 닉네임
                    Log.d(TAG, "invoke : nickname = " + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 성별
                    Log.d(TAG, "invoke : gender = " + user.getKakaoAccount().getGender());
                    // 유저의 연령대
                    Log.d(TAG, "invoke : age = " + user.getKakaoAccount().getAgeRange());

                    // 로그인이 되어있으면 홈화면으로 이동
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "invoke: user is null");
                }
                return null;
            }
        });
    }


    // HashKey 받기
    private String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if(packageInfo == null) {
                return null;
            }
            for(Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}