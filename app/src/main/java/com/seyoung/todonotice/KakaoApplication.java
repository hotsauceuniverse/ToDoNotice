package com.seyoung.todonotice;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "cd08a7486a17588a9873dd2f67c7974a");
    }
}
