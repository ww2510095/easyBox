package org.yly.framework.easybox.cache.bean;

import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.cache.EasyBoxEachMapKey;
import org.yly.framework.easybox.mybatis.EasyBoxSqlException;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;
import org.yly.framework.easybox.utils.EasyBoxGsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/6 0006
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxMap extends HashMap<String ,EasyBoxSqlVersion> {


    public void addVersion(String tabName){
        String[] tabs =tabName.split(",");
        for(String str:tabs){
            EasyBoxBeanEache.getTabEasyBoxMap().addVersion(str);
        }
    }

    public Object getData(String tabName,String key,EasyBoxEachMapKey mEasyBoxEachMapKey,EasyBoxBaseDao cYlyBaseDao) {
        EasyBoxSqlVersion mEasyBoxSqlVersion =get(key);
        if(mEasyBoxSqlVersion==null){
            synchronized (key) {
                Object obj = getDate(mEasyBoxEachMapKey, key,cYlyBaseDao);
                mEasyBoxSqlVersion = new EasyBoxSqlVersion();
                mEasyBoxSqlVersion.setVersion(0L);
                mEasyBoxSqlVersion.setData(EasyBoxGsonUtil.toJsonString(obj));
                put(key, mEasyBoxSqlVersion);
                return obj;
            }
        }
        Long vcode=0L;
        String[] tabs =tabName.split(",");
        for(String str:tabs){
            Long tvcode = EasyBoxBeanEache.getTabEasyBoxMap().get(str);
            if(tvcode==null){
                tvcode=0L;
                EasyBoxBeanEache.getTabEasyBoxMap().put(str,tvcode);
            }
            if(tvcode>vcode){
                /**
                 * 取最高版本，因为有leftjoin这种情况
                 * */
                vcode=EasyBoxBeanEache.getTabEasyBoxMap().get(str);
            }
        }
        if(vcode>mEasyBoxSqlVersion.getVersion()){
            synchronized (key){
                Object obj = getDate(mEasyBoxEachMapKey,key,cYlyBaseDao);
                mEasyBoxSqlVersion.setVersion(vcode);
                mEasyBoxSqlVersion.setData(EasyBoxGsonUtil.toJsonString(obj));
                put(key,mEasyBoxSqlVersion);
                return obj;
            }
        }
        if(mEasyBoxEachMapKey==EasyBoxEachMapKey.GETALLCOUNT){
            /**
             * 如果是计数直接返回
             * */
            return mEasyBoxSqlVersion.getData();
        }else{
            /**
             * 如果是数据将他转为map
             * */
            return EasyBoxGsonUtil.fromJsonList(mEasyBoxSqlVersion.getData(),Map.class);
        }

    }
    private Object getDate(EasyBoxEachMapKey mEasyBoxEachMapKey, String mStr,EasyBoxBaseDao cYlyBaseDao){
        EasyBoxSql mEasyBoxSql = EasyBoxGsonUtil.fromJsonString(mStr,EasyBoxSql.class);
        switch (mEasyBoxEachMapKey){
            case GETALL:
               return cYlyBaseDao.getAll(mEasyBoxSql);
            case GETALLCOUNT:
                return cYlyBaseDao.getAllCount(mEasyBoxSql);
            case EXESELECTSQL:
                return cYlyBaseDao.exeSelectSql(mEasyBoxSql);
        }
        throw new EasyBoxSqlException("更新缓存时出现未知错误，请检查使用方法是否正确");
    }
}
