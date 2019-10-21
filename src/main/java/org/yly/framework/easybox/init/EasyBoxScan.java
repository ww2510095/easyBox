package org.yly.framework.easybox.init;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.yly.framework.easybox.init.scan.EasyBoxInit;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(EasyBoxInit.class)
@MapperScan("org.yly.framework.easybox.mybatis.*")//扫描
@ServletComponentScan
public @interface EasyBoxScan {
    /**
     * 扫描的javaBean路径
     * 次参数可以为您纠正一些没必要的错误，不建议使用空值
     * 如果您开启了简单的日志功能，那么范围只限于扫描下的范围
     * 如果您开启了自动创建表的功能，同样表的创建也只限于扫描的返回
     * 建议您使用您的顶级包名，如:org.yly.framework.easybox
     * 值得注意的是系统的包自带次功能，
     * 不建议采用com这种最外层的包名，这样在启动的时候将浪费您大量的时间
     * */
    String[] beanUrl() default {};
    /**
     * easyBox允许在启动的时候自动创建表，这样您将不必在表的创建上花费时间
     * 当然，如果您对表有更高的要求，如分表分库什么的那还是需要您手动更改
     * */
    boolean  createTab() default false;
    /**
     * EasyBox的策略
     * */
    EasyBoxStrategy strategy() default EasyBoxStrategy.SPEED;
     enum EasyBoxStrategy{
         /**
          * 次策略将开启EasyBox的所有组件功能
          * 高并发，高速度的策略
          * 但会占用大量资源，建议采用Redis
          * 如果您的服务器项目并发量特别大，建议采用Redis集群
          * */
         SPEED,
         /**
          * 次策略以节约资源为主，
          * 不管在并发还是在速度上相比起SPEED都要差
          * 但，资源将最大化利用，适合资源紧缺的中小型项目
          * */
         RESOURCES
    }
}
