package com.by_syk.mdcolor.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by By_syk on 2016-03-31.
 */
public class ExtraUtil {
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
