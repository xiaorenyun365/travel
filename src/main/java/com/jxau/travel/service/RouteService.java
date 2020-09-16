package com.jxau.travel.service;

import com.jxau.travel.domain.PageBean;
import com.jxau.travel.domain.Route;

/**
 * 线路service
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
}
