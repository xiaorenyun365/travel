package com.jxau.travel.service;

import com.jxau.travel.domain.User;

/**
 * @Auther: xiaory
 * @Date: 2020/9/14 - 14:33
 */
public interface UserSevice {
    //  注册用户
    boolean regist(User user);
    //激活用户
    boolean active(String code);
    //登录
    User login(User user);
}
