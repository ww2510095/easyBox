package org.yly.framework.easybox.mybatis.dao;

import org.apache.ibatis.annotations.SelectProvider;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.dao.imp.EasyBoxBaseDaoImp;

import java.util.List;
import java.util.Map;
/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public interface EasyBoxBaseDao {

	@SelectProvider(type = EasyBoxBaseDaoImp.class,method = "getAll")
	List<Map> getAll(EasyBoxSql mEasyBoxSql);
	@SelectProvider(type = EasyBoxBaseDaoImp.class,method = "getAllCount")
	 Integer getAllCount(EasyBoxSql mEasyBoxSql);

	/**
	 * 单纯的执行一条sql，不求返回值，除了主体sql所有的参数无效
	 * */
	@SelectProvider(type = EasyBoxBaseDaoImp.class,method = "exeSql")
	 void exeSql(EasyBoxSql mEasyBoxSql);
	/**
	 * 查询sql，所有参数有效
	 * */
	@SelectProvider(type = EasyBoxBaseDaoImp.class,method = "exeSelectSql")
	 List<Map> exeSelectSql(EasyBoxSql mEasyBoxSql);
	/**
	 * 得到给定的参数最大值+1
	 * */
	@SelectProvider(type = EasyBoxBaseDaoImp.class,method = "getParamAddOne")
	Integer getParamAddOne(String column);
	
	

}
