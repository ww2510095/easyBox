package org.yly.framework.easybox.base.auth.userRole;

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
public class UserRole extends EasyBoxBaseBean {
    private String id;
    private String userId;//用户id
    private String authId;//权限id
}
