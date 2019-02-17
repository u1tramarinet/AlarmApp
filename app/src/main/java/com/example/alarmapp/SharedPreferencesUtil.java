package com.example.alarmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesUtil {
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();
    private static final String SHARED_PREFERENCES_NAME = "setting_test";
    static final String KEY_NOTIFICATION_TIME = "notification_time";

    /**
     *
     * @param context
     * @param key
     * @param value
     * @return
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
        editor.putInt(KEY_NOTIFICATION_TIME, value);
        // commitは同期（結果あり）。 applyは非同期（結果なし）。
        boolean bRet = editor.commit();
        Log.d(TAG, "writeInt/out result=" + bRet);
        return bRet;
    }

    /**
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
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
        int value = setting.getInt(KEY_NOTIFICATION_TIME, defaultValue);
        Log.d(TAG, "readInt/out result=" + value);
        return value;
    }

    /**
     *
     * @param context
     * @return
     */
    private static SharedPreferences getSetting(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
