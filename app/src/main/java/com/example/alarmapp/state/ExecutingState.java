package com.example.alarmapp.state;

import android.util.Log;

import com.example.alarmapp.activity.AlarmActivity;

/**
 * アラームが実行中である状態
 */
public class ExecutingState implements AlarmState {
    private static final String TAG = ExecutingState.class.getSimpleName();
    /**
     * このクラスのインスタンスは１つだけ
     */
    private static ExecutingState singleton = new ExecutingState();

    /**
     * このクラスのインスタンスを２つ以上生成させないため、コンストラクタはprivate指定
     * ※参考：シングルトンパターン
     */
    private ExecutingState() {}

    /**
     * このクラスのインスタンスを取得する
     * @return このクラスのインスタンス
     *
     * 他の状態を表すクラスと同列に扱えるように、返り値の型は親クラス
     * ※参考：ポリモーフィズム
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
        // NOP
        Log.d(TAG, "start/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(AlarmActivity context) {
        Log.d(TAG, "stop/in");
        context.stopAlarm();
        Log.d(TAG, "stop/out");
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
