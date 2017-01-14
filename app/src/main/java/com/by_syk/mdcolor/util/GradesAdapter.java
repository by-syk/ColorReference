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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by_syk.mdcolor.R;

/**
 * Created by By_syk on 2016-04-01.
 */
public class GradesAdapter extends BaseAdapter {
    private Context context = null;

    private LayoutInflater layoutInflater = null;

    private Palette palette = null;

    private static class ViewHolder {
        CardView cardView;
        TextView tvGrade;
        TextView tvHex;
        ImageView ivStar;
    }

    public GradesAdapter(Context context, Palette palette) {
        this.context = context;
        this.palette = palette;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return palette.getSize();
    }

    @Override
    public Integer getItem(int position) {
        return palette.getColor(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
         * 使用ViewHolder模式来避免没有必要的调用findViewById()：因为太多的findViewById也会影响性能
         * ViewHolder模式通过getView()方法返回的视图的标签(Tag)中存储一个数据结构，
         * 这个数据结构包含了指向我们要绑定数据的视图的引用，从而避免每次调用getView()的时候调用findViewById()
         */
        ViewHolder viewHolder;

        // 重用缓存convertView传递给getView()方法来避免填充不必要的视图
        if (convertView == null) {
            /* 避免这样使用：
             *     layoutInflater.inflate(R.layout.list_item, null);
             * 查看
             *     https://possiblemobile.com/2013/05/layout-inflation-as-intended/
             */
            convertView = layoutInflater.inflate(R.layout.list_item_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_view);
            viewHolder.tvGrade = (TextView) convertView.findViewById(R.id.tv_grade);
            viewHolder.tvHex = (TextView) convertView.findViewById(R.id.tv_hex);
            viewHolder.ivStar = (ImageView) convertView.findViewById(R.id.iv_star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cardView.setCardBackgroundColor(palette.getColor(position));

        viewHolder.tvGrade.setText(palette.getGradeName(position));
        viewHolder.tvHex.setText(palette.getColorStr(position));

        int floating_text_color = palette.getFloatingTextColor(position);
        viewHolder.tvGrade.setTextColor(floating_text_color);
        viewHolder.tvHex.setTextColor(floating_text_color);

        if (palette.isSuggestedHue(position)) {
            viewHolder.ivStar.setVisibility(View.VISIBLE);

            // Create new Drawable to avoid star color error.
            Drawable drawable = context.getDrawable(R.drawable.ic_star);
            viewHolder.ivStar.setImageDrawable(drawable);
            viewHolder.ivStar.getDrawable().setTint(floating_text_color);
        } else {
            viewHolder.ivStar.setVisibility(View.GONE);
        }

        return convertView;
    }
}