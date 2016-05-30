package cn.kevinwu.esp8266_cloud_controller.util.sign;

import cn.kevinwu.esp8266_cloud_controller.config.Setting;
import cn.kevinwu.esp8266_cloud_controller.util.common.MD5Util;
import cn.kevinwu.esp8266_cloud_controller.util.common.TimeUtil;

/**
 * Created by KevinWu on 16-5-30.
 * KevinWu.cn
 */
public class SignUtil {
    public static String getSign(){
        String rawString ="gettimestamp="+
                TimeUtil.getTimestamp()+"&appid="+ Setting.appid+Setting.appkey;
        return MD5Util.getMd5String(rawString);
    }
}
