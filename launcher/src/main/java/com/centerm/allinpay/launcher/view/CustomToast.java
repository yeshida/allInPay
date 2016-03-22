package com.centerm.allinpay.launcher.view;

import com.centerm.allinpay.launcher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {

	private Context context;
	private TextView contentShow;

	public CustomToast(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.common_custom_toast_layout, null);
		contentShow = (TextView) view.findViewById(R.id.content_show);
		this.setView(view);
		
	}

	@Override
	public void setText(int resId) {
		contentShow.setText(resId);
	}

	@Override
	public void setText(CharSequence s) {
		contentShow.setText(s);
	}

	@Override
	public void setGravity(int gravity, int xOffset, int yOffset) {
		// TODO Auto-generated method stub
		super.setGravity(gravity, xOffset, yOffset);
	}

}
