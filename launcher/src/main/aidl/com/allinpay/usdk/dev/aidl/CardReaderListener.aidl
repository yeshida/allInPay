package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.CardInfo;
interface CardReaderListener {
	void onReadCardSucc(in CardInfo info);
	void onReadCardErr(in int errCode);
}