package org.yly.framework.easybox.utils;

import java.lang.annotation.*;

import lombok.ToString;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 检查验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface EasyBoxCheckJavaBean {
	/**
	 * 是否可空，表的主键不生效
	 * */
	boolean notNull() default false;
	/**
	 * 最大长度，-1代表无限长
	 * */
	int maxLength() default -1;
	/**
	 * 最小长度
	 * */
	int minLength() default 0;
	/**
	 * 是否是数字
	 * */
	boolean number() default false;
	/**
	 * 最小值，仅number为true时生效
	 * */
	long numberMin() default Integer.MIN_VALUE;
	/**
	 * 最大值，仅number为true时生效
	 * */
	long numberMax() default  Integer.MAX_VALUE;
	
}
