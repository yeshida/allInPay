package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.CardReaderListener;
interface CardReaderProvider {
	void readCard(int timeout,byte readMode,CardReaderListener listener);
	void stopReadCard();
}