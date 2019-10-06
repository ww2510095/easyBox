package org.yly.framework.easybox.mybatis.bean;

import lombok.Data;
/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */

@Data
public class EasyBoxBaseBean {
    private static final long serialVersionUID = 1L;
    /**
     * 数据库里有多少条复合逻辑的数据，只有集合的第一条数据有值，其他都为空
     * */
    public Integer conuntSize;
    /**
     * 表里的时间字段,如果要根据时间赛选，时间的字段名
     * 注意：这里是字段名，而不是值
     * */
    public String timeKey;




}
