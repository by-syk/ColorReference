package com.by_syk.mdcolor.util;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.by_syk.lib.toast.GlobalToast;
import com.by_syk.mdcolor.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /*public static SpannableString getLinkableMessage(String message) {
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
    }*/

    /**
     * 实现 Markdown 链接，如：
     *     [Unicode Consortium](http://www.unicode.org)
     */
    public static SpannableString getLinkableMessage(final Context CONTEXT, String message) {
        String newMessage = message;
        final List<String> tagsList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            tagsList.add(matcher.group(1));
            tagsList.add(matcher.group(2));
            newMessage = newMessage.replaceFirst("\\[(.*?)\\]\\((.*?)\\)", matcher.group(1));
        }

        SpannableString spannableString = new SpannableString(newMessage);
        int temp_pos;
        for (int i = 0, len = tagsList.size(); i < len - 1; i += 2) {
            temp_pos = newMessage.indexOf(tagsList.get(i));
            if (tagsList.get(i + 1).startsWith("copy:")) {
                final String TEXT = tagsList.get(i + 1).substring(5);
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        copy2Clipboard(CONTEXT, TEXT);

                        GlobalToast.showToast(CONTEXT,
                                CONTEXT.getString(R.string.toast_copied, TEXT));
                    }
                }, temp_pos, temp_pos + tagsList.get(i).length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new URLSpan(tagsList.get(i + 1)),
                        temp_pos, temp_pos + tagsList.get(i).length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }

    @TargetApi(11)
    @SuppressWarnings("deprecation")
    public static void copy2Clipboard(Context context, String text) {
        if (text == null) {
            return;
        }

        if (C.SDK >= 11) {
            ClipboardManager clipboardManager = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, text);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
        }
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
