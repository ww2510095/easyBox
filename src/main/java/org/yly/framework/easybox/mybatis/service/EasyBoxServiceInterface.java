package org.yly.framework.easybox.mybatis.service;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public interface EasyBoxServiceInterface {
    /**
     * 数据库对应的表名，如果不重写此方法，
     * */
    String getTableName();
    /**
     * 数据库里面的主键字段
     * */
    default String getIdKey() {
        return "id";
    }
}
