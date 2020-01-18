package com.alvis.exam.configuration.spring.security;

import com.alvis.exam.base.SystemCode;
import com.alvis.exam.domain.User;
import com.alvis.exam.domain.UserEventLog;
import com.alvis.exam.event.UserEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 用户退出
 *
 * @author alvis
 */
@Component
@AllArgsConstructor
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();  //获取cookies
        System.out.println("cookies:" + cookies);

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + "------" + cookie.getValue()); //获取cookie
            }
        }
        if (authentication != null) {
            User user = ((AuthUser) authentication.getPrincipal()).getUser();
            UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
            userEventLog.setContent(user.getUserName() + " 退出了学之思考试系统");
            eventPublisher.publishEvent(new UserEvent(userEventLog));
            RestUtil.response(response, SystemCode.OK);
        }
    }
}