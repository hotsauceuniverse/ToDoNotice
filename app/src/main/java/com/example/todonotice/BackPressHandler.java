package com.example.todonotice;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

public class BackPressHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    // 생성자 작성
    public BackPressHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (isAfter2Seconds()) {
            backKeyPressedTime = System.currentTimeMillis();
            // 현재시간을 다시 초기화

            toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (isBefore2Seconds()) {
            // 현재 액티비티가 HomeActivity인 경우에만 앱 종료
            if (activity instanceof MainActivity) {
                appShutdown();
            } else {
                activity.finish();
            }
            toast.cancel();
        }
    }

    private Boolean isAfter2Seconds() {
        return System.currentTimeMillis() > backKeyPressedTime + 2000;
        // 2초 지났을 경우
    }

    private Boolean isBefore2Seconds() {
        return System.currentTimeMillis() <= backKeyPressedTime + 2000;
        // 2초가 지나지 않았을 경우
    }

    private void appShutdown() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAndRemoveTask(); // Activity를 종료하고 최근 앱 사용 목록에서도 해당 앱을 제거
        } else {
            activity.finish();
        }

        System.runFinalization(); // 작업중인 쓰레드가 다 종료되면, 종료 시키라는 명령어
        System.exit(0); // 현재 Activity 종료
    }
}

