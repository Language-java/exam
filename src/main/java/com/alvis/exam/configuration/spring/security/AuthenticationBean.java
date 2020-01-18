package com.alvis.exam.configuration.spring.security;

import lombok.Data;

/**
 * 账号密码状态
 *
 * @author alvis
 */

@Data
public class AuthenticationBean {
    private String userName;
    private String password;
    private boolean remember; //记住密码的选项

}
