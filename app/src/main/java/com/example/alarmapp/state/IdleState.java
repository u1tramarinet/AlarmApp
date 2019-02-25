package com.example.alarmapp.state;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.alarmapp.AlarmService;
import com.example.alarmapp.NotificationSettingDialogFragment;

public class IdleState implements AlarmState {
    private static final String TAG = IdleState.class.getSimpleName();
    private static IdleState singleton = new IdleState();

    private IdleState(){}

    public static AlarmState getInstance() {
        return singleton;
    }

    @Override
    public void start(Context context, int sec) {
        Log.d(TAG, "start/in");
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(AlarmService.KEY_ALARM_SECOND, sec);
        context.startService(intent);
        Log.d(TAG, "start/out");
    }

    @Override
    public void stop(Context context) {
        Log.d(TAG, "stop NOP now=" + singleton);
        // NOP
    }

    @Override
    public void setting(Context context) {
        Log.d(TAG, "setting/in");
        Log.d(TAG, "setting/out");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public String toString() {
        return TAG;
    }
}
