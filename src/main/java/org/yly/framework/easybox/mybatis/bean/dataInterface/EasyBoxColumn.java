package org.yly.framework.easybox.mybatis.bean.dataInterface;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface EasyBoxColumn {
    /**
     * 用来表示在数据表里面的对应字段，如果和@EasyBoxNotParams一起使用，次参数将被忽略
     * */
    String value();
}
