package org.yly.framework.easybox.mybatis.bean.dataInterface;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/3 0003
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 *
 *查询的时候该值采用equals还是like 默认equals
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
@Documented
@Inherited
public @interface EqLike {
    Eq_Like value() default Eq_Like.eq;
     enum Eq_Like{
         /**
          * equals
          * */
        eq,
         /**
          * like
          **/
         like,
         /**
          * 自动装配，
          * 这个值只有在调用getAllLeftJoinOne或者getAllLeftJoin才生效
          * 表示根据javabean自动装配eq还是like
          * 如果该值作用于javabean则默认表示eq
          **/
         auto
    }
}
