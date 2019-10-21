package org.yly.framework.easybox.base.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.yly.framework.easybox.base.log.config.EasyBoxLogAutoConfiguration.EasyBoxLogAutoConfiguration;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
@Configuration
@ConfigurationProperties(prefix = EasyBoxLogAutoConfiguration)
public class EasyBoxLogAutoConfiguration {


    public static final String EasyBoxLogAutoConfiguration ="easy.box.log";
    /**
     * 是否加载简单操作日志
     * */
    private boolean save =false;
}
