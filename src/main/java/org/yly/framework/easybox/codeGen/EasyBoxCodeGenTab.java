package org.yly.framework.easybox.codeGen;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/19 0019
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * javaBeanToSql时的表名，如果是空的将取类名
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EasyBoxCodeGenTab {
    String value();
    String id() default "id";
}
