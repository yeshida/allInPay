package com.centerm.allinpay.launcher.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.utils.ScreenUtils;
import com.centerm.allinpay.launcher.view.GridItemView;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/6.
 */
public class TestActivity extends BaseActivity {

    private final static int PORTRAIT_COLUMNS = 2;
    private final static int LANDSCAPE_COLUMNS = 5;
    private final static int ROWS = 3;
    private final static int ITEM_SPACING = 30;
    private FrameLayout appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        appContainer = (FrameLayout) findViewById(R.id.app_container);
//        appContainer.addView(generate(100,100),new FrameLayout.LayoutParams(-1,-1));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = appContainer.getWidth();
                int height = appContainer.getHeight();
                logger.info("test", "容器宽度==" + width + "    高度==" + height);
                GridView gridView = generate(width, height);
                appContainer.addView(gridView, new FrameLayout.LayoutParams(-1, -1));
            }
        }, 2000);
    }

    private GridView generate(int spaceWidth, int spaceHeight) {
        int width = spaceWidth;
        int height = spaceHeight;
        int rows = ROWS;
        int columns = PORTRAIT_COLUMNS;
        ScreenUtils screenUtils = new ScreenUtils(this);
        if (screenUtils.isTablet()) {
            columns = LANDSCAPE_COLUMNS;
        }
        GridView gridView = new GridView(this);
        gridView.setNumColumns(columns);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setVerticalSpacing(ITEM_SPACING);
        gridView.setHorizontalSpacing(ITEM_SPACING);
        gridView.setGravity(Gravity.CENTER);
        gridView.setAdapter(new GridApapter());
        gridView.setSelector(getResources().getDrawable(R.drawable.hover));

        return gridView;

    }

    private class GridApapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = new GridItemView(TestActivity.this);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, 300);
            view.setLayoutParams(params);
            return view;
        }
    }


}
