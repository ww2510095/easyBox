package org.yly.framework.easybox.base.log.config;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 需要记录的字段,如果为空则不记录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface EasyBoxLogKey {

    String value();
}
