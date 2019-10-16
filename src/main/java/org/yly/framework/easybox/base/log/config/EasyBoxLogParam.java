package org.yly.framework.easybox.base.log.config;

import sun.security.action.GetLongAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxLogParam {

    private static final Map<String,String> logKey = new HashMap<>();
    public static Map<String, String> getLogKey() {
        return logKey;
    }
}
