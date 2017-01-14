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

package com.by_syk.mdcolor;

import android.app.Activity;
import android.os.Bundle;

import com.by_syk.lib.storage.SP;
import com.by_syk.mdcolor.util.C;

/**
 * Created by By_syk on 2016-03-31.
 */
public class BaseActivity extends Activity {
    protected SP sp = null;

    public static final int[] DEFAULT_THEME_ID = {R.style.AppThemeDark,
            R.style.AppThemeLight, R.style.AppThemeLightD};
    public static final int[][] THEMES_ID = {
            {R.style.AppThemeDark_Red, R.style.AppThemeLight_Red, R.style.AppThemeLightD_Red},
            {R.style.AppThemeDark_Pink, R.style.AppThemeLight_Pink, R.style.AppThemeLightD_Pink},
            {R.style.AppThemeDark_Purple, R.style.AppThemeLight_Purple, R.style.AppThemeLightD_Purple},
            {R.style.AppThemeDark_DeepPurple, R.style.AppThemeLight_DeepPurple, R.style.AppThemeLightD_DeepPurple},
            {R.style.AppThemeDark_Indigo, R.style.AppThemeLight_Indigo, R.style.AppThemeLightD_Indigo},
            {R.style.AppThemeDark_Blue, R.style.AppThemeLight_Blue, R.style.AppThemeLightD_Blue},
            {R.style.AppThemeDark_LightBlue, R.style.AppThemeLight_LightBlue, R.style.AppThemeLightD_LightBlue},
            {R.style.AppThemeDark_Cyan, R.style.AppThemeLight_Cyan, R.style.AppThemeLightD_Cyan},
            {R.style.AppThemeDark_Teal, R.style.AppThemeLight_Teal, R.style.AppThemeLightD_Teal},
            {R.style.AppThemeDark_Green, R.style.AppThemeLight_Green, R.style.AppThemeLightD_Green},
            {R.style.AppThemeDark_LightGreen, R.style.AppThemeLight_LightGreen, R.style.AppThemeLightD_LightGreen},
            {R.style.AppThemeDark_Lime, R.style.AppThemeLight_Lime, R.style.AppThemeLightD_Lime},
            {R.style.AppThemeDark_Yellow, R.style.AppThemeLight_Yellow, R.style.AppThemeLightD_Yellow},
            {R.style.AppThemeDark_Amber, R.style.AppThemeLight_Amber, R.style.AppThemeLightD_Amber},
            {R.style.AppThemeDark_Orange, R.style.AppThemeLight_Orange, R.style.AppThemeLightD_Orange},
            {R.style.AppThemeDark_DeepOrange, R.style.AppThemeLight_DeepOrange, R.style.AppThemeLightD_DeepOrange},
            {R.style.AppThemeDark_Brown, R.style.AppThemeLight_Brown, R.style.AppThemeLightD_Brown},
            {R.style.AppThemeDark_Grey, R.style.AppThemeLight_Grey, R.style.AppThemeLightD_Grey},
            {R.style.AppThemeDark_BlueGrey, R.style.AppThemeLight_BlueGrey, R.style.AppThemeLightD_BlueGrey},
    };
    public static final int[] DIALOG_THEME_ID = {R.style.DialogThemeDark,
            R.style.DialogThemeLight, R.style.DialogThemeLight};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = new SP(this, false);

        int themeColor = sp.getInt(C.SP_THEME_COLOR, -1);
        int themeStyle = sp.getInt(C.SP_THEME_STYLE);
        boolean withDarkAb = sp.getBoolean(C.SP_WITH_DARK_AB);

        if (themeColor >= 0 && themeColor < THEMES_ID.length) {
            if (themeStyle == 0) {
                setTheme(THEMES_ID[themeColor][0]);
            } else {
                setTheme(THEMES_ID[themeColor][withDarkAb ? 2 : 1]);
            }
        } else {
            setTheme(DEFAULT_THEME_ID[themeStyle]);
        }

        super.onCreate(savedInstanceState);
    }
}
