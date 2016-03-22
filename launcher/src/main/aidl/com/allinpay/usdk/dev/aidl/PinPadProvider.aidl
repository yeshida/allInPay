package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.PinPadConfig;
import com.allinpay.usdk.dev.aidl.KeyInfo;
import com.allinpay.usdk.dev.aidl.PinPadListener;
interface PinPadProvider {
	void config(in PinPadConfig cfg);
	void showContent(int lineNum,String content);
	void clearScr(int lineNum);
	int loadKey(in KeyInfo info);
	int loadMkeyByComm(int portNo);
	byte[] calcMac(int mackeyIndex,int mode,in byte[] data);
	void inputOnlinePin(String panBlock, int pinKeyIndex, int pinAlgMode,in PinPadListener listener);
	void inputText(int mode, PinPadListener listener);
	byte[] des(int keyIndex, int desType, int desMode, in byte[] data);

}