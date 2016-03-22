package com.allinpay.usdk.dev.aidl;
import com.allinpay.usdk.dev.aidl.BasicProvider;
import com.allinpay.usdk.dev.aidl.CardReaderProvider;
import com.allinpay.usdk.dev.aidl.PinPadProvider;
import com.allinpay.usdk.dev.aidl.PrinterProvider;
import com.allinpay.usdk.dev.aidl.EthernetProvider;
import com.allinpay.usdk.dev.aidl.emv.EmvProvider;
import com.allinpay.usdk.dev.aidl.SystemServiceProvider;

interface DeviceProvider {
	BasicProvider      getBasicProvider();
	CardReaderProvider getCardReaderProvider();
	PinPadProvider     getPinPadProvider(); 
	PrinterProvider    getPrinterProvider();
	EthernetProvider   getEthernetProvider();
	EmvProvider	   	   getEmvProvider();
	SystemServiceProvider getSystemServiceProvider();
}