package com.centerm.allinpay.launcher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.view.GridItemView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by linwanliang on 2016/3/7.
 */
public class GridViewAdapter extends BaseAdapter {
    private DisplayImageOptions imageOptions;
    private Context context;
    private List<JSONObject> data;
    private int itemHeight;


    public GridViewAdapter(Context context, List<JSONObject> appList, int itemHeight) {
        this.context = context;
        this.data = appList;
        this.itemHeight = itemHeight;
        imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new GridItemView(context);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, itemHeight);
            convertView.setLayoutParams(params);
        }
        GridItemView view = (GridItemView) convertView;
        //每个view携带着自己的数据集合
        //很重要
        view.setTag(data.get(position));
        try {
            JSONObject itemData = data.get(position);
            String appName = itemData.getString(Key.name);
            String iconUrl = itemData.getString(Key.BigIcon);
            view.getTextView().setText(appName);
            ImageLoader.getInstance().displayImage(iconUrl, view.getIconView(), imageOptions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
