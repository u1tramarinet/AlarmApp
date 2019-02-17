package com.example.alarmapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class NotificationSettingDialogFragment extends DialogFragment {
    private static final String TAG = NotificationSettingDialogFragment.class.getSimpleName();
    private DialogActionListener mListener = null;
    private int[] mSeconds = null;
    private int mCurrentSecond;

    /**
     *
     */
    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d(TAG, "onClick/in which=" + which);
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.d(TAG, "Pressed Positive Button");
                SharedPreferencesUtil.writeInt(getContext(), SharedPreferencesUtil.KEY_NOTIFICATION_TIME, mCurrentSecond);
                mListener.onDialogPositiveClick();
            } else if (0 <= which && which < mSeconds.length) {
                Log.d(TAG, "Pressed Other Button " + mSeconds[which] + " sec.");
                mCurrentSecond = mSeconds[which];
            } else {
                Log.d(TAG, "Pressed Unknown Button");
            }
            Log.d(TAG, "onClick/out");
        }
    };

    /**
     *
     * @return
     */
    public static NotificationSettingDialogFragment newInstance() {
        Log.d(TAG, "newInstance");
        return new NotificationSettingDialogFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach/in");
        super.onAttach(context);
        try {
            mListener = (DialogActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DialogActionListener");
        }
        Log.d(TAG, "onAttach/out");
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
        initialize();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.notification_time_setting)
                .setSingleChoiceItems(getItemsFromSeconds(), getCheckedItemFromCurrentSecond(), mOnClickListener)
                .setPositiveButton(R.string.dialog_ok, mOnClickListener);
        return builder.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach/in");
        super.onDetach();
        mListener = null;
        Log.d(TAG, "onDetach/out");
    }

    /**
     *
     */
    private void initialize() {
        Log.d(TAG, "initialize/in");
        Resources res = getResources();
        mSeconds = res.getIntArray(R.array.seconds);
        int defaultValue = res.getInteger(R.integer.second_default);
        mCurrentSecond = SharedPreferencesUtil.readInt(getContext(), SharedPreferencesUtil.KEY_NOTIFICATION_TIME, defaultValue);
        Log.d(TAG, "initialize/out");
    }

    /**
     *
     * @return
     */
    private CharSequence[] getItemsFromSeconds() {
        Log.d(TAG, "getItemsFromSeconds");
        CharSequence[] array = new CharSequence[mSeconds.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = getString(R.string.second, mSeconds[i]);
        }
        return array;
    }

    /**
     *
     * @return
     */
    private int getCheckedItemFromCurrentSecond() {
        Log.d(TAG, "getCheckedItemFromCurrentSecond");
        int index = 0;
        for (int i = 0; i < mSeconds.length; i++) {
            if (mCurrentSecond == mSeconds[i]) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     *
     */
    public interface DialogActionListener {
        void onDialogPositiveClick();
    }
}
