package com.jxau.travel.service.impl;

import com.jxau.travel.dao.UserDao;
import com.jxau.travel.dao.impl.UserDaoImpl;
import com.jxau.travel.domain.User;
import com.jxau.travel.service.UserSevice;
import com.jxau.travel.util.MailUtils;
import com.jxau.travel.util.UuidUtil;

/**
 * @Auther: xiaory
 * @Date: 2020/9/14 - 14:33
 */
public class UserServiceImpl implements UserSevice{

    private UserDao userDao = new UserDaoImpl();

    //  注册用户
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户信息
        User u = userDao.findByUserName(user.getUsername());
        //2.判断该用户是否已存在
        if (u != null){
            return false;      //用户存在，注册失败
        }
        //3.保存用户信息
        //设置激活码 和 激活码状态，唯一字符串UUID
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);

        //4.发送激活邮件
        //邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活[雪花旅游社]</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    //激活用户
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    //登录
    @Override
    public User login(User user) {
        return userDao.findByUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
