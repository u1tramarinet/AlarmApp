package com.example.alarmapp.state;

import android.content.Context;
import android.util.Log;

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
        Log.d(TAG, "start/out");
    }

    @Override
    public void stop(Context context) {
        Log.d(TAG, "stop/in");
        // NOP
        Log.d(TAG, "stop/out");
    }

    @Override
    public String toString() {
        return TAG;
    }
}
