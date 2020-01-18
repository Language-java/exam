package com.alvis.exam.configuration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.List;


/**
 * @author alvis
 */
@ConfigurationProperties(prefix = "system")  //读取类的前缀，给它自动注入
@Data   //提供有get和set方法
public class SystemConfig {
    private String fdfsNgix;
    private PasswordKeyConfig pwdKey;
    private List<String> securityIgnoreUrls;
    private WxConfig wx;
}
