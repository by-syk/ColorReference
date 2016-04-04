package com.by_syk.mdcolor.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by_syk.mdcolor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by By_syk on 2016-03-31.
 */
public class MyAdapter extends BaseAdapter {
    private Context context = null;
    private LayoutInflater layoutInflater = null;

    private List<Palette> dataList = null;

    private int checked = -1;

    static class ViewHolder {
        public TextView tvName;
        public TextView tvHex;
        public ImageView ivHead;
    }

    public MyAdapter(Context context, int checked) {
        this.context = context;
        this.checked = checked;

        layoutInflater = LayoutInflater.from(context);

        /*
         * 不直接引用传入的List对象：
         * this.dataList = dataList;
         * 避免不可控的数据变更导致崩溃：
         * java.lang.IllegalStateException:
         *     The content of the adapter has changed but ListView did not receive a notification.
         * 因此采用复制一份数据的方案，完全由该Adapter对象维护。
         */
        dataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Palette getItem(int position) {
        return dataList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvHex = (TextView) convertView.findViewById(R.id.tv_hex);
            viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.iv_head);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Palette palette = dataList.get(position);

        viewHolder.tvName.setText(palette.getName());
        viewHolder.tvHex.setText(palette.getPrimaryColorStr());

        Drawable drawable = context.getDrawable(position == checked
                ? R.drawable.ic_head_checked : R.drawable.ic_head);
        viewHolder.ivHead.setImageDrawable(drawable);
        viewHolder.ivHead.getDrawable().setTint(palette.getPrimaryColor());
        //viewHolder.ivHead.setColorFilter(palette.getPrimaryColor());

        return convertView;
    }

    /*public Palette getCheckedItem() {
        if (checked < 0 || checked >= dataList.size()) {
            return null;
        }

        return dataList.get(checked);
    }*/

    public int getChecked() {
        return checked;
    }

    public void notifyRefresh(List<Palette> dataList) {
        if (dataList == null) {
            return;
        }

        this.dataList.clear();
        this.dataList.addAll(dataList);

        notifyDataSetChanged();
    }

    /*public void notifyRefreshChecked(int checked) {
        this.checked = checked;

        notifyDataSetChanged();
    }*/
}