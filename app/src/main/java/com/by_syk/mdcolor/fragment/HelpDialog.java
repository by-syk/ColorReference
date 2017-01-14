package com.by_syk.mdcolor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import com.by_syk.lib.storage.SP;
import com.by_syk.mdcolor.BaseActivity;
import com.by_syk.mdcolor.R;
import com.by_syk.mdcolor.util.C;

/**
 * Created by By_syk on 2017-01-14.
 */

public class HelpDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getString(R.string.help_desc);

        // Add underlines for words: 500, 700, A200, A400.
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
        index = message.indexOf("A400");
        spannableString.setSpan(new UnderlineSpan(), index, index + 4,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return getDialogBuilder()
                .setTitle(R.string.dlg_title_help)
                .setMessage(spannableString)
                .setPositiveButton(R.string.dlg_bt_got_it, null)
                .create();
    }

    private AlertDialog.Builder getDialogBuilder() {
        if (/*C.SDK >= 21 && */C.SDK < 23) {
            SP sp = new SP(getActivity(), false);
            if (sp.getInt(C.SP_THEME_COLOR, -1) >= 0) {
                return new AlertDialog.Builder(getActivity(),
                        BaseActivity.DIALOG_THEME_ID[sp.getInt(C.SP_THEME_STYLE)]);
            }
        }

        return new AlertDialog.Builder(getActivity());
    }
}
