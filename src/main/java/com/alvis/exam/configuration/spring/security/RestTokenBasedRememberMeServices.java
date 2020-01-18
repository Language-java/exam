package com.alvis.exam.configuration.spring.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

/**
 * 记住我，Cookie
 *
 * @author alvis
 */
public class RestTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

    public RestTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService); //验证时的key,userDetailsService是异常信息
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        return (boolean) request.getAttribute(DEFAULT_PARAMETER); //返回获取的登录记住状态
    }

}
