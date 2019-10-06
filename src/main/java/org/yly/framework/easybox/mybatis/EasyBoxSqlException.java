package org.yly.framework.easybox.mybatis;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/3 0003
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxSqlException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EasyBoxSqlException(String msg) {
        super(msg);
    }

    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String toString() {
        return "Sql错误:" + getMessage();
    }
}
