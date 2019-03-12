package com.example.alarmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * シェアードプリファレンスを扱うためのUtilクラス
 */
public class SharedPreferencesUtil {
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();
    /**
     * シェアードプリファレンスを識別
     */
    private static final String SHARED_PREFERENCES_NAME = "setting";
    /**
     * 通知時間を書き込み/読み出しのためのキー
     */
    public static final String KEY_NOTIFICATION_TIME = "notification_time";

    /**
     * 整数を書き込む
     * @param context コンテキスト
     * @param key その整数のキー
     * @param value 保存する整数
     * @return 処理結果
     */
    public static boolean writeInt(Context context, String key, int value) {
        Log.d(TAG, "writeInt/in key=" + key + ", value=" + value);
        // 引数チェック
        if (context == null) {
            Log.d(TAG, "writeInt/out Context is null.");
            return false;
        } else if (key == null || key.equals("")) {
            Log.d(TAG, "writeInt/out Key is null or empty");
            return false;
        }

        // データ書き込み
        SharedPreferences setting = getSetting(context);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(key, value);
        // commitは同期（結果あり）, applyは非同期（結果なし）
        boolean bRet = editor.commit();
        Log.d(TAG, "writeInt/out result=" + bRet);
        return bRet;
    }

    /**
     * 整数を読み出す
     * @param context コンテキスト
     * @param key 整数を書き込んだ時に指定したキー
     * @param defaultValue 読み出し失敗or書き込んだことがない場合に返す整数
     * @return 読み出した整数
     */
    public static int readInt(Context context, String key, int defaultValue) {
        Log.d(TAG, "readInt/in key=" + key);
        // 引数チェック
        if (context == null) {
            Log.d(TAG, "readInt/out Context is null.");
            return defaultValue;
        } else if (key == null || key.equals("")) {
            Log.d(TAG, "readInt/out Key is null or empty");
            return defaultValue;
        }

        // データ読み込み
        SharedPreferences setting = getSetting(context);
        // 未書き込みだったらdefaultValueが返ってくる
        int value = setting.getInt(key, defaultValue);
        Log.d(TAG, "readInt/out result=" + value);
        return value;
    }

    /**
     * 専用のSharedPreferencesを取得する
     * @param context コンテキスト
     * @return SharedPreferences
     */
    private static SharedPreferences getSetting(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
