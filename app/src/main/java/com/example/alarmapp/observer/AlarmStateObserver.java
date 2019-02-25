package com.example.alarmapp.observer;

import com.example.alarmapp.state.AlarmState;

public interface AlarmStateObserver {
    void update(AlarmState state);
}
