package com.example.alarmapp.state;

import android.content.Context;

public interface AlarmState {
    void start(Context context, int sec);
    void stop(Context context);
}
