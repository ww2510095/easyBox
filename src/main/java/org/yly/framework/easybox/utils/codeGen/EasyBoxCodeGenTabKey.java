package org.yly.framework.easybox.utils.codeGen;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/19 0019
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 标志了该类是一张表，可直接继承自EasyBoxBaseBean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EasyBoxCodeGenTabKey {

}
