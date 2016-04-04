package com.by_syk.mdcolor.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by By_syk on 2016-03-11.
 */
public class GlobalToast {
    private static Toast toast = null;

    public static void showToast(Context context, String message, boolean is_long) {
        if (message == null) {
            return;
        }

        if (toast == null) { // 首次使用Toast
            toast = Toast.makeText(context.getApplicationContext(), message,
                    is_long ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(is_long ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setText(message);
        }
        toast.show();
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, false);
    }

    public static void showToast(Context context, int str_id, boolean is_long) {
        showToast(context, context.getString(str_id), is_long);
    }

    public static void showToast(Context context, int str_id) {
        showToast(context, context.getString(str_id), false);
    }
}