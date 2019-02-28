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

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Context context, int sec) {
        Log.d(TAG, "start NOP now=" + singleton);
        // NOP
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(Context context) {
        Log.d(TAG, "stop/in");
        Intent intent = new Intent(context, AlarmService.class);
        context.stopService(intent);
        Log.d(TAG, "stop/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canOpenSetting() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TAG;
    }
}
