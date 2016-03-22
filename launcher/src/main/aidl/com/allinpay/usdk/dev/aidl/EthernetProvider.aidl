package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.EthernetConfig;
interface EthernetProvider {
	void config(in EthernetConfig cfg);
	void enable();
	void disable();
	boolean isReady();
}