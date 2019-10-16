package org.yly.framework.easybox.base.auth.role;

import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 权限树
 */
@Data
public class EasyBoxRole extends EasyBoxBaseBean {
    private String id;
    private String name;//角色名字名字
    private String authId;//权限id
}
