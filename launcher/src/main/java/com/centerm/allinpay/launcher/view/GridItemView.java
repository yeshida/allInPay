package com.centerm.allinpay.launcher.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.download.DownloadStatusObserver;

import org.json.JSONObject;

import java.io.File;


/**
 * Created by linwanliang on 2016/3/4.
 */
public class GridItemView extends FrameLayout {


    private final static int[] COLOR_SETS = new int[]{0xccff99ff, 0xe6ff6600, 0x7f6798fb, 0xcc7cff20,
            0xffffd200, 0xe67dfb22, 0xccfa0afa, 0xcc97cb3e, 0xe600ccff};

    private ImageView iconView;
    private TextView hoverView;
    private TextView textView;

    private DownloadStatusObserver statusObserver;

    public GridItemView(Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        this.setBackgroundColor(getRandomColor());
        iconView = new ImageView(context);
        iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //match_parent,match_parent
        this.addView(iconView, new FrameLayout.LayoutParams(-1, -1));

        textView = new TextView(context);
        float textSize = context.getResources().getDimension(R.dimen.font_small);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        textView.setSingleLine(true);
        textView.setPadding(10, 10, 10, 10);
        textView.setTextColor(Color.WHITE);
        //wrap_content,wrap_content
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.BOTTOM;
        this.addView(textView, params);


        hoverView = new TextView(context);
        hoverView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        hoverView.setGravity(Gravity.CENTER);
        hoverView.setTextColor(Color.WHITE);
        hoverView.setBackgroundResource(R.drawable.hover);
        hoverView.setVisibility(View.GONE);
        //match_parent,match_parent
        this.addView(hoverView, new FrameLayout.LayoutParams(-1, -1));
    }


    private int getRandomColor() {
        int counts = COLOR_SETS.length;
        int random = (int) (Math.random() * counts);
        return COLOR_SETS[random];
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getTextView() {
        return textView;
    }


    /**
     * 显示APP的当前状态，例如正在下载、暂停、正在解压
     *
     * @param status   状态描述
     * @param progress 进度值，如果为负数，则不显示出来。
     */
    public void showStatus(String status, int progress) {
        if (hoverView.getVisibility() != View.VISIBLE) {
            hoverView.setVisibility(View.VISIBLE);
        }
        StringBuilder stringBuilder = new StringBuilder(status);
        if (progress >= 0) {
            stringBuilder.append("\n已下载" + progress + "%");
        }
        hoverView.setText(stringBuilder.toString());
    }

    /**
     * 清空状态描述并且隐藏图层。
     */
    public void hideStatus() {
        hoverView.setText("");
        hoverView.setVisibility(View.GONE);
    }

    public DownloadStatusObserver getStatusObserver() {
        return statusObserver;
    }

    public void setStatusObserver(DownloadStatusObserver statusObserver) {
        this.statusObserver = statusObserver;
    }
}
