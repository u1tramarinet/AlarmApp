package com.example.alarmapp.state;

import android.content.Context;
import android.util.Log;

public class ExecutingState implements AlarmState {
    private static final String TAG = ExecutingState.class.getSimpleName();
    private static ExecutingState singleton = new ExecutingState();

    private ExecutingState() {}

    public static AlarmState getInstance() {
        return singleton;
    }

    @Override
    public void start(Context context, int sec) {
        Log.d(TAG, "start/in");
        // NOP
        Log.d(TAG, "start/out");
    }

    @Override
    public void stop(Context context) {
        Log.d(TAG, "stop/in");
        Log.d(TAG, "stop/out");
    }

    @Override
    public String toString() {
        return TAG;
    }
}
