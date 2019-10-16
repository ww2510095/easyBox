package org.yly.framework.easybox.base.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 简单的日志，只记录状态
 */
@Data
@AllArgsConstructor
public class EasyBoxLog extends EasyBoxBaseBean {
    private String id;
    private String tabName;//表名
    private Long time;//时间
    private String userId;//操作人
    private String dateId;//数据id
    private String value;//值
}
