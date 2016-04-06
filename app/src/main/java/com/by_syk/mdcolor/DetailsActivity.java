package com.by_syk.mdcolor;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.by_syk.mdcolor.util.C;
import com.by_syk.mdcolor.util.GlobalToast;
import com.by_syk.mdcolor.util.GradesAdapter;
import com.by_syk.mdcolor.util.Palette;

/**
 * Created by By_syk on 2016-04-01.
 */
public class DetailsActivity extends BaseActivity {
    private ListView lvGrades;

    private GradesAdapter gradesAdapter = null;
    private Palette palette = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

        // Tell users how to copy color values.
        // Just once.
        copyToast();
    }

    private void init() {
        Intent intent = getIntent();
        palette = (Palette) intent.getSerializableExtra("palette");

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(palette.getName());

        lvGrades = (ListView) findViewById(R.id.lv_grades);

        gradesAdapter = new GradesAdapter(this, palette);
        lvGrades.setAdapter(gradesAdapter);

        lvGrades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                copy2Clipboard(palette.getColorStr(position));

                // Do not show again.
                sharedPreferences.edit().putBoolean("toast_copy_color", false).apply();
            }
        });
    }

    private void copyToast() {
        if (sharedPreferences.getBoolean("toast_copy_color", true)) {
            GlobalToast.showToast(this, R.string.toast_tap_card_to_copy);

            //sharedPreferences.edit().putBoolean("toast_view_details", false).apply();
        }
    }

    private void copy2Clipboard(String text) {
        if (text == null) {
            return;
        }

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));

        GlobalToast.showToast(DetailsActivity.this, getString(R.string.toast_copied, text));
    }

    private void helpDialog() {
        String message = getString(R.string.help_desc);

        // Add underlines for words: 500, 700, A200.
        SpannableString spannableString = new SpannableString(message);
        int index = message.indexOf("500");
        //new ForegroundColorSpan(palette.getColor(500))
        spannableString.setSpan(new UnderlineSpan(), index, index + 3,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        index = message.indexOf("700");
        spannableString.setSpan(new UnderlineSpan(), index, index + 3,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        index = message.indexOf("A200");
        spannableString.setSpan(new UnderlineSpan(), index, index + 4,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        AlertDialog alertDialog = getDialogBuilder()
                .setTitle(R.string.dia_title_help)
                .setMessage(spannableString)
                .setPositiveButton(R.string.dia_bt_got_it, null)
                .create();
        alertDialog.show();
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
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_help:
                helpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
