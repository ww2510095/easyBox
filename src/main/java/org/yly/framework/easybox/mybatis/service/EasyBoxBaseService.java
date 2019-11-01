package org.yly.framework.easybox.mybatis.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.yly.framework.easybox.base.log.EasyBoxLog;
import org.yly.framework.easybox.base.log.EasyBoxLogService;
import org.yly.framework.easybox.base.log.config.EasyBoxLogAutoConfiguration;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.mybatis.EasyBoxSqlException;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.EasyBoxLeftJoin;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EqLike;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;
import org.yly.framework.easybox.security.EasyBoxSecurity;
import org.yly.framework.easybox.utils.EasyBoxBeanUtil;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.EasyBoxUserils;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;
import org.yly.framework.easybox.utils.sqlUtil.EasyBoxDateUtil;
import org.yly.framework.easybox.utils.sqlUtil.EasyBoxSqlUtil;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxDeCode;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxNotParams;
/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public  abstract class EasyBoxBaseService<T extends EasyBoxBaseBean> implements EasyBoxServiceInterface{


	@Autowired
	private EasyBoxBaseDao cEasyBoxBaseDao;
	@Autowired
	private EasyBoxLogService cEasyBoxLogService;
	@Autowired
	private EasyBoxLogAutoConfiguration cEasyBoxLogAutoConfiguration;

	@Override
	public String getIdKey() {
		return "id";
	}

	/**
	 * 执行更新的sql
	 *  mEasyBoxSql sql内容
	 *  whereKey 更改的字段 where a=1 则此处为a
	 *  whereValue 更改的内容  where a=1 则此处为1
	  NoSuchFieldException,IllegalAccessException
	 * */
	public void exeSql(EasyBoxSql mEasyBoxSql,String whereKey,String whereValue) throws NoSuchFieldException, IllegalAccessException {
		exeSql(mEasyBoxSql,getTableName(),getIdKey(),whereKey,whereValue);
	}
	/**
	 * 执行更新的sql
	 *  mEasyBoxSql sql内容
	  NoSuchFieldException,IllegalAccessException
	 * */
	public void exeSql(EasyBoxSql mEasyBoxSql) throws NoSuchFieldException, IllegalAccessException {
		exeSql(mEasyBoxSql,"1","2");
	}
	/**
	 * 执行一条查询的sql
	 *  mEasyBoxSql sql内容
	  NoSuchFieldException,IllegalAccessException
	 * */
	public List<Map> exeSelectSql(EasyBoxSql mEasyBoxSql) {
		String ssql =mEasyBoxSql.getSql();
		if(EasyBoxStringUtil.isBlank(ssql)){
			throw new EasyBoxCheckException("sql主体不可为空");
		}
		if(ssql.toLowerCase().trim().indexOf("SELECT")!=0){
			throw new EasyBoxCheckException("exeSelectSql方法只能执行查询sql，如果要执行增加或修改或删除请使用exeSel");
		}
		return cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
	}
	/**
	 * 根据给定的参数删除表里的数据
	 *  key key
	 *  value value
	  NoSuchFieldException,IllegalAccessException
	 * */
	public void deleteByParam(String key,String value) throws NoSuchFieldException, IllegalAccessException {
		EasyBoxSql mEasyBoxSql =new EasyBoxSql();
		mEasyBoxSql.setSql("delete "+getTableName()+" where "+key+"='"+ value+"'");
		exeSql(mEasyBoxSql,getTableName(),getIdKey(),null,null);
	}
	/**
	 * 根据指定的条件更新，优点是速度比updateBySelect更快一些，
	 * 缺点是只能更新字符型与数值型，不能更新时间日期型
	 *  strs strs
	 *  whereKalue whereKalue
	 *  whereKey whereKey
	  NoSuchFieldException,IllegalAccessException
	 * */
	public void updateByParam(String whereKey,String whereKalue,String... strs) throws NoSuchFieldException, IllegalAccessException {
		if(EasyBoxStringUtil.isBlank(whereKey)||EasyBoxStringUtil.isBlank(whereKalue)) {
			throw new RuntimeException("给定的条件或值不可为空");
		}
		if(strs==null||strs.length==0||strs.length%2!=0) {
			throw new RuntimeException("给定的值错误");
		}
		EasyBoxSql mEasyBoxSql =new EasyBoxSql();
		mEasyBoxSql.setSql("update "+getTableName()+" set  ");
		for(int i=0;i<strs.length;i=i+2) {
			mEasyBoxSql.setSql(mEasyBoxSql.getSql()+strs[i]+"='"+strs[i+1]+"',");
		}
		mEasyBoxSql.setSql(mEasyBoxSql.getSql().substring(0,mEasyBoxSql.getSql().length()-1));
		mEasyBoxSql.setSql(mEasyBoxSql.getSql()+" where "+whereKey+"='"+ whereKalue+"'");
		exeSql(mEasyBoxSql,getTableName(),getIdKey(),whereKey,whereKalue);
	}
	/**
	 * 根据给定的某个参数查询表里的数据，速度比起getAll更快一些
	 *  key key
	 *  value value
	 * */
	public List<Map> getByParam(String key,String value) {
		EasyBoxSql mEasyBoxSql =new EasyBoxSql();
		mEasyBoxSql.setSql("select "+EasyBoxSqlUtil.getColumnSql(null,getTableName())+" from "+getTableName()+" where "+key+"='"+ value+"'");
		return cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
	}
	/**
	 * 执行一条指定的查询sql语句
	 *  mEasyBoxSql SQL
	 *  tabName 表名，如果有多个表名以逗号隔开，这个参数不能出现错误，否则有可能会获得脏数据
	 * */
	public List<Map> exeSelectSql(EasyBoxSql mEasyBoxSql,String tabName) {
		return cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
	}


	/**
	 * 保存或添加数据
	 *  params params
	 * 添加参见{@like add(T)}
	 * 修改参见{@like updateBySelect(T)}
	 * */
	public newSaveType save(T params) throws NoSuchFieldException, IllegalAccessException {
		if(getByid(params)!=null) {
			updateBySelect(params);
			return newSaveType.update;
		}else {
			add(params);
			return newSaveType.add;
		}
	}
	public enum newSaveType{
		add,update
	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  orderbykey 排序的字段
	 *  orderbytype 排序的方式
	 *  _and_or 多个条件之间采用and还是or
	 *  _EqLike 多个条件之间采用的策略
	 *               eq:等于
	 *               like:like '%t%'
	 *               auto:根据javabean自动装配
	 *  starTime 只查询给定时间之后的值，只支持时间戳
	 *  endTime 只查询给定时间之前的值，只支持时间戳
	 *  mLeftJoin 关联的其他表,如果为空则是单表查询
	 * */
	public List<T> getAllLeftJoinOne(T t,
									 Integer page, Integer rows,
									 String orderbykey, EasyBoxSql.orderBy orderbytype,
									 and_or _and_or,
									 EqLike.Eq_Like _EqLike,
									 Long starTime, Long endTime,
									 EasyBoxLeftJoin mLeftJoin
	){
		return getAllLeftJoin(t, page, rows, orderbykey, orderbytype, _and_or, _EqLike,starTime, endTime, Arrays.asList(mLeftJoin));
	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  orderbykey 排序的字段
	 *  orderbytype 排序的方式
	 *  _and_or 多个条件之间采用and还是or
	 *  _EqLike 多个条件之间采用的策略
	 *                eq:等于
	 *                like:like '%t%'
	 *                auto:根据javabean自动装配
	 *  starTime 只查询给定时间之后的值，只支持时间戳
	 *  endTime 只查询给定时间之前的值，只支持时间戳
	 *  mListLeftJoin 关联的其他表,如果为空则是单表查询
	 * */
	public List<T> getAllLeftJoin(T t,
								  Integer page, Integer rows,
								  String orderbykey, EasyBoxSql.orderBy orderbytype,
								  and_or _and_or,
								  EqLike.Eq_Like _EqLike,
								  Long starTime, Long endTime,
								  List<EasyBoxLeftJoin> mListLeftJoin
	){
		EasyBoxSecurity.checkJavaBean(t, false);
		try {
			EasyBoxSql mEasyBoxSql =new EasyBoxSql();
			mEasyBoxSql.setStarTime(starTime);
			mEasyBoxSql.setEndTime(endTime);
			StringBuilder skey=new StringBuilder();//字段
			skey.append(EasyBoxSqlUtil.getColumnSql(t,getTableName()));//组装原Sql
			StringBuilder sleftjoin =new StringBuilder();//leftjoin
			String tname = getTableName();
			if(mListLeftJoin!=null) {
				StringBuilder key = new StringBuilder();
				for (EasyBoxLeftJoin mLeftJoin : mListLeftJoin) {
					sleftjoin.append(" left join ");
					sleftjoin.append(mLeftJoin.getLeftJointabName());
					tname=tname+","+mLeftJoin.getLeftJointabName();
					sleftjoin.append(" on ");
					if(!mLeftJoin.getTabNameOn().contains(".")) {
						sleftjoin.append(getTableName());
						sleftjoin.append(".");
					}
					sleftjoin.append(mLeftJoin.getTabNameOn());
					sleftjoin.append("=");
					if(!mLeftJoin.getLeftJointabNameOn().contains(".")) {
						sleftjoin.append(mLeftJoin.getLeftJointabName());
						sleftjoin.append(".");
					}
					sleftjoin.append(mLeftJoin.getLeftJointabNameOn());
					sleftjoin.append(" ");
					for (String str : mLeftJoin.getLeftJointabField()) {
						key.append(",");
						if(!str.contains(".")) {
							key.append(mLeftJoin.getLeftJointabName());
							key.append(".");
						}
						key.append(str);
					}
				}
				skey.append(key);
			}
			mEasyBoxSql.setColumns(skey.toString());
			mEasyBoxSql.setSql("select "+skey+" from "+getTableName()+sleftjoin+getWhere(t,_and_or,_EqLike));
			mEasyBoxSql.setPage(page);
			mEasyBoxSql.setRows(rows);
			mEasyBoxSql.setOrderbykey(orderbykey);
			mEasyBoxSql.setOrderbytype(orderbytype);
			List<Map> listmap =cEasyBoxBaseDao.getAll(mEasyBoxSql);
			List<?> listobj = EasyBoxBeanUtil.ListMap2ListJavaBean(listmap, t.getClass());
			if(listobj.size()!=0) {
				Method m=listobj.get(0).getClass().getMethod("setConuntSize", Integer.class);
				m.invoke(listobj.get(0), cEasyBoxBaseDao.getAllCount(mEasyBoxSql));
			}else {
				return new ArrayList<>();
			}
			return (List<T>)listobj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  orderbykey 排序的字段
	 *  orderbytype 排序的方式
	 *  _and_or 多个条件之间采用and还是or
	 *  starTime 只查询给定时间之后的值，只支持时间戳
	 *  endTime 只查询给定时间之前的值，只支持时间戳
	 * */
	public List<T> getAllSplitTime(T t, Integer page, Integer rows, String orderbykey, EasyBoxSql.orderBy orderbytype, and_or _and_or, Long starTime, Long endTime){
		return getAllLeftJoin(t, page, rows, orderbykey, orderbytype, _and_or, EqLike.Eq_Like.auto,starTime, endTime, null);
	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  orderbykey 排序的字段
	 *  orderbytype 排序的方式
	 *   _and_or 多个条件之间采用and还是or
	 * */
	public List<T> getAll(T t, Integer page, Integer rows, String orderbykey, EasyBoxSql.orderBy orderbytype, and_or _and_or){
		return getAllSplitTime(t, page, rows, orderbykey, orderbytype, _and_or, null, null);
	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  orderbykey 排序的字段
	 *  orderbytype 排序的方式
	 * @return List<T>
	 * */
	public List<T> getAll(T t,Integer page,Integer rows,String orderbykey,EasyBoxSql.orderBy orderbytype){
		return getAll(t, page, rows,orderbykey,orderbytype,and_or.and);
	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *              @return List<T>
	 * */
	public List<T> getAll(T t,Integer page,Integer rows,and_or _and_or){
		return getAll(t, page, rows,null,null,_and_or);

	}
	/**
	 * 根据给定的条件查询表里面的数据
	 *  t 条件
	 *  page 当前页数 默认：1
	 *  rows 每一页的条数 默认10
	 *  @return List<T>
	 * */
	public List<T> getAll(T t,Integer page,Integer rows){
		return getAll(t, page, rows,null,null);

	}
	public enum and_or{
		and,or
	}
	/**
	 * 查询指定的where条件
	 *   obj 条件
	 *   _and_or 采用或者还是and,默认and
	 *   _EqLike 多个条件之间采用的策略,默认 auto
	 * 	               eq:等于
	 * 	              like:like '%t%'
	 * 	              auto:根据javabean自动装配
	 *  @return String
	 * */
	public String getWhere(T obj, and_or _and_or, EqLike.Eq_Like _EqLike)   {
		try {
			if(obj==null) {
				return "";
			}
			if(_and_or==null){
				_and_or=and_or.and;
			}
			if(_EqLike==null){
				_EqLike=EqLike.Eq_Like.auto;
			}
			Object value;// 执行结果
			StringBuilder sb = new StringBuilder();
			boolean andOr=false;
			Field[] field = obj.getClass().getDeclaredFields();
			for(Field mField :field) {
				if(mField.getAnnotation(EasyBoxNotParams.class)!=null){
					continue;
				}
				mField.setAccessible(true);
				value=mField.get(obj);
				if (value != null&&isBaseTypes(mField.getType())&&value.toString().trim().length()!=0){
					sb.append(" ");
					if(!andOr){
						sb.append(" where ");
						andOr=true;
					}else{
						sb.append(_and_or.toString());
					}
					sb.append(" ")
							.append(getTableName())
							.append(".")
							.append(EasyBoxSqlUtil.getColumnName(mField));
					switch (_EqLike){
						case eq:
							sb.append( "='" )
									.append( value)
									.append( "' ");
							break;
						case like:
							sb.append( " like '%" )
									.append(value)
									.append("%' ");
							break;
						case auto:
							EqLike mEqLike=mField.getAnnotation(EqLike.class);
							if(mEqLike!=null&&mEqLike.value()==EqLike.Eq_Like.like){
								sb.append( " like '%" )
										.append(value)
										.append("%' ");
							}else{
								sb.append( "='" )
										.append( value)
										.append( "' ");
							}
							break;
					}

				}
			}
			return sb.toString();
		} catch (Exception e) {
			return " where 1=1 ";
		}


	}
	/**
	 * 根据id获取数据
	 * 如果id为空则返回null
	 *  @return T
	 *  obj
	 * */
	public T getByid(T obj)  {
		try {
			String idvalue = getId(obj);
			if(EasyBoxStringUtil.isBlank(idvalue)) {
				return	null;
			}
			EasyBoxSql mEasyBoxSql = new EasyBoxSql();
			mEasyBoxSql.setSql("select "+EasyBoxSqlUtil.getColumnSql(obj,getTableName())+" from "+getTableName()+" where "+getIdKey()+"='"+idvalue+"'");
			List<Map> listmap = cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
			if(listmap.size()==0) {
				return null;
			}else {
				return (T) EasyBoxBeanUtil.map2JavaBean(listmap.get(0), obj.getClass());
			}
		} catch (Exception e) {
			return null;
		}

	}



	/**
	 * 添加非空数据
	 * id列可空
	 * 		如果该列的类型是String，则取UUID
	 * 		如果该列的类型是Long，则取当前时间戳
	 * 		如果该列的类型是Integer，则使用自增长的方式取数据库里面该列最大值+1
	 * 		其他类型抛出EasyBoxCheckException
	 * 如果是高并发项目，或者插入数据频繁的项目，不推荐Long或者Integer
	 * 这种情况下并不是安全的操作，很有可能抛出SqlException
	 *
	 * 	  obj
	 */
	public void add(T obj) throws NoSuchFieldException, IllegalAccessException {
		Class<?> clazz =obj.getClass();
		String mobj =getId(obj);
		if(EasyBoxStringUtil.isBlank(mobj)) {
			Field mField =clazz.getDeclaredField(getIdKey());
			mField.setAccessible(true);
			if(mField.getType().equals(java.lang.String.class)) {
				mField.set(obj, UUID.randomUUID().toString());
			}else {
				if(mField.getType().equals(java.lang.Long.class)) {
					mField.set(obj, System.currentTimeMillis());
				}else if(mField.getType().equals(java.lang.Integer.class)){
					mField.set(obj, getParamAddOne(getTableName(),mobj,cEasyBoxBaseDao));
				}else{
					throw new EasyBoxCheckException("未知的主键类型,请手动设置主键");
				}
			}
		}
		EasyBoxSecurity.checkJavaBean(obj, true);//验证参数是否合法
		Object valueKey;// 执行结果
		Object values;// 执行结果
		Field[] fields = clazz.getDeclaredFields(); // 拿到所有的字段值
		StringBuilder mkey = new StringBuilder();
		StringBuilder mvalue = new StringBuilder();

		for (Field field2 : fields) {
			field2.setAccessible(true);

			valueKey= EasyBoxSqlUtil.getColumnName(field2);
			values=field2.get(obj);
			if(values!=null) {
				mkey.append(valueKey);
				mkey.append(",");
				if(values instanceof Date) {
					Date da = (Date)values;
					EasyBoxDeCode mYlyDeCode=field2.getAnnotation(EasyBoxDeCode.class);
					if(mYlyDeCode!=null&&mYlyDeCode.dataLong()) {
						mvalue.append("'");
						mvalue.append(da.getTime());
						mvalue.append("',");
					}else {
						mvalue.append("TO_DATE('");
						mvalue.append(EasyBoxDateUtil.long2StringDate(da.getTime()));
						mvalue.append("', 'YYYY-MM-DD HH24:MI:SS'),");
					}
				}else {
					mvalue.append("'");
					mvalue.append(values.toString().trim());
					mvalue.append("',");
				}
			}
		}
		String sql = "INSERT INTO " + getTableName() + " (" + mkey.substring(0, mkey.length() - 1) + ") " + "VALUES("
				+ mvalue.substring(0, mvalue.length() - 1) + ")";
		EasyBoxSql mEasyBoxSql = new EasyBoxSql();
		mEasyBoxSql.setSql(sql);
		exeSql(mEasyBoxSql,getTableName(),getIdKey());
	}
	private  boolean isBaseTypes(Class<?> className) {
		if(isBaseType(className))return true;
		if (className.equals(java.lang.String.class) ||className.equals(java.math.BigDecimal.class)) {
			return true;
		}
		return false;
	}

	/**
	 * 更新主键以外所有的值，如果给定的值为空则将数据库更新为null
	 * 如果主键为空，抛出EasyBoxCheckException
	 *   t
	 * */
	public void updateAll(T t) throws IllegalAccessException, NoSuchFieldException {
		update(t, true);
	}
	/**
	 * 根据主键更新不为空的值
	 * 如果主键为空，抛出EasyBoxCheckException
	 * */
	public void updateBySelect(T t) throws IllegalAccessException, NoSuchFieldException {
		update(t, false);
	}

/**
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 * ====================================================================================
 *
 *
 * */
	public static final boolean isBaseType(Class clazz){
		if(clazz.isPrimitive()){
			return true;
		}
		try{
			clazz.getMethod("valueOf",String.class).invoke(null,"1");
				return true;
		}catch (Exception e){
			return false;
		}

	}


	private void update(T obj, boolean updateNull) throws IllegalAccessException, NoSuchFieldException {
		EasyBoxSecurity.checkJavaBean(obj, updateNull);//验证参数是否合法
		Object value ;//值
		Object valueKey;// key
		String idValue=getId(obj);
		if(EasyBoxStringUtil.isBlank(idValue)){
			throw new EasyBoxCheckException("更新的时候id不可为空");
		}
		Class<?> clazz =obj.getClass();
		Field[] fields = clazz.getDeclaredFields(); // 拿到所有的字段值
		StringBuilder sql = new StringBuilder("update " + getTableName() + " set ");
		boolean isOne=true;//是否是第一个
		for (Field field : fields) {
				field.setAccessible(true);
				if(!field.getName().equals(getIdKey())) {
					valueKey= field.get(obj);
					value=field.get(obj);
					if(value==null) {
						if(updateNull) {
							if(!isOne) {
								sql.append(",");
							}
							sql.append(valueKey);
							sql.append("=null");
							isOne=false;
						}
					}else {
						if(!isOne) {
							sql.append(",");
						}
						sql.append(valueKey);

						if(value instanceof Date) {
							Date da = (Date)value;
							sql.append("=");
							EasyBoxDeCode mYlyDeCode=field.getAnnotation(EasyBoxDeCode.class);
							if(mYlyDeCode!=null&&mYlyDeCode.dataLong()) {
								sql.append("'");
								sql.append(da.getTime());
								sql.append("'");
							}else {
								sql.append("TO_DATE('");
								sql.append(EasyBoxDateUtil.long2StringDate(da.getTime()));
								sql.append("', 'YYYY-MM-DD HH24:MI:SS')");
							}
						}else {
							sql.append("='");
							sql.append(value.toString().trim());
							sql.append("'");
						}


						isOne=false;
					}
				}
		}
		sql.append(" where ");
		sql.append(getIdKey());
		sql.append(" ='");
		sql.append(idValue);
		sql.append("'");
		EasyBoxSql mEasyBoxSql= new EasyBoxSql();
		mEasyBoxSql.setSql(sql.toString());
		exeSql(mEasyBoxSql,getTableName(),getIdKey(),getIdKey(),idValue);

	}


	public String getId(T t) throws IllegalAccessException, NoSuchFieldException {
		if(t==null){
			throw new EasyBoxCheckException("给定的数据不合法，不可为null");
		}
		Field mField = t.getClass().getDeclaredField(getIdKey());
		mField.setAccessible(true);
		return (String) mField.get(t);

	}
	/**
	 * 单纯的执行一条sql，不求返回值，除了主体sql所有的参数无效
	 * */
	public void exeSql(EasyBoxSql mEasyBoxSql,String tabName,String idKey,String whereKey,String whereKeyValue) throws NoSuchFieldException, IllegalAccessException {
		String sql = mEasyBoxSql.getSql().toUpperCase();
		if(sql.trim().indexOf("UPDATE")==0||sql.trim().indexOf("DELETE")==0||sql.trim().indexOf("INSERT")==0){
			if(cEasyBoxLogAutoConfiguration.isSave()){
				String key = EasyBoxBeanEache.getLogKey().get(tabName);
				if(!EasyBoxStringUtil.isBlank(key)&&!EasyBoxStringUtil.isBlank(whereKey)){
					EasyBoxSql mEasyBoxSqlLog =new EasyBoxSql();
					mEasyBoxSqlLog.setSql("select "+key+" from "+tabName+" where "+whereKey+"='"+whereKeyValue+"'");
					List<Map> listMap =cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
					for(Map mmap:listMap){
						cEasyBoxLogService.add(new EasyBoxLog(
								UUID.randomUUID().toString(),
								tabName,
								System.currentTimeMillis(),
								EasyBoxUserils.getUser().getUserName(),
								mmap.get(idKey).toString(),
								key
						));
					}

				}
			}
		}
		cEasyBoxBaseDao.exeSql(mEasyBoxSql);
	}
	/**
	 * 得到对应的参数的值+1
	 * */
	public Integer getParamAddOne(String tabName,String column,EasyBoxBaseDao cYlyBaseDao){
		try{
			String sql = "select nvl(max(to_number("+column+")),0)+1 from "+tabName;
			return cYlyBaseDao.getParamAddOne(sql);
		}catch (Exception e){
			throw new EasyBoxSqlException("给定字段"+column+"错误，该列不存在或者不能换换为number");
		}
	}
}
