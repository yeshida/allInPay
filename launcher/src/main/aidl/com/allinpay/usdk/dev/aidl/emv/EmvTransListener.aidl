package com.allinpay.usdk.dev.aidl.emv;
interface EmvTransListener{
	int onWaitAppSelect(in List<String> appNameList, boolean isFirstSelect); 
	int onConfirmCardNo(String cardno);
	int onCertVerfiy(String certType, String certValue);
	int onCardHolderPwd(boolean isOnlinePin, int offlinePinLeftTimes);
	int onOnlineProc();
	void onShowMessage(String message);
	void onSendKey(int keyCode);
}