package com.centerm.allinpay.launcher.view;

import com.centerm.allinpay.launcher.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MessageAlertDialog extends AlertDialog {

	private Activity activity;
	private TextView messageShow;

	public MessageAlertDialog(Activity activity) {
		super(activity);
		this.activity = activity;
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.common_message_alert_dialog_content_view, null);
		messageShow = (TextView) view.findViewById(R.id.message_show);
		setContent(view);
	}

	public void setMessage(String message) {
		messageShow.setText(message);
	}
}
