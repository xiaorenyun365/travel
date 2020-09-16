package com.jxau.travel.dao.impl;

import com.jxau.travel.dao.CategoryDao;
import com.jxau.travel.domain.Category;
import com.jxau.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 17:14
 */
public class CategoryDaoImpl implements CategoryDao{

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //查询所有
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
