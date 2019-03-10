package com.example.alarmapp.state;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.alarmapp.AlarmService;
import com.example.alarmapp.R;

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
