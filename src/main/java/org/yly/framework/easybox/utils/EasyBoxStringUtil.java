package org.yly.framework.easybox.utils;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 字符串工具类
 */
public class EasyBoxStringUtil {
	
	public static boolean isBlank(Object obj) {
		if(obj==null) return true;
		if(obj instanceof String) {
			return obj.toString().trim().length()==0;
		}else {
			return false;
		}
	}

}
