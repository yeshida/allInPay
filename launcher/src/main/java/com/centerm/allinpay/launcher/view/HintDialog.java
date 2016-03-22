package com.centerm.allinpay.launcher.view;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.utils.ScreenUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class HintDialog extends Dialog {

	private boolean autoDismissFlag = false;
	private Activity activity;
	private TextView messageShow;

	public HintDialog(Activity activity) {
		super(activity, R.style.CustomeDialog);
		this.activity = activity;
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.common_message_alert_dialog_content_view, null);
		messageShow = (TextView) view.findViewById(R.id.message_show);
		setContentView(view);
		this.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
					return true;
				}
				return false;
			}
		});
	}

	public void setHint(String hint) {
		messageShow.setText(hint);
	}

	@Override
	public void show() {
		super.show();
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		ScreenUtils screenUtils = new ScreenUtils(activity);
		int width = screenUtils.getWidth();
		int height = screenUtils.getHeight();
		int ref = width < height ? width : height;
		int dialogWidth = (int) (ref * 0.7);
		params.width = dialogWidth;
		this.getWindow().setAttributes(params);
		if (autoDismissFlag) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					HintDialog.this.dismiss();
				}
			}, 1500);
		}
	}

	public void setAutoDismiss(boolean isDismiss) {
		this.autoDismissFlag = isDismiss;
	}
}
