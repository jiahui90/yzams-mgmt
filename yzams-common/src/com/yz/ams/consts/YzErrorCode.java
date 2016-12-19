/*
 * YzErrorCode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-27 09:58:47
 */
package com.yz.ams.consts;

/**
 *
 * @author Lyc
 */
public class YzErrorCode {
     // 000000 - 009999 鍏叡閿欒
    /**
     * 鏁版嵁搴撳唴閮ㄥけ璐�
     */
    int DB_ERROR = 10;
    /**
     * 閲嶅鐨勪富閿�
     */
    int DB_DUPLICATE_KEY = 11;
    /**
     * 鏁版嵁搴撴彁浜ゅけ璐�
     */
    int DB_TRANSACTION_ERROR = 12;
    /**
     * 鏁版嵁搴撳洖婊氬け璐�
     */
    int DB_ROLLBACK_ERROR = 14;
    /**
     * 鏁版嵁搴撴棤娉曡闂�
     */
    int DB_CONNECT_FAILED = 15;
    /**
     * 瀹㈡埛绔暟鎹簱鍐呴儴閿欒
     */
    int CLIENT_DB_ERROR = 16;
    /**
     * 瀹㈡埛绔暟鎹簱鎻愪氦澶辫触
     */
    int CLIENT_DB_TRANSACTION_ERROR = 17;
    /**
     * 瀹㈡埛绔暟鎹簱鍥炴粴澶辫触
     */
    int CLIENT_DB_ROLLBACK_ERROR = 18;
    /**
     * 搴旂敤鏈嶅姟鍣ㄥ唴閮ㄩ敊璇�
     */
    int SERVER_ERROR = 20;

    /**
     * 鐭俊鏈嶅姟鍣ㄩ敊璇�
     */
    int SENDING_SERVER_EXCEPTION = 21;
    /**
     * 鐢ㄦ埛璁よ瘉澶辫触锛岃閲嶆柊鐧诲綍
     */
    int LACK_OF_AUTH = 110;
    /**
     * 缃戠粶閾炬帴閿欒
     */
    int NETWORK_ERROR = 500;
    /**
     * 鍙傛暟閿欒
     */
    int ILLEGAL_ARGS = 600;
    /**
     * 娣诲姞鐨勮褰曞凡瀛樺湪
     */
    int OBJECT_EXIST = 6666;
    /**
     * 鏌ヨ鐨勮褰曚笉瀛樺湪
     */
    int OBJECT_NOT_EXIST = 7777;
    /**
     * 鎵ц绾跨▼鎰忓涓柇
     */
    int THREAD_INTERRUPTED = 8888;
    /**
     * 鏈煡閿欒
     */
    int UNKNOWN_ERROR = 9999;

    //100000-200000 鏄墜鏈虹閿欒
    /**
     * 鐢ㄦ埛鍚嶆垨瀵嗙爜涓嶈兘涓虹┖
     */
    int EMPTY_USERID_OR_PWD = 100100;
    /**
     * 鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒
     */
    int WRONG_USERID_OR_PWD = 100110;
    /**
     * 鐢ㄦ埛宸插湪鍏朵粬璁惧涓婄櫥褰�
     */
    int LOGIN_OTHER_DEVICE = 100120;
    /**
     * 涓�鍗￠�氬凡缁忚鍏朵粬鐢ㄦ埛缁戝畾
     */
    int APP_FAILED_BIND_ICCARD = 100130;
    /**
     * 楠岃瘉鐮佸凡缁忚繃鏈熸垨鑰呰浣跨敤杩�
     */
    int APP_VERIFICATION_FAILED = 100140;
    /**
     * 閭宸茬粡琚敞鍐�
     */
    int APP_ALREADY_REGIST = 100150;
    /**
     * 鎵嬫満鍙峰凡缁忚娉ㄥ唽
     */
    int APP_MOBILE_ALREADY_REGIST = 100157;
    /**
     * 閭欢鏍煎紡鏈夎
     */
    int INVALID_EMAIL = 100151;
    /**
     * 鏄电О鏍煎紡鏈夎
     */
    int INVALID_NICKNAME = 100152;
    /**
     * 瀵嗙爜鏈夎
     */
    int INVALID_PASSWORD = 100153;
    /**
     * 瀵嗙爜鏈夎
     */
    int INVALID_REALNAME = 100154;
    /**
     * 瀵嗙爜鏈夎
     */
    int INVALID_IDCARD = 100155;
    /**
     * 鏃у瘑鐮佷笉姝ｇ‘
     */
    int APP_UNCORRECT_OLDPASSWORD = 100160;
    /**
     * 鐢ㄦ埛涓嶅瓨鍦�
     */
    int APP_SIGNUPUSER_NOTFOUND = 100170;
    /**
     * 鏄电О宸茶鍗犵敤
     */
    int AGAIN_NICKNAME = 100180;
    /**
     * 鏂版棫瀵嗙爜閲嶅
     */
    int PASSWORD_REPEAT = 100190;
    /**
     * 鐢ㄦ埛閲嶅绉垎
     */
    int AGAIN_POINTS = 100200;
    /**
     * 鍙嶉淇℃伅杩囦笉浜嗗悗鍙伴獙璇�
     */
    int APP_FEEDBACK_VALIDATE_ERROR = 100300;
    /**
     * 鎶藉鐮佹棤鏁�
     */
    int INVALID_DRAW_PRIZE_CODE = 100400;
    /**
     * 鍏戝鐮佹棤鏁�
     */
    int INVALID_RECEIVE_PRIZE_CODE = 100410;
    /**
     * 鍏戝鐮佸凡杩囨湡
     */
    int RECEIVE_PRIZE_CODE_EXPIRED = 100420;
    /**
     * 濂栧搧宸查鍙�
     */
    int RECEIVE_PRIZE_CODE_RECEIVED = 100430;
    /**
     * 鍙厬鎹㈠湀璞嗕负0
     */
    int RECEIVE_PRIZE_CODE_ZERO = 100431;
    /**
     * 濂栧搧宸插厬鎹�
     */
    int RECEIVE_PRIZE_CODE_REDEEMED = 100440;
    /**
     * 鏃犳晥鐨勫鍝侊紙鐢ㄦ埛鏈嫢鏈夎濂栧搧鎴栧鍝佺紪鐮佹棤鏁堬級
     */
    int INVALID_PRIZE = 100450;
    /**
     * 娌℃湁鎶藉涓浘鐗�
     */
    int NO_PROGRESSING_PIC = 100460;
    /**
     * 娌℃湁棣栭〉鍥剧墖
     */
    int NO_HOMEPAGE_PIC = 100470;
    /**
     * 鍦堣眴浣欓涓嶈冻
     */
    int NOT_ENOUGH_COIN = 100500;
    /**
     * 璁㈠崟灏氭湭鏀粯
     */
    int ORDER_NOT_PAID = 100510;
    /**
     * 璁㈠崟宸插彇娑�
     */
    int ORDER_CANCELED = 100520;
    /**
     * 璁㈠崟鐘舵�佸紓甯�
     */
    int ORDER_STATUS_ERROR = 100590;
    /**
     * 璁㈠崟闇�瑕佸湀瀛樺け璐ユ牎楠�
     */
    int ORDER_SHOULD_CHECK = 100591;
    
    //200000-209999 鍚庡彴绠＄悊绔敊璇�
    /**
     * 鏂囦欢鏈壘鍒�
     */
    int File_NOT_FOUND = 200100;
    /**
     * 鎺掑簭浣嶇敓鎴愬け璐�
     */
    int FAILED_GENERATE_SORT_ORDER = 200200;
    /**
     * 瀵煎嚭寮傚父
     */
    int WRITE_EXPORT_FILE_ERROR = 200210;

    //300000-309999 涓�鍗￠�氭湇鍔″櫒鐩稿叧
    int CARD_NETWORK_ERROR = 300001;
    /**
     * CRC鏍￠獙閿欒
     */
    int CRC_ERROR = 300010;
    /**
     * 涓�鍗￠�氭湇鍔″櫒閿欒鍓嶇紑
     */
    int ECARD_RESP_START = 0x301000;
    /**
     * 瓒呭嚭鏈�澶ч檺鍒�
     */
    int AMOUNT_LIMIT = 301000 + 0xD8;
    
    //990000-999999 USMS閿欒
    /**
     * USMS閿欒浠ｇ爜璧峰
     */
    int USMS_ERROR_CODE_START = 990000;
    /**
     * 鎮ㄦ病鏈夎鑹诧紝涓嶈兘鐧诲綍
     */
    int LACK_OF_ROLES = 991000;
     /**
     * 涓�鍗￠�氫氦鏄撴棩鏈熷拰璐﹀崟鏃ユ湡涓嶄竴鑷�
     */
    int DATE_DISACCORD = 991999;
}
