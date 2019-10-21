package org.yly.framework.easybox.mybatis.bean;


import lombok.Data;
import org.yly.framework.easybox.utils.EasyBoxGsonUtil;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
public class EasyBoxSql {

	/**
	 * Sql主体
	 * */
	private String sql;
	/**
	 * 排序字段
	 * */
	private String orderbykey;
	/**
	 * 排序方式
	 * */
	private orderBy orderbytype;
	/**
	 * 当前页数
	 * */
	private Integer page;
	/**
	 * 每一页的条数
	 * */
	private Integer rows;
	/**
	 * 开始时间
	 * */
	private Long starTime;
	/**
	 * 结束时间
	 * */
	private Long endTime;
	/**
	 * 表里的时间字段。如果要根据时间赛选，那么时间的字段
	 * 该字段的值必须是可以转为number类型的值
	 * */
	private String timeKey;
	/**
	 * 列明，分页也不采用*，但是因为有leftjoin，所以字段不确定，所以不能从缓存里取
	 * */
	private String columns;
	/**
	 * 是否是count
	 * */
	private boolean count=false;
	@Override
	public String toString() {
		return EasyBoxGsonUtil.toJsonString(this).replace(".0","");
	}

	public enum orderBy{
		ASC,//升序
		DESC//降序
	}
}
