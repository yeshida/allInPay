package com.allinpay.usdk.dev.aidl;

public class PrinterInfo {

	/**
	 * 状态正常
	 */
	public static final int STATUS_NORMAL    = 0x00;
	/**
	 * 状态打印机忙
	 */
	public static final int STATUS_BUSYING   = -0x01;
	/**
	 * 状态缺纸
	 */
	public static final int STATUS_LESSPAPER = -0x02;
	/**
	 * 状态过热
	 */
	public static final int STATUS_TOOHOT    = -0x03; 

	public static final int FONT_SIZE_LARGE  = 32;
	public static final int FONT_SIZE_NORMAL = 24;
	public static final int FONT_SIZE_SMALL  = 16;
}
