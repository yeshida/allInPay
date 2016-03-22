package com.allinpay.usdk.dev.aidl;

public interface PrinterErrorCode {
	
	/**
	 *正常状态，无错误
	 */
	public static final int ERROR_NONE = 0x00;
	
	/**
	 * 服务崩溃
	 */
	public static final int ERROR_SERVICE_CRASH = 0x101;

	/**
	 *缺纸，不能打印
	 */
	public static final int ERROR_PAPERENDED = 0xF0; 
	/**
	 *硬件错误
	 */
	public static final int ERROR_HARDERR = 0xF2; 
	/**
	 *打印头过热
	 */
	public static final int ERROR_OVERHEAT = 0xF3; 
	/**
	 *缓冲模式下所操作的位置超出范围 
	 */
	public static final int ERROR_BUFOVERFLOW = 0xF5; 
	/**
	 *低压保护
	 */
	public static final int ERROR_LOWVOL = 0xE1; 

	/**
	 *纸张将要用尽，还允许打印
	 *(单步进针打特有返回值)
	 */
	public static final int ERROR_PAPERENDING = 0xF4; 
	
	/**
	 *打印机芯故障(过快或者过慢)
	 */
	public static final int ERROR_MOTORERR = 0xFB; 
	
	/**
	 *自动定位没有找到对齐位置,纸张回到原来位置
	 */
	public static final int ERROR_PENOFOUND = 0xFC; 
	
	/**
	 *卡纸
	 */
	public static final int ERROR_PAPERJAM = 0xEE; 

	/**
	 *没有找到黑标
	 */
	public static final int ERROR_NOBM = 0xF6; 
	/**
	 *打印机处于忙状态
	 */
	public static final int ERROR_BUSY = 0xF7; 
	/**
	 *黑标探测器检测到黑色信号
	 */
	public static final int ERROR_BMBLACK = 0xF8; 

	/**
	 *打印机电源处于打开状态
	 */
	public static final int ERROR_WORKON =  0xE6;
	/**
	 *打印头抬起
	 *(自助热敏打印机特有返回值)
	 */
	public static final int ERROR_LIFTHEAD = 0xE0; 
	/**
	 *切纸刀不在原位
	 *(自助热敏打印机特有返回值)
	 */
	public static final int ERROR_CUTPOSITIONERR = 0xE2; 
	/**
	 *低温保护或AD出错
	 *(自助热敏打印机特有返回值)
	 */
	public static final int ERROR_LOWTEMP = 0xE3; 
	
	/**
	 *手座机状态正常，但通讯失败
	 *(520针打特有返回值)
	 */
	public static final int ERROR_COMMERR = 0xE5;
}
