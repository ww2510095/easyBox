package org.yly.framework.easybox.task;

import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxColumn;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EqLike;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxNotParams;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/4 0004
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
public class Member extends EasyBoxBaseBean {
    private String uname;
    @EasyBoxColumn("password")
    @EqLike(EqLike.Eq_Like.like)
    private String pwd;
    private String id;
}
