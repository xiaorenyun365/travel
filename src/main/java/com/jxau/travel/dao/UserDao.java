package com.jxau.travel.dao;

import com.jxau.travel.domain.User;

/**
 * @Auther: xiaory
 * @Date: 2020/9/14 - 14:35
 */
public interface UserDao {

    //根据用户名查询用户信息
    public User findByUserName(String username);

    //保存用户
    public void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUserNameAndPassword(String username, String password);
}
