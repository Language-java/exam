package com.alvis.exam.configuration.spring.wx;

import com.alvis.exam.base.SystemCode;
import com.alvis.exam.configuration.spring.security.RestUtil;
import com.alvis.exam.domain.User;
import com.alvis.exam.domain.UserToken;
import com.alvis.exam.service.UserService;
import com.alvis.exam.service.UserTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component

public class TokenHandlerInterceptor implements HandlerInterceptor {
    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    private final static ThreadLocal<UserToken> USER_TOKEN_THREAD_LOCAL = new ThreadLocal<>();
    private final UserTokenService userTokenService;
    private final UserService userService;

    @Autowired
    public TokenHandlerInterceptor(UserTokenService userTokenService, UserService userService) {
        this.userTokenService = userTokenService;
        this.userService = userService;
    }

    /**
     * 调用封装的token中的错误信息
     * @param request   获取请求中的token
     * @param response  响应封装在RestUtil中的对应的异常数据
     * @param handler   传递消息Message
     * @return          返回错误信息
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        if (StringUtils.isBlank(token)) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        if (token.length() != 36) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        UserToken userToken = userTokenService.getToken(token);
        if (null == userToken) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        Date now = new Date();
        User user = userService.getUserByUserName(userToken.getUserName());
        if (now.before(userToken.getEndTime())) {
            USER_THREAD_LOCAL.set(user);
            USER_TOKEN_THREAD_LOCAL.set(userToken);
            return true;
        } else {   //refresh token
            UserToken refreshToken = userTokenService.insertUserToken(user);
            RestUtil.response(response, SystemCode.AccessTokenError.getCode(), SystemCode.AccessTokenError.getMessage(), refreshToken.getToken());
            return false;
        }
    }

    public static ThreadLocal<User> getUserThreadLocal() {
        return USER_THREAD_LOCAL;
    }

    public static ThreadLocal<UserToken> getUserTokenThreadLocal() {
        return USER_TOKEN_THREAD_LOCAL;
    }
}
