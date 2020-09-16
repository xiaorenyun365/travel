package com.jxau.travel.dao;

import com.jxau.travel.domain.Route;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/16 - 9:41
 */
public interface RouteDao {
    //根据cid查询该分类的所有记录数
    int findTotalCount(int cid);

    //根据cid,star,pageSize; 查询当前页面的数据集合
    List<Route> findByCurrentPage(int cid, int start, int pageSize);
}
