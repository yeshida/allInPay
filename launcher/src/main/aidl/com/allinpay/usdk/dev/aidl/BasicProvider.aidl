package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.BasicInfo;
interface BasicProvider {
	void initial();
	void reset();
	BasicInfo getBasicInfo();
	void beep(in int mode);
	void setLed(byte led);
}