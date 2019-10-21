package org.yly.framework.easybox.cache.bean;

import lombok.Data;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/6 0006
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
public class EasyBoxSqlVersion {
    /**
     * 数据，表名
     * */
    private String data;
    /**
     * 数据的版本
     * */
    private Long version;
}
