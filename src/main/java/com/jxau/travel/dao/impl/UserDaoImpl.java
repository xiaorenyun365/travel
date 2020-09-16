package com.jxau.travel.dao.impl;

import com.jxau.travel.dao.UserDao;
import com.jxau.travel.domain.User;
import com.jxau.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Auther: xiaory
 * @Date: 2020/9/14 - 14:34
 */
public class UserDaoImpl implements UserDao {
    // jdbc工具类
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //根据用户名查询用户信息
    @Override
    public User findByUserName(String username) {
        User user = null;
        try {   //ctrl+alt+t
            //1.定义sql语句
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    //保存用户
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),
                user.getName(),user.getBirthday(),
                user.getSex(),user.getTelephone(),
                user.getEmail(),user.getStatus(),user.getCode());
    }

    //根据激活码查询用户对象
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    //修改指定用户激活状态
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid=?";
        template.update(sql,user.getUid());
    }

    //根据用户名和密码查询用户
    @Override
    public User findByUserNameAndPassword(String username, String password) {
        User user = null;
        try {   //ctrl+alt+t
            //1.定义sql语句
            String sql = "select * from tab_user where username=? and password=?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {

        }
        return user;
    }
}
