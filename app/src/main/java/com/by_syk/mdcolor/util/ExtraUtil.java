/**
 * Copyright 2016-2017 By_syk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
