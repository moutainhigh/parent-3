package com.schcilin.common_server.dto.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 14:39
 * @Version 1.0
 * @des: 用户dto
 */
public class SysUserDTO implements Serializable {
    private static final long serialVersionUID = 3253109721705529638L;
    private Long id;
    /**
     * 登录账号
     */
    @NotNull(message = "用户名不能为空")
    @Length(max = 20,message = "用户名长度必须在{max}个字符内")
    private String username;
    /**
     * 名称
     */
    @NotNull(message = "姓名不能为空")
    @Length(max = 20,message = "姓名长度必须为{max}个字符内")
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;
    /**
     * 用户类型
     */
    private String type;
}
