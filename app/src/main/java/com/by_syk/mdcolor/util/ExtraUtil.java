package com.by_syk.mdcolor.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;

/**
 * Created by By_syk on 2016-03-31.
 */
public class ExtraUtil {
    /**
     * @param message 包含空格隔开或独立成行的完整链接，如：
     *                ……
     *                https://github.com/kenglxn/GRGen
     *                ……
     */
    public static SpannableString getLinkableDialogMessage(String message) {
        SpannableString spannableString = new SpannableString(message);

        int temp_pos;
        int temp_end = 0;
        int temp_end2;
        while (temp_end < message.length()) {
            temp_pos = message.indexOf("http", temp_end);
            if (temp_pos < 0) {
                break;
            }

            temp_end = message.indexOf(" ", temp_pos);
            temp_end2 = message.indexOf("\n", temp_pos);
            if (temp_end2 > 0 && temp_end2 < temp_end) {
                temp_end = temp_end2;
            }

            if (temp_end <= 0) {
                temp_end = message.length();
            }
            spannableString.setSpan(new URLSpan(message.substring(temp_pos, temp_end)),
                    temp_pos, temp_end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    /**
     * Go to app market to view details of this app.
     *
     * @param via_browser Whether view via browser or client app.
     */
    public static void gotoMarket(Context context, boolean via_browser) {
        String packageName = context.getPackageName();

        final String LINK = String.format((via_browser
                ? "https://play.google.com/store/apps/details?id=%s"
                : "market://details?id=%s"), packageName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(LINK));

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            if (!via_browser) {
                gotoMarket(context, true);
            }
        }
    }
}
