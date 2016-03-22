package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.PrinterListener;
interface PrinterProvider {
	void initial();
	int getStatus();
	int appendStr(String str,int fontSize,boolean isBold);
	int appendImage(in Bitmap bitmap);
	int startPrint();
	void cutPaper();
}