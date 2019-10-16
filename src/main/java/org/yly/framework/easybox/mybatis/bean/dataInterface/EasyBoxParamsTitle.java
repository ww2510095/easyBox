package org.yly.framework.easybox.mybatis.bean.dataInterface;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 为字段起一个标题名字，所有提示都将使用这个别名，如果为空或者没有此注解将采用字段名
 */
public @interface EasyBoxParamsTitle {
    /***
     * 标题
     */
    String value();
}
