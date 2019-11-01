package org.yly.framework.easybox.utils.exception;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 效验错误
 */
public class EasyBoxCheckException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EasyBoxCheckException(String msg) {
        super(msg);
    }

    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String toString() {
        return "error:" + getMessage();
    }
}
