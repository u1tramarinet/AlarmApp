package com.example.alarmapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alarmapp.AlarmService;

/**
 * アラームの処理をまとめたActivity
 */
public abstract class AlarmActivity extends AppCompatActivity {
    private static final String TAG = AlarmActivity.class.getSimpleName();

    /**
     * アラームを開始する
     * @param sec 指定秒数
     */
    public void startAlarm(int sec) {
        Log.d(TAG, "startAlarm/in sec=" + sec);
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra(AlarmService.KEY_ALARM_SECOND, sec);
        startService(intent);
        Log.d(TAG, "startAlarm/out");
    }

    /**
     * アラームを停止する
     */
    public void stopAlarm() {
        Log.d(TAG, "stopAlarm/in");
        Intent intent = new Intent(this, AlarmService.class);
        stopService(intent);
        Log.d(TAG, "stopAlarm/out");
    }
}
