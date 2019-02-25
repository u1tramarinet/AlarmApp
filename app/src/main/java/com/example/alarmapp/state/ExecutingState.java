package com.example.alarmapp.state;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.alarmapp.AlarmService;
import com.example.alarmapp.R;

public class ExecutingState implements AlarmState {
    private static final String TAG = ExecutingState.class.getSimpleName();
    private static ExecutingState singleton = new ExecutingState();

    private ExecutingState() {}

    public static AlarmState getInstance() {
        return singleton;
    }

    @Override
    public void start(Context context, int sec) {
        Log.d(TAG, "start NOP now=" + singleton);
        // NOP
    }

    @Override
    public void stop(Context context) {
        Log.d(TAG, "stop/in");
        Intent intent = new Intent(context, AlarmService.class);
        context.stopService(intent);
        Log.d(TAG, "stop/out");
    }

    @Override
    public void setting(Context context) {
        Log.d(TAG, "setting/in");
        Toast.makeText(context, R.string.warning_in_running, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "setting/out");
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public String toString() {
        return TAG;
    }
}
