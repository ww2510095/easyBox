package org.yly.framework.easybox.utils.sqlUtil.sqlInterface;

import java.lang.annotation.*;


/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN https://blog.csdn.net/qq_25861361
 * 标记了不在表里面的字段
 * 如果字段被标记，那么说明表里没有这个字段，因此哪怕是查询的时候传入的条件也会被忽略
 * 自动生成sql语句的时候被标记的字段会被忽略
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface EasyBoxNotParams {

}
