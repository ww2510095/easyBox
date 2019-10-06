package org.yly.framework.easybox.cache.bean;

import java.util.HashMap;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/6 0006
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxTabMap extends HashMap<String,Long> {

    public void  addVersion(String tabName){
        Long version =get(tabName);
        if(version==null){
            put(tabName,0L);
        }else{
            put(tabName,version+1);
        }
    }
}
