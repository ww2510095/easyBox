package org.yly.framework.easybox.base.auth.userRole;

import org.springframework.stereotype.Service;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Service
public class EasyBoxUserRoleService extends EasyBoxBaseService<EasyBoxUserRole> {
    public static final String tabName ="easy_User_Role";
    @Override
    public String getTableName() {
        return tabName;
    }
}
