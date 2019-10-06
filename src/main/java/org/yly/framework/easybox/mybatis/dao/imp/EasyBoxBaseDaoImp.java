package org.yly.framework.easybox.mybatis.dao.imp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.security.EasyBoxSecurity;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxBaseDaoImp {
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 根据指定非空字段查询数据
     */
    public String getAll(EasyBoxSql mEasyBoxSql) {
        return execSelectSql(mEasyBoxSql, true);
    }
    public String getAllCount(EasyBoxSql mEasyBoxSql) {
        String timeWhere = "";
        if (!EasyBoxStringUtil.isBlank(mEasyBoxSql.getTimeKey())) {
            timeWhere = "where "
                    + mEasyBoxSql.getStarTime() + "<nvl(" + mEasyBoxSql.getTimeKey() + ",9999999999999) and nvl("
                    + mEasyBoxSql.getTimeKey() + ",-1)<" + mEasyBoxSql.getEndTime();
        }
        return "select nvl(count(1),0) from (" + mEasyBoxSql.getSql() + ")  " + timeWhere;
    }

    public String exeSql(EasyBoxSql mEasyBoxSql) {
        /**
         * sql安全校验
         * */
        EasyBoxSecurity.sqlValueInspect(mEasyBoxSql.getSql(),"错误,参数or字符串后面不可包含等同表达式，如: or 1=1");
        logger.info(mEasyBoxSql.getSql());
        return mEasyBoxSql.getSql();
    }

    /**
     * 执行一条查询的sql
     */
    public String exeSelectSql(EasyBoxSql mEasyBoxSql) {
        return execSelectSql(mEasyBoxSql, false);
    }

    /**
     * 查询指定字段的值+1
     */
    public String getParamAddOne(String sql) {
        return sql;
    }

/***
 *===================================================
 *===================================================
 *===================================================
 *===================================================
 *===================================================
 *
 * */
    /**
     * 执行一条查询的sql
     */
    private String execSelectSql(EasyBoxSql mEasyBoxSql, boolean isPage) {
        Integer page = mEasyBoxSql.getPage();
        Integer rows = mEasyBoxSql.getRows();
        if (isPage) {
            /**
             * 是否强制分页
             * */
            if (page == null) {
                page = 1;
            }
            if (rows == null) {
                rows = 10;
            }
        }

        String sql = mEasyBoxSql.getSql();
        if (!EasyBoxStringUtil.isBlank(mEasyBoxSql.getOrderbykey())) {
            sql = sql + " order by " + mEasyBoxSql.getOrderbykey() + " " + mEasyBoxSql.getOrderbytype();
        }
        String mColumns1 = mEasyBoxSql.getColumns();
        String mColumns2 = "a.*";
        if (EasyBoxStringUtil.isBlank(mColumns1)) {
            mColumns1 = "*";
        } else {
            mColumns2 = mColumns1;
        }
        if (!EasyBoxStringUtil.isBlank(mEasyBoxSql.getTimeKey())) {
            /**
             * 需要关联时间
             * */
            if (mEasyBoxSql.getStarTime() == null) {
                mEasyBoxSql.setStarTime(0L);
            }
            if (mEasyBoxSql.getEndTime() == null) {
                mEasyBoxSql.setEndTime(System.currentTimeMillis());
            }
            String timeWhere = "where "
                    + mEasyBoxSql.getStarTime() + "<nvl(" + mEasyBoxSql.getTimeKey() + ",9999999999999) and nvl("
                    + mEasyBoxSql.getTimeKey() + ",-1)<" + mEasyBoxSql.getEndTime();
            if (page != null) {
                sql = "select " + mColumns1 + ",rownum1A from (select " + mColumns2 + ",rownum rownum1A from (" + sql + ")a " + timeWhere + " )where"
                        + " rownum1A> " + ((page - 1) * rows) + " and rownum1A <= " + (page * rows);
            } else {
                sql = " select " + mColumns1 + " from (" + sql + ")" + timeWhere;
            }
        } else {
            /**
             * 不需要关联时间
             * */
            if (page != null) {
                sql = "select " + mColumns1 + ",rownum1A from (select " + mColumns2 + ",rownum rownum1A from (" + sql + ")a  )where"
                        + " rownum1A> " + ((page - 1) * rows) + " and rownum1A <= " + (page * rows);
            }
        }
        /**
         * sql安全校验
         * */
        EasyBoxSecurity.sqlValueInspect(sql);
        logger.info(sql);
        return sql;
    }
}