package com.centerm.allinpay.launcher.view;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.utils.ScreenUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AlertDialog extends Dialog {
    private Activity activity;
    private FrameLayout contentContainer;
    private Button negativeButton, positiveButton;

    private ClickListener listener;

    private View content;
    private ViewGroup titleContainer;
    private TextView titleShow;

    public AlertDialog(Activity activity) {
        super(activity, R.style.CustomeDialog);
        this.activity = activity;
        init();
    }

    private void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.common_alert_dialog_layout, null);
        contentContainer = (FrameLayout) view.findViewById(R.id.content_view_container);
        titleContainer = (ViewGroup) view.findViewById(R.id.title_container);
        titleShow = (TextView) view.findViewById(R.id.dialog_title_show);
        negativeButton = (Button) view.findViewById(R.id.negative_btn);
        positiveButton = (Button) view.findViewById(R.id.positive_btn);
        negativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.this.dismiss();
                if (listener != null) {
                    listener.onNegativeClick(v);
                }
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.this.dismiss();
                if (listener != null) {
                    listener.onPositiveClick(v);
                }
            }
        });
        this.setContentView(view);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_HOME) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setContent(View content) {
        this.content = content;
        contentContainer.addView(content);
    }

    public View getContent() {
        return content;
    }

    public ClickListener getClickListener() {
        return listener;
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onPositiveClick(View v);

        void onNegativeClick(View v);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        ScreenUtils screenUtils = new ScreenUtils(activity);
        int width = screenUtils.getWidth();
        int heihgt = screenUtils.getHeight();
        int ref = width < heihgt ? width : heihgt;
        int dialogWidth = (int) (ref * 0.7);
        params.width = dialogWidth;
        this.getWindow().setAttributes(params);
    }

    public void hideNegativeButton() {
        negativeButton.setVisibility(View.GONE);
    }

    public void hidePositiveButton() {
        positiveButton.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        titleContainer.setVisibility(View.VISIBLE);
        titleShow.setText(title);
    }

    public void hideTitle() {
        titleContainer.setVisibility(View.GONE);
    }

}
