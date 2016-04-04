package com.by_syk.mdcolor;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.by_syk.mdcolor.util.C;
import com.by_syk.mdcolor.util.ExtraUtil;
import com.by_syk.mdcolor.util.GlobalToast;
import com.by_syk.mdcolor.util.MyAdapter;
import com.by_syk.mdcolor.util.Palette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {
    ListView lvColors;

    private MyAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        (new LoadColorsTask()).execute();
    }

    private void init() {
        //Log.d("MainActivity", "init()");

        lvColors = (ListView) findViewById(R.id.lv_colors);

        myAdapter = new MyAdapter(this, sharedPreferences.getInt(C.SP_THEME_COLOR, -1));
        lvColors.setAdapter(myAdapter);

        //lvColors.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == sharedPreferences.getInt(C.SP_THEME_COLOR, -1)) {
                    gotoDetails(position);
                    return;
                }

                // 提示双击查看详情
                viewDetailsToast();

                sharedPreferences.edit().putInt(C.SP_THEME_COLOR, position)
                        .putBoolean(C.SP_WITH_DARK_AB,
                                myAdapter.getItem(position).isLightThemeWithDarkABSuggested())
                        .putBoolean(C.SP_SMOOTH_SCROLL, false)
                        .apply();

                changeTheme();
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_themes);
        radioGroup.check(switchRadioButtonOrderAndId(sharedPreferences.getInt(C.SP_THEME_STYLE, 0)));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int theme_style = switchRadioButtonOrderAndId(checkedId);
                if (theme_style == -1) {
                    return;
                }

                sharedPreferences.edit().putInt(C.SP_THEME_STYLE, theme_style).apply();

                changeTheme();
            }
        });

        initFAB();
    }

    private void initFAB() {
        ImageButton fabLucky = (ImageButton) findViewById(R.id.fab_lucky);

        fabLucky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int lucky_one;
                do {
                    lucky_one = random.nextInt(myAdapter.getCount());
                } while (lucky_one == myAdapter.getChecked());

                sharedPreferences.edit()
                        .putInt(C.SP_THEME_COLOR, lucky_one)
                        .putBoolean(C.SP_WITH_DARK_AB, myAdapter.getItem(lucky_one)
                                .isLightThemeWithDarkABSuggested())
                        .putBoolean(C.SP_SMOOTH_SCROLL, true)
                        .apply();

                GlobalToast.showToast(MainActivity.this, getString(R.string.toast_lucky_color,
                        myAdapter.getItem(lucky_one).getName()));

                changeTheme();
            }
        });

        fabLucky.setVisibility(View.VISIBLE);
        fabLucky.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_bottom_in));
    }

    private void gotoDetails(int which) {
        // 不再提示双击查看详情
        sharedPreferences.edit().putBoolean("toast_view_details", false).apply();

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("palette", myAdapter.getItem(which));

        startActivity(intent);
    }

    private void viewDetailsToast() {
        if (sharedPreferences.getBoolean("toast_view_details", true)) {
            GlobalToast.showToast(this, R.string.toast_tap_again_details);

            //sharedPreferences.edit().putBoolean("toast_view_details", false).apply();
        }
    }

    private int switchRadioButtonOrderAndId(int i) {
        switch (i) {
            case 0:
                return R.id.rb_dark_theme;
            case 1:
                return R.id.rb_light_theme;
            case R.id.rb_dark_theme:
                return 0;
            case R.id.rb_light_theme:
                return 1;
            //case R.id.rb_light_with_dark_theme:
            //    return 2;
            default:
                return -1;
        }
    }

    private class LoadColorsTask extends AsyncTask<String, Integer, List<Palette>> {

        @Override
        protected List<Palette> doInBackground(String... params) {
            return loadColors();
        }

        @Override
        protected void onPostExecute(List<Palette> dataList) {
            super.onPostExecute(dataList);

            myAdapter.notifyRefresh(dataList);
            //myAdapter.notifyDataSetChanged();

            if (sharedPreferences.getBoolean(C.SP_SMOOTH_SCROLL, false)) {
                lvColors.smoothScrollToPosition(sharedPreferences.getInt(C.SP_THEME_COLOR, 0));
            } else {
                lvColors.setSelection(sharedPreferences.getInt(C.SP_THEME_COLOR, 0));
            }
        }
    }

    private List<Palette> loadColors() {
        List<Palette> dataList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources()
                .openRawResource(R.raw.palette)));

        Palette palette;
        String tempStr;
        try {
            while ((tempStr = bufferedReader.readLine()) != null) {
                if (tempStr.startsWith("#")) {
                    continue;
                }

                palette = new Palette();
                boolean is_ok = palette.setAll(tempStr);
                if (!is_ok) {
                    continue;
                }

                dataList.add(palette);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private void changeTheme() {
        recreate();
        //reload();
    }

    /*private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }*/

    private void aboutDialog() {
        SpannableString message = ExtraUtil
                .getLinkableDialogMessage(getString(R.string.about_desc));

        AlertDialog alertDialog = getDialogBuilder()
                .setTitle(R.string.dia_title_about)
                .setMessage(message)
                .setPositiveButton(R.string.dia_bt_ok, null)
                .create();
        alertDialog.show();

        // 使内容中的链接可以被点击
        ((TextView) alertDialog.findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }

    private AlertDialog.Builder getDialogBuilder() {
        if (/*C.SDK >= 21 && */C.SDK < 23) {
            if (sharedPreferences.getInt(C.SP_THEME_COLOR, -1) >= 0) {
                return new AlertDialog.Builder(this,
                        DIALOG_THEME_ID[sharedPreferences.getInt(C.SP_THEME_STYLE, 0)]);
            }
        }

        return new AlertDialog.Builder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset: {
                sharedPreferences.edit().remove(C.SP_THEME_COLOR).apply();

                GlobalToast.showToast(this, R.string.toast_reset);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((RadioGroup) findViewById(R.id.rg_themes)).clearCheck();
                        ((RadioButton) findViewById(R.id.rb_dark_theme)).setChecked(true);
                    }
                }, 400);

                return true;
            }
            case R.id.menu_about:
                aboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
