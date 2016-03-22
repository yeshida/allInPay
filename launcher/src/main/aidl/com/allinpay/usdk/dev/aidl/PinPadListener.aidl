package com.allinpay.usdk.dev.aidl;
interface PinPadListener {
	void onInputResult(int retCode, in byte[] data);
	void onSendKey(int keyCode);
}