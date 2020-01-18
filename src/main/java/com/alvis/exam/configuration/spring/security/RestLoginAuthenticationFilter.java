package com.alvis.exam.configuration.spring.security;

import com.alvis.exam.configuration.property.CookieConfig;

import com.alvis.exam.utility.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 登录参数序列化
 *
 * @author alvis
 */

//获取登录请求传过来的参数
public class RestLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RestLoginAuthenticationFilter.class);

    public RestLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
    }

    //登录认证
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest; //请求的token
        try (InputStream is = request.getInputStream()) {
            AuthenticationBean authenticationBean = JsonUtil.toJsonObject(is, AuthenticationBean.class); //封装的请求信息(需要反序列化,因为Spring Security不支持json格式)
            request.setAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER, authenticationBean.isRemember()); //登录信息和记住状态设置进到request里
            authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUserName(), authenticationBean.getPassword());//账号密码存入到authRequest
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest); //放入到token中的数据
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    //UserDetailsService底层是账号密码的验证
    void setUserDetailsService(UserDetailsService userDetailsService) {
        //应该是记住密码的延时 (CookieConfig.getName()应该是它的标识(key)?,userDetailsService是跑到异常信息)
        RestTokenBasedRememberMeServices tokenBasedRememberMeServices = new RestTokenBasedRememberMeServices(CookieConfig.getName(), userDetailsService);
        tokenBasedRememberMeServices.setTokenValiditySeconds(CookieConfig.getInterval()); //应该是重新设置延时时间
        setRememberMeServices(tokenBasedRememberMeServices); //setRememberMeServices 设置不为空的tokenBasedRememberMeServices
    }


    //账号密码验证的token
    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request)); //把请求中的数据设置到(authRequest)token中
    }

}
