package org.yly.framework.easybox.base;

import lombok.Data;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 返回到前端的值
 */
@Data
public class ReqBase {
    private int code;//响应码，0：正常
    private String msg;//提示信息
    private Object data;//数据
}
