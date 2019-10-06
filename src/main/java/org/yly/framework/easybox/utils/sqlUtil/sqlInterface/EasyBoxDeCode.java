package org.yly.framework.easybox.utils.sqlUtil.sqlInterface;

import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxDataType;

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
public @interface EasyBoxDeCode {
	/**
	 * 遵循oracle的decode规则
	 * */
	 String[] dcode() default {};
	 /**
	  * 当数据库里面为时间戳的时候可以给字段加上改注解,次注解在不同的java类型上有不同的表现形式
	  * 如果字段是String，查询出来的数据将是yyyy-MM-dd HH:mm:ss，插入或修改只需要传入可以时间戳即可
	  * 如果字段是data，增删改查都将封装data类型
	  * */
	 boolean dataLong() default false;
	 /**
	  * //如果为字段起了别名，那么dataLong与dcode将映射到别名字段
	  * 原本的字段将取本身值
	  * */
	 EasyBoxDataType alias() default @EasyBoxDataType;

}
