package org.yly.framework.easybox.mybatis.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.yly.framework.easybox.base.RequestType;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.spring.bean.EasyBoxAspectException;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/10 0010
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 *环绕建言
 * 此类的子类将自动代理环绕建言功能
 * 统一做一些切面处理，如参数校验等等
 */
public abstract class EasyBoxBaseController<T extends EasyBoxBaseBean> {

    protected abstract EasyBoxBaseService<T> getService();
    /**
     * 成功，只返回提示信息
     * */
    protected RequestType sendTrueMsg(String msg){
        return getRequestType(null, msg, null);
    }
    /**
     * 成功，只返回数据
     * */
    protected RequestType sendTrueData(Object obj){
        return getRequestType(null, null, obj);
    }
    /**
     * 失败，返回提示信息
     * */
    protected RequestType sendFalse(String msg){
        return getRequestType(-1, msg, null);
    }
    /**
     * 失败，返回提示信息，带着stat
     * */
    protected RequestType sendFalse(String msg,int status){
        return getRequestType(status, msg, null);
    }

    private RequestType getRequestType(Integer code,String msg,Object data){
        RequestType reqt = new RequestType();
        //状态码
        if(code!=null){
            reqt.setCode(code);
        }else {
            reqt.setCode(0);
        }
        //提示信息
        if(msg!=null){
            reqt.setMsg(msg);
        }
        //数据
        reqt.setData(data);
        //时间
        reqt.setTimestamp(System.currentTimeMillis());

        return reqt;
    }
    @RequestMapping("save")
    public RequestType save(T t)throws Exception {
         EasyBoxBaseService.newSaveType mnewSaveType= getService().save(t);
        return sendTrueMsg((mnewSaveType== EasyBoxBaseService.newSaveType.add?"添加":"修改")+"成功");
    }
    @RequestMapping("deleteById")
    public RequestType delete(T t,EasyBoxAspectException mEasyBoxAspectException)throws Exception {
        getService().deleteByParam(getService().getIdKey(),getService().getId(t));
        return sendTrueMsg("删除成功");
    }
    @RequestMapping("list")
    public RequestType list(T t,Integer page,Integer rows,EasyBoxAspectException mEasyBoxAspectException) {
        return sendTrueData(getService().getAll(t,page,rows));
    }

}
