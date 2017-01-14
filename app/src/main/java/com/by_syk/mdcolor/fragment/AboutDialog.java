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

package com.by_syk.mdcolor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.by_syk.lib.storage.SP;
import com.by_syk.lib.text.AboutMsgRender;
import com.by_syk.mdcolor.BaseActivity;
import com.by_syk.mdcolor.R;
import com.by_syk.mdcolor.util.C;
import com.by_syk.mdcolor.util.ExtraUtil;

/**
 * Created by By_syk on 2017-01-14.
 */

public class AboutDialog extends DialogFragment {
    private boolean isExecuted = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SpannableString message = AboutMsgRender.render(getActivity(), getString(R.string.about_desc));

        return getDialogBuilder()
                .setTitle(R.string.dlg_title_about)
                .setMessage(message)
                .setPositiveButton(R.string.dlg_bt_ok, null)
                .setNegativeButton(R.string.dlg_bt_rate_me, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ExtraUtil.gotoMarket(getActivity(), false);
                    }
                })
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isExecuted) {
            isExecuted = true;

            // Make the url text clickable.
            TextView tvMessage = (TextView) getDialog().findViewById(android.R.id.message);
            if (tvMessage != null) {
                tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
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
