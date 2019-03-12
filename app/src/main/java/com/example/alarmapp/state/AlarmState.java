package com.example.alarmapp.state;

import com.example.alarmapp.activity.AlarmActivity;

/**
 * アラームの状態ごとの動作を定義するための型（インターフェース）
 *
 * 状態ごとに各メソッドの動作を定義することで、
 * メソッド内のif文による肥大化を防いだり、新たに状態が増えた時の変更が容易になる
 *
 * ※参考：ステートパターン
 */
public interface AlarmState {
    /**
     * アラームを開始する
     * @param context Service開始などの実際の処理を行うためのコンテキスト
     * @param sec アラーム設定秒数
     */
    void start(AlarmActivity context, int sec);

    /**
     * アラームを中止する
     * @param context Service終了などの実際の処理を行うためのコンテキスト
     */
    void stop(AlarmActivity context);

    /**
     * アラームが実行中かどうか
     * @return true:アラームは実行中である, false:アラームは実行中ではない
     */
    boolean isRunning();
}
