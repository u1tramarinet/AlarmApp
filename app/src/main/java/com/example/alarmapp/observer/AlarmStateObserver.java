package com.example.alarmapp.observer;

import com.example.alarmapp.state.AlarmState;

/**
 * アラームの状態の更新受け取るオブザーバ
 *
 * このインターフェースを継承し、オブザーバリスト（今回はAlarmApplication）に登録することで受け取ることができる
 *
 * ※参考：オブザーバパターン
 */
public interface AlarmStateObserver {
    /**
     * アラームの状態の更新を受け取る
     * @param state アラームの状態を表すオブジェクト
     */
    void update(AlarmState state);
}
