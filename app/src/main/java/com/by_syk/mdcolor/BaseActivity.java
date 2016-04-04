package com.by_syk.mdcolor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.by_syk.mdcolor.util.C;

/**
 * Created by By_syk on 2016-03-31.
 */
public class BaseActivity extends Activity {
    SharedPreferences sharedPreferences;

    public final int[] DEFAULT_THEME_ID = { R.style.app_theme_dark,
            R.style.app_theme_light, R.style.app_theme_light_d };
    public final int[][] THEMES_ID =  {
            { R.style.app_theme_dark_red, R.style.app_theme_light_red, R.style.app_theme_light_d_red },
            { R.style.app_theme_dark_pink, R.style.app_theme_light_pink, R.style.app_theme_light_d_pink },
            { R.style.app_theme_dark_purple, R.style.app_theme_light_purple, R.style.app_theme_light_d_purple },
            { R.style.app_theme_dark_deep_purple, R.style.app_theme_light_deep_purple, R.style.app_theme_light_d_deep_purple },
            { R.style.app_theme_dark_indigo, R.style.app_theme_light_indigo, R.style.app_theme_light_d_indigo },
            { R.style.app_theme_dark_blue, R.style.app_theme_light_blue, R.style.app_theme_light_d_blue },
            { R.style.app_theme_dark_light_blue, R.style.app_theme_light_light_blue, R.style.app_theme_light_d_light_blue },
            { R.style.app_theme_dark_cyan, R.style.app_theme_light_cyan, R.style.app_theme_light_d_cyan },
            { R.style.app_theme_dark_teal, R.style.app_theme_light_teal, R.style.app_theme_light_d_teal },
            { R.style.app_theme_dark_green, R.style.app_theme_light_green, R.style.app_theme_light_d_green },
            { R.style.app_theme_dark_light_green, R.style.app_theme_light_light_green, R.style.app_theme_light_d_light_green },
            { R.style.app_theme_dark_lime, R.style.app_theme_light_lime, R.style.app_theme_light_d_lime },
            { R.style.app_theme_dark_yellow, R.style.app_theme_light_yellow, R.style.app_theme_light_d_yellow },
            { R.style.app_theme_dark_amber, R.style.app_theme_light_amber, R.style.app_theme_light_d_amber },
            { R.style.app_theme_dark_orange, R.style.app_theme_light_orange, R.style.app_theme_light_d_orange },
            { R.style.app_theme_dark_deep_orange, R.style.app_theme_light_deep_orange, R.style.app_theme_light_d_deep_orange },
            { R.style.app_theme_dark_brown, R.style.app_theme_light_brown, R.style.app_theme_light_d_brown },
            { R.style.app_theme_dark_grey, R.style.app_theme_light_grey, R.style.app_theme_light_d_grey },
            { R.style.app_theme_dark_blue_grey, R.style.app_theme_light_blue_grey, R.style.app_theme_light_d_blue_grey },
    };
    public final int[] DIALOG_THEME_ID = { R.style.dialog_theme_dark,
            R.style.dialog_theme_light, R.style.dialog_theme_light };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        int theme_color = sharedPreferences.getInt(C.SP_THEME_COLOR, -1);
        int theme_style = sharedPreferences.getInt(C.SP_THEME_STYLE, 0);
        boolean with_dark_ab = sharedPreferences.getBoolean(C.SP_WITH_DARK_AB, false);

        if (theme_color >= 0 && theme_color < THEMES_ID.length) {
            if (theme_style == 0) {
                setTheme(THEMES_ID[theme_color][0]);
            } else {
                setTheme(THEMES_ID[theme_color][with_dark_ab ? 2 : 1]);
            }
        } else {
            setTheme(DEFAULT_THEME_ID[theme_style]);
        }

        super.onCreate(savedInstanceState);
    }
}
