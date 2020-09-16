package com.jxau.travel.service.impl;

import com.jxau.travel.dao.RouteDao;
import com.jxau.travel.dao.impl.RouteDaoImpl;
import com.jxau.travel.domain.PageBean;
import com.jxau.travel.domain.Route;
import com.jxau.travel.service.RouteService;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/16 - 9:41
 */
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置
        pb.setCurrentPage(currentPage);//当前页
        pb.setPageSize(pageSize);//页面大小

        int totalCount = routeDao.findTotalCount(cid);
        pb.setTotalCount(totalCount);//总记录数

        int start = (currentPage-1) * pageSize;
        List<Route> list = routeDao.findByCurrentPage(cid, start, pageSize);
        pb.setList(list);//当前页面显示的数据

        int totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize)+1;
        pb.setTotalPage(totalPage);//总页数

        return pb;//返回封装的pageBean
    }
}
