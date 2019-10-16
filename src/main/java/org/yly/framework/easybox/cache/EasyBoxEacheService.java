package org.yly.framework.easybox.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.yly.framework.easybox.base.log.EasyBoxLog;
import org.yly.framework.easybox.base.log.EasyBoxLogService;
import org.yly.framework.easybox.base.log.config.EasyBoxLogAutoConfiguration;
import org.yly.framework.easybox.base.log.config.EasyBoxLogParam;
import org.yly.framework.easybox.mybatis.EasyBoxSqlException;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 缓存服务核心类
 * */
public class EasyBoxEacheService {

    private EasyBoxLogService cEasyBoxLogService;
    private EasyBoxLogAutoConfiguration cEasyBoxLogAutoConfiguration;
    private EasyBoxBaseDao cEasyBoxBaseDao;
    public EasyBoxEacheService(EasyBoxBaseDao mEasyBoxBaseDao,
                               EasyBoxLogService mEasyBoxLogService,
                               EasyBoxLogAutoConfiguration mEasyBoxLogAutoConfiguration
                               ){
        cEasyBoxLogAutoConfiguration=mEasyBoxLogAutoConfiguration;
        cEasyBoxLogService=mEasyBoxLogService;
        cEasyBoxBaseDao=mEasyBoxBaseDao;

    }


    /**
     * 获取数据表的内容
     * */
   public  List<Map> getAll(EasyBoxSql mEasyBoxSql,String tabName){
       mEasyBoxSql.setCount(false);
       return (List<Map>)EasyBoxBeanEache.getEacheMap()
               .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.GETALL,cEasyBoxBaseDao);
   }
   public Integer getAllCount(EasyBoxSql mEasyBoxSql,String tabName){
       mEasyBoxSql.setCount(true);
       return Integer.valueOf(EasyBoxBeanEache.getEacheMap()
               .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.GETALLCOUNT,cEasyBoxBaseDao).toString());
   }
    /**
     * 单纯的执行一条sql，不求返回值，除了主体sql所有的参数无效
     * */
    public void exeSql(EasyBoxSql mEasyBoxSql,String tabName,String idKey,String whereKey,String whereKeyValue) throws NoSuchFieldException, IllegalAccessException {
        String sql = mEasyBoxSql.getSql().toUpperCase();
        if(sql.trim().indexOf("UPDATE")==0||sql.trim().indexOf("DELETE")==0||sql.trim().indexOf("INSERT")==0){
            if(EasyBoxStringUtil.isBlank(mEasyBoxSql.getUserId())){
                throw new EasyBoxCheckException("sql执行错误，操作人不可为空");
            }
            if(cEasyBoxLogAutoConfiguration.isSave()){
                String key = EasyBoxLogParam.getLogKey().get(tabName);
                if(!EasyBoxStringUtil.isBlank(key)&&!EasyBoxStringUtil.isBlank(whereKey)){
                    EasyBoxSql mEasyBoxSqlLog =new EasyBoxSql();
                    mEasyBoxSqlLog.setSql("select "+key+" from "+tabName+" where "+whereKey+"='"+whereKeyValue+"'");
                    List<Map> listMap =cEasyBoxBaseDao.exeSelectSql(mEasyBoxSql);
                    for(Map mmap:listMap){
                        cEasyBoxLogService.add(new EasyBoxLog(
                                UUID.randomUUID().toString(),
                                tabName,
                                System.currentTimeMillis(),
                                mEasyBoxSql.getUserId(),
                                mmap.get(idKey).toString(),
                                key
                        ));
                    }

                }
            }
            EasyBoxBeanEache.getEacheMap().addVersion(tabName);
        }
         cEasyBoxBaseDao.exeSql(mEasyBoxSql);
    }
    /**
     * 查询sql，所有参数有效
     * */
    public List<Map> exeSelectSql(EasyBoxSql mEasyBoxSql,String tabName){
        return (List<Map>)EasyBoxBeanEache.getEacheMap()
                .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.EXESELECTSQL,cEasyBoxBaseDao);
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
