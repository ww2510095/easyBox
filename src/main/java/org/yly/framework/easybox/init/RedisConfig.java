package org.yly.framework.easybox.init;

import lombok.Data;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
public class RedisConfig {
    /**
     * Redis的地址
     * */
    private String url;
    /**
     * Redis的密码
     * */
    private String pwd;
}
