package org.yly.framework.easybox.task;

import org.springframework.stereotype.Service;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/4 0004
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Service
public class Members extends EasyBoxBaseService<Member> {
    @Override
    public String getTableName() {
        return "member";
    }

}
