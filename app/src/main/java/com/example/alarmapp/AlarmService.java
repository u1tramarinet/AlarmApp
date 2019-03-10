package com.example.alarmapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * アラームのサービス
 */
public class AlarmService extends Service {
    private static final String TAG = AlarmService.class.getSimpleName();
    /**
     * アラーム設定秒数をIntent経由で受け取るためのキー
     */
    public static final String KEY_ALARM_SECOND = "alarm_second";
    /**
     * アラーム設定秒数
     */
    private int mAlarmSecond;
    /**
     * ハンドラー
     */
    private Handler mHandler = new Handler();
    /**
     * アラームの完了処理を行うランナブル
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            startDialogActivity();
        }
    };

    /**
     * コンストラクタ
     */
    public AlarmService() {
        // NOP
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate/in");
        super.onCreate();
        Log.d(TAG, "onCreate/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        mAlarmSecond = intent.getIntExtra(KEY_ALARM_SECOND, -1);
        if (mAlarmSecond < 0) {
            stopSelf();
            return START_NOT_STICKY;
        }

        mHandler.postDelayed(mRunnable, mAlarmSecond * 1000);
        return START_NOT_STICKY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        // NOP
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy/in");
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        Log.d(TAG, "onDestroy/out");
    }

    /**
     * アラーム完了時のダイアログ（Activity）表示を行う
     */
    private void startDialogActivity() {
        Log.d(TAG, "startDialogActivity/in");
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra(KEY_ALARM_SECOND, mAlarmSecond);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Log.d(TAG, "startDialogActivity/out");
    }
}
