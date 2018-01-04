package me.snake.mina;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 系统公用常量类
 * @author suxl
 *
 */
public class Constants {

    public static final byte V4_PROTOCOL_START = 0x5B;
    public static final byte V4_PROTOCOL_END = 0x5D;

    public static final byte V4_PROTOCOL_CODE = 0x04;

    public static final String USER_SESSION = "userSessionBean";
    public static final String ERROR_MESSAGE = "errorMsg";
    public static final String SUCCESS_MESSAGE = "successMsg";
    
    /**
     * 状态标志位
     */
    public static final int STATUS_YES = 1; //对应true
    public static final int STATUS_NO = 0;	//对应false

    public static final String TOKEN_KEY = "SESSION_TOKEN";
	
	public final static String URL_KEY = "f5253d3e727685745c59b697babcc080"; //加密key

    public static final Long PATIENT_ROLE_ID = 6l;
    public static final Long HOSPITAL_ROLE_ID = 3l;
    public static final Long DOCTOR_ROLE_ID = 4l;
    public static final Long NURSE_ROLE_ID = 5l;

    public static final int ALERT_LEVEL_RED = 1;
    public static final int ALERT_LEVEL_YELLOW = 2;
    public static final int ALERT_LEVEL_GREEN = 3;

    public static final Double DEFUALT_HIGH_ALARM = 7.8;
    public static final Double DEFUALT_LOW_ALARM = 3.9;
    public static final Integer DEFUALT_HIGH_SUSTAIN_TIME = 30;
    public static final Integer DEFUALT_LOW_SUSTAIN_TIME = 15;
    public static final String CHARTSET_CODE_GBK = "GBK";
    public static final Charset CHARTSET_GBK = Charset.forName(CHARTSET_CODE_GBK);
    public static final CharsetDecoder CHARTSET_GBK_DECODER = CHARTSET_GBK.newDecoder();

    public static final long SECOND_TIME = 1000;
    public static final long MINUTE_TIME = SECOND_TIME * 60;
    public static final long HOUR_TIME = MINUTE_TIME * 60;
    public static final long DAY_TIME = HOUR_TIME * 24;
    public static final long YEAR_TIME = DAY_TIME * 365;

    public static final int CODE_TIME_OUT = 2 * (int) HOUR_TIME;

}
