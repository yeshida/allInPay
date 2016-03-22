package com.allinpay.usdk.dev.aidl.emv;


public class EmvConstant {
	/********************************返回码定义***************************************/
	public static final int EMV_OK = 0;
	private static int BASE_ERR = -0x8000;
	/**
	 * IC卡复位失败
	 */
	public static final int ICC_RESET_ERR = BASE_ERR - 1;
	/**
	 * IC命令失败
	 */
	public static final int ICC_CMD_ERR = BASE_ERR - 2;
	/**
	 * IC卡锁卡
	 */
	public static final int ICC_BLOCK = BASE_ERR - 3;
	/**
	 * IC返回码错误
	 */
	public static final int EMV_RSP_ERR   = BASE_ERR - 4;
	/**
	 * //EMV应用已锁
	 */
	public static final int EMV_APP_BLOCK   = BASE_ERR - 5;        
	/**
	 * 卡片里没有EMV应用
	 */
	public static final int EMV_NO_APP   = BASE_ERR - 6;           
	/**
	 *  用户取消当前操作或交易
	 */
	public static final int EMV_USER_CANCEL  = BASE_ERR - 7;
	/**
	 * 用户操作超时
	 */
	public static final int EMV_TIME_OUT   = BASE_ERR - 8;
	/**
	 * 卡片数据错误
	 */
	public static final int EMV_DATA_ERR   = BASE_ERR - 9;
	/**
	 * 交易不接受
	 */
	public static final int EMV_NOT_ACCEPT  = BASE_ERR - 10;
	/**
	 * 交易被拒绝
	 */
	public static final int EMV_DENIAL  = BASE_ERR - 11;           
	/**
	 * 密钥过期
	 */
	public static final int EMV_KEY_EXP   = BASE_ERR - 12;
	/**
	 * 没有密码键盘或键盘不可用
	 */
	public static final int EMV_NO_PINPAD   = BASE_ERR - 13;        
	/**
	 * 没有密码键盘或键盘不可用
	 */
	public static final int EMV_NO_PASSWORD  = BASE_ERR - 14;
	/**
	 *  密钥校验和错
	 */
	public static final int EMV_SUM_ERR  = BASE_ERR - 15;
	/**
	 * 没有找到指定的数据或元素
	 */
	public static final int EMV_NOT_FOUND  = BASE_ERR - 16;
	/**
	 * 指定的数据元素没有数据
	 */
	public static final int EMV_NO_DATA  = BASE_ERR - 17;
	/**
	 * 内存溢出
	 */
	public static final int EMV_OVERFLOW  = BASE_ERR - 18;
	/**
	 * 无交易日志
	 */
	public static final int EMV_NO_TRANS_LOG  = BASE_ERR - 19;    
	/**
	 * 指定的日志记录不存在
	 */
	public static final int EMV_RECORD_NOTEXIST  = BASE_ERR - 20;
	/**
	 * 指定的标签在当前日志记录中不存在
	 */
	public static final int EMV_LOGITEM_NOTEXIST  = BASE_ERR - 21;
	/**
	 * GAC中卡片回送6985, 由应用决定是否fallback
	 */
	public static final int ICC_RSP_6985  = BASE_ERR - 22;
	/**
	 *  文件操作失败
	 */
	public static final int EMV_FILE_ERR   = BASE_ERR - 24;       
	/**
	 * 参数错误
	 */
	public static final int EMV_PARAM_ERR   = BASE_ERR - 30;
	/**
	 *  TLVData数据已满
	 */
	public static final int EMV_DATA_OVERFLOW  = BASE_ERR - 35;
	/**
	 * 请求下一个CVM
	 */
	public static final int EMV_NEXT_CVM   = BASE_ERR - 53;
	/**
	 *  退出CVM
	 */
	public static final int EMV_QUIT_CVM   = BASE_ERR - 57;
	/**
	 * 选择下一个应用
	 */
	public static final int EMV_SELECT_NEXT  = BASE_ERR - 59;
	/**
	 * 必须使用其他界面进行交易
	 */
	public static final int CLSS_USE_CONTACT  = BASE_ERR - 23;
	/**
	 * 必须终止交易
	 */
	public static final int CLSS_TERMINATE  = BASE_ERR - 25;
	/**
	 * 其他错误
	 */
	public static final int CLSS_FAILED   = BASE_ERR - 26;
	/**
	 * 应拒绝交易
	 */
	public static final int CLSS_DECLINE  = BASE_ERR - 27;
	/**
	 * 国际交易(VISA AP payWave Level2 卡片使用)
	 */
	public static final int CLSS_WAVE2_OVERSEA  = BASE_ERR - 31;
	/**
	 * US 卡(VISA AP payWave Level2 卡片使用)
	 */
	public static final int CLSS_WAVE2_US_CARD  = BASE_ERR - 33;
	/**
	 * 需使用IC卡交易（VISA payWave卡片使用）
	 */
	public static final int CLSS_WAVE3_INS_CARD  = BASE_ERR - 34;
	/**
	 * 卡片超出有效期
	 */
	public static final int CLSS_CARD_EXPIRED  = BASE_ERR - 36;
	/**
	 * 选择PPSE时返回码错误，且无匹配应用
	 */
	public static final int CLSS_NO_APP_PPSE_ERR  = BASE_ERR - 37;
	/**
	 * 请使用非接完整PBOC
	 */
	public static final int CLSS_USE_VSDC  = BASE_ERR - 38;      
	/**
	 * 仅用于AE内核，持卡人验证要求拒绝交易
	 */
	public static final int CLSS_CVMDECLINE  = BASE_ERR - 39;
	/**
	 * 请参考用户设备
	 */
	public static final int CLSS_REFER_CONSUMER_DEVICE  = BASE_ERR - 40; 

	/**
	 * IC卡降级
	 */
	public static final int FALL_BACK		 =  BASE_ERR - 200;
	/**
	 * 通道错误
	 */
	public static final int ERR_CHANNEL      =  BASE_ERR - 201;
	/**
	 * 参数错误
	 */
	public static final int ERR_PARAM_INVA   =  BASE_ERR - 202;
	/**
	 * 电子现金参数错误
	 */
	public static final int ERR_EC_PARAM     =  BASE_ERR - 203;
	/**
	 * 路径错误
	 */
	public static final int ERR_CORE_PATH    =  BASE_ERR - 204;
	/**
	 * 卡余额不足
	 */
	public static final int CLSS_NO_BALANCE  =  BASE_ERR - 205;
	/**
	 * 纯电子现金卡,超过脱机交易限额
	 */
	public static final int CLSS_OVERFLMT    =  BASE_ERR - 206;
	/**
	 * 非PBOC非接触卡
	 */
	public static final int ERR_NOCLSSPBOC   =  BASE_ERR - 207;	  
	/**
	 * 写文件失败
	 */
	public static final int ERR_WRITEFILE_FAIL = BASE_ERR - 208;	
	/**
	 * 读文件失败
	 */
	public static final int ERR_READFILE_FAIL =  BASE_ERR - 209;	
	/**
	 * 非法参数
	 */
	public static final int ERR_INVAILD_PARA = BASE_ERR - 210;  
	public static final int APPEMV_ERR_AMOUNT_FORMAT = ERR_INVAILD_PARA - 1; // 金额格式错误
	public static final int APPEMV_ERR_PARAM_LENGTH = ERR_INVAILD_PARA - 2; // 参数长度错误
	
	
	
	/********************************EMV FLOW 定义***************************************/
	public static enum FLOW {
		/**
		 * 全流程
		 */
		COMPLETE,
		/**
		 * 简化流程
		 */
		SIMPLE,
		/**
		 * 非接流程
		 */
		QPBOC
	}
}

