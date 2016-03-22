package com.allinpay.usdk.dev.aidl;


/**
 * pinpad相关的常量定义
 * @author Steven.W
 *
 */
public class PinPadConstant {
	
/***********************pinpad类型定义******************************************/
	/**
	 * pinpad类型:内置
	 */
	public static final int INTERNAL = 0;
	
	/**
	 * pinpad类型:外置
	 */
	public static final int EXTERNAL = 1;
	

	
/***********************密钥类型定义******************************************/
	/**
	 * 密钥类型:主密钥
	 */
	public static final int KEYTYPE_MKEY     = 0x01;
	/**
	 * 密钥类型:PIN密钥
	 */
	public static final int KEYTYPE_PINKEY   = 0x02;
	/**
	 * 密钥类型:MAC密钥
	 */
	public static final int KEYTYPE_MACKEY   = 0x03;
	/**
	 * 密钥类型:磁道加密密钥
	 */
	public static final int KEYTYPE_TDKKEY = 0x04;
	/**
	 * 密钥类型:DES密钥
	 */
	public static final int KEYTYPE_DESKEY   = 0x05;
	
/*************************************PIN 算法类型************************************/
	public static final byte MODE_ISO9564_0 = 0x00;
	public static final byte MODE_ISO9564_1 = 0x01;
	public static final byte MODE_ISO9564_2 = 0x02;
	public static final byte MODE_HK_EPS= 0x03;
	
/*************************************MAC 算法类型************************************/
	public static final int ECB = 0x00;
	public static final int CBC = 0x01;

/*************************************DES 算法类型************************************/
	public static final int DES  = 0x00;
	public static final int TDES = 0x01;

/*************************************DES 模式常量************************************/
	/**
	 * 加密
	 */
	public static final int ENCRYPT = 0x00;
	/**
	 * 解密
	 */
	public static final int DECRYPT = 0x01;
	
/*************************************外置密码键盘显示定义************************************/
	/**
	 * "*"号显示
	 */
	public static final int PASSWORD = 0;
	/**
	 * 原样显示
	 */
	public static final int PLAINTEXT = 1;
	
/***************************************PinPad返回值定义*************************************/
	public static final int OK = 0;
	private static final int ERR_BASE = -0xFF0000;
	/**
	 * 密钥不存在
	 */
	public static final int ERR_NO_KEY = ERR_BASE - 1;
	/**
	 * 密钥索引错，参数索引不在范围内
	 */
	public static final int ERR_KEY_INDEX = ERR_BASE -2;
	/**
	 * 密钥写入时，源密钥的层次比目的密钥低
	 */
	public static final int ERR_DERIVE = ERR_BASE - 3;
	/**
	 * 密钥验证失败
	 */
	public static final int ERR_CHECK_KEY = ERR_BASE - 4;
	/**
	 * 没输入PIN
	 */
	public static final int ERR_NO_PIN_INPUT = ERR_BASE - 5;
	/**
	 * 取消输入PIN
	 */
	public static final int ERR_INPUT_CANCEL = ERR_BASE - 6;
	/**
	 * 函数调用小于最小间隔时间
	 */
	public static final int ERR_WAIT_INTERVAL = ERR_BASE - 7;
	/**
	 * KCV模式错，不支持
	 */
	public static final int ERR_CHECK_MODE = ERR_BASE - 8;
	/**
	 * 无权使用该密钥，当出现密钥标签不对，或者写入密钥时，源密钥类型的值大于目的密钥类型，都会返回该密钥
	 */
	public static final int ERR_NO_RIGHT_USE = ERR_BASE - 9;
	/**
	 * 密钥类型错
	 */
	public static final int ERR_KEY_TYPE = ERR_BASE - 10;
	/**
	 *期望PIN的长度字符串错
	 */
	public static final int ERR_EXPLEN = ERR_BASE - 11;
	/**
	 * 目的密钥索引错，不在范围内
	 */
	public static final int ERR_DSTKEY_INDEX = ERR_BASE- 12;
	/**
	 * 源密钥索引错，不在范围内
	 */
	public static final int ERR_SRCKEY_INDEX = ERR_BASE -13;
	/**
	 * 密钥长度错
	 */
	public static final int ERR_KEY_LEN = ERR_BASE - 14;
	/**
	 * 输入PIN超时
	 */
	public static final int ERR_INPUT_TIMEOUT = ERR_BASE - 15;
	/**
	 * IC卡不存在
	 */
	public static final int ERR_NO_ICC = ERR_BASE - 16;
	/**
	 * IC卡未初始化
	 */
	public static final int ERR_ICC_NO_INIT = ERR_BASE - 17;
	/**
	 * DUKPT组索引号错
	 */
	public static final int ERR_GROUP_INDEX = ERR_BASE - 18;
	/**
	 * 指针参数非法为空
	 */
	public static final int ERR_PARAM_PTR_NULL = ERR_BASE - 19;
	/**
	 * PED已锁
	 */
	public static final int ERR_LOCKED = ERR_BASE - 20;
	/**
	 * PED通用错误
	 */
	public static final int ERR_RET_ERROR = ERR_BASE - 21;
	/**
	 * 没有空闲的缓冲
	 */
	public static final int ERR_NOMORE_BUF = ERR_BASE - 22;
	/**
	 * 需要取得高级权限
	 */
	public static final int ERR_NEED_ADMIN = ERR_BASE - 23;
	/**
	 * DUKPT已经溢出
	 */
	public static final int ERR_DUKPT_OVERFLOW = ERR_BASE -24;
	/**
	 * KCV校验失败
	 */
	public static final int ERR_KCV_CEHECK = ERR_BASE - 25;
	/**
	 * 源密钥类型错
	 */
	public static final int ERR_SRCKEY_TYPE = ERR_BASE - 26;
	/**
	 * 命令不支持
	 */
	public static final int ERR_UNSUPPORT_CMD = ERR_BASE - 27;
	/**
	 * 通讯错误
	 */
	public static final int ERR_COMM = ERR_BASE - 28;
	/**
	 * 没有用户认证公钥
	 */
	public static final int ERR_NO_UAPUK = ERR_BASE - 29;
	/**
	 * 取系统敏感服务失败
	 */
	public static final int ERR_ADMIN = ERR_BASE - 30;
	/**
	 * PED处于下载非激活状态
	 */
	public static final int ERR_DOWNLOAD_DISACTIVE = ERR_BASE - 31;
	/**
	 * KCV 奇校验失败
	 */
	public static final int ERR_KCV_ODD_CHECK = ERR_BASE - 32;
	/**
	 * 读取PED数据失败
	 */
	public static final int ERR_PED_DATA_RW = ERR_BASE - 33;
	/**
	 * 卡操作错误(脱机明文、密文密码验证)
	 */
	public static final int ERR_ICC_CMD = ERR_BASE - 34;
	/**
	 * 用户按CLEAR键退出输入PIN
	 */
	public static final int ERR_INPUT_CLEAR = ERR_BASE - 35;
	/**
	 * PED存储空间不足
	 */
	public static final int ERR_NO_FREE_FLASH = ERR_BASE - 36;
	/**
	 * DUKPT KSN需要先加1
	 */
	public static final int ERR_DUPKT_NEED_INC_KSN = ERR_BASE - 37;
	/**
	 * KCV MODE错误
	 */
	public static final int ERR_KCV_MODE = ERR_BASE - 38;
	/**
	 * NO KCV
	 */
	public static final int ERR_DUKPT_NO_KCV = ERR_BASE - 39;
	/**
	 * 按FN/ATM4键取消PIN输入
	 */
	public static final int ERR_PIN_PYBASS_BY_FUNCKEY = ERR_BASE - 40;
	/**
	 * 数据MAC校验错
	 */
	public static final int ERR_MAC = ERR_BASE - 41;
	/**
	 * 数据CRC校验错
	 */
	public static final int ERR_CRC = ERR_BASE - 42;
	
	/**
	 * 其他异常错误
	 */
	public static final int ERR_OTHER = ERR_BASE - 0xFF;
}
