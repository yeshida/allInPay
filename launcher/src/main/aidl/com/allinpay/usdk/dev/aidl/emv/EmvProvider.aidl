package com.allinpay.usdk.dev.aidl.emv;
import com.allinpay.usdk.dev.aidl.emv.EmvTermConfig;
import com.allinpay.usdk.dev.aidl.emv.EmvTransData;
import com.allinpay.usdk.dev.aidl.emv.EmvTransListener;
import com.allinpay.usdk.dev.aidl.emv.EmvCardLog;
import com.allinpay.usdk.dev.aidl.emv.EmvAidParam;
import com.allinpay.usdk.dev.aidl.emv.EmvCapk;
import com.allinpay.usdk.dev.aidl.emv.EmvChannel;
interface EmvProvider{
	void setTermConfig(in EmvTermConfig config);
	EmvTermConfig getTermConfig();
	int process(in EmvTransData transData, in EmvTransListener listener);
	void setTlv(int tag, in byte[] value);
	byte[] getTlv(int tag);
	List<String> readLogRecord(in EmvChannel channel, in int logType);
	void setAidParam(in EmvAidParam param);
	List<EmvAidParam> getAidParam();
	void clearTermAid();
	void setCapk(in EmvCapk capk);
	List<EmvCapk> getCapk();
	void clearCapk();
	long getEcBalance(in EmvChannel channel);
}