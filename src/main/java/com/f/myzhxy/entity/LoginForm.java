package com.f.myzhxy.entity;

import lombok.Data;

/**
* @project: ssm_sms
* @deprecated: 用户登录表单信息
*/

@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
