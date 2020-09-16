package com.jxau.travel.dao.impl;

import com.jxau.travel.dao.RouteDao;
import com.jxau.travel.domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/16 - 9:41
 */
public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate();

    @Override
    public int findTotalCount(int cid) {
        String sql = "select count(1) from tab_route where cid=?";
        return template.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public List<Route> findByCurrentPage(int cid, int start, int pageSize) {
        String sql = "select * from tab_route where cid=? limit ?, ?";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);
    }
}
