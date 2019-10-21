package org.yly.framework.easybox.spring.bean;

import lombok.Data;

import java.util.List;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/10 0010
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 如果方法有此参数，那么环绕错误信息不会被抛出，而是封装为此数组
 */
@Data
public class EasyBoxAspectException {
    private List<String> easyBoxException;

}
