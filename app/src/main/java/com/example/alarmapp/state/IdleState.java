package com.example.alarmapp.state;

import android.util.Log;

import com.example.alarmapp.activity.AlarmActivity;

/**
 * アラームが停止中である状態
 */
public class IdleState implements AlarmState {
    private static final String TAG = IdleState.class.getSimpleName();
    /**
     * このクラスのインスタンスは１つだけ
     */
    private static IdleState singleton = new IdleState();

    /**
     * コンストラクタ
     */
    private IdleState(){}

    /**
     * このクラスのインスタンスを取得する
     * @return このクラスのインスタンス
     */
    public static AlarmState getInstance() {
        return singleton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(AlarmActivity context, int sec) {
        Log.d(TAG, "start/in");
        context.startAlarm(sec);
        Log.d(TAG, "start/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(AlarmActivity context) {
        Log.d(TAG, "stop/in");
        // NOP
        Log.d(TAG, "stop/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TAG;
    }
}
