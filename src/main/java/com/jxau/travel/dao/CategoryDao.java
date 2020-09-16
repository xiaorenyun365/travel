package com.jxau.travel.dao;

import com.jxau.travel.domain.Category;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 17:11
 */
public interface CategoryDao {
    //查询所有
    List<Category> findAll();
}
