package com.example.todonotice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class IntroActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    RelativeLayout Kakao_Button, Google_login ;
    private static final String TAG_k = "KakaoLogin";
    private static final String TAG_G = "GoogleLogin";
    private FirebaseAuth firebaseAuth;                  // firebase 인증 객체
    private GoogleApiClient googleApiClient;            // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100;     // 구글 로그인 결과 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        // android default intro splash remove (build:gradle, theme,
        // https://stackoverflow.com/questions/72003647/react-native-splash-screen-on-android-12
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        setTheme(R.style.Theme_ToDoNotice);

        Log.d("getKeyHash", "" + getKeyHash(IntroActivity.this));

        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken,Throwable,Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG_k,"CallBack Method");
                // oAuthToken != null 이면 로그인 성공
                if(oAuthToken != null) {
                    // 토큰이 전달된다면 로그인이 성공한 것이고, 토큰이 전달되지 않으면 로그인 실패
                    updateKakaoLoginUi();
                } else {
                    // 로그인 실패
                    Log.e(TAG_k, "invoke: login fail");
                }
                return null;
            }
        };

        // 카카오 로그인 버튼 클릭 리스너
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

        // 구글 API
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();      // Firebase 인증 객체 초기화

        Google_login = findViewById(R.id.google_login);
        Google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
    }

    @Override
    // 구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();

                String userName = account.getDisplayName();
                String userGivenName = account.getGivenName();
                String userFamilyName = account.getFamilyName();
                String userEmail = account.getEmail();
                String userId = account.getId();
                Uri userPhoto = account.getPhotoUrl();

                Log.d(TAG_G, "handleSignInResult: userName " + userName);
                Log.d(TAG_G, "handleSignInResult: userGivenName " + userGivenName);
                Log.d(TAG_G, "handleSignInResult: userEmail " + userEmail);
                Log.d(TAG_G, "handleSignInResult: userFamilyName " + userFamilyName);
                Log.d(TAG_G, "handleSignInResult: userPhoto " + userPhoto);
                Log.d(TAG_G, "handleSignInResult: userId " + userId);

                resultLogin(account);       // 로그인 결과값 출력 수행하라는 메소드
            }
        }
    }

    // 구글 로그인 결과
    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(IntroActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            intent.putExtra("userName", account.getDisplayName());
                            intent.putExtra("userPhoto", String.valueOf(account.getPhotoUrl()));    // String.valueOf 특정 자료형을 String 형태로 변환

                            startActivity(intent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(IntroActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateKakaoLoginUi() {
        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if(user != null) {
                    // 유저의 아이디
                    Log.d(TAG_k, "invoke : id = " + user.getId());
                    // 유저의 이메일
                    Log.d(TAG_k, "invoke : e-mail = " + user.getKakaoAccount().getEmail());
                    // 유저의 닉네임
                    Log.d(TAG_k, "invoke : nickname = " + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 성별
                    Log.d(TAG_k, "invoke : gender = " + user.getKakaoAccount().getGender());
                    // 유저의 연령대
                    Log.d(TAG_k, "invoke : age = " + user.getKakaoAccount().getAgeRange());

                    // 로그인이 되어있으면 홈화면으로 이동
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG_k, "invoke: user is null");
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