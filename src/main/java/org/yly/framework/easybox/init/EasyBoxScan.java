package org.yly.framework.easybox.init;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;
import org.yly.framework.easybox.init.scan.EasyBoxInit;

import java.lang.annotation.*;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(EasyBoxInit.class)
@MapperScan("org.yly.framework.easybox.mybatis.*")//扫描
public @interface EasyBoxScan {
    /**
     * 扫描的javaBean路径
     * */
    String[] beanUrl() default {};
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
