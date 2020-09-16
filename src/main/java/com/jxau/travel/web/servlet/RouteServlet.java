package com.jxau.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxau.travel.domain.PageBean;
import com.jxau.travel.domain.Route;
import com.jxau.travel.service.RouteService;
import com.jxau.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: xiaory
 * @Date: 2020/9/16 - 9:40
 */
@WebServlet("/route/*")
public class RouteServlet extends HttpServlet {
    //声明Service业务对象
    private RouteService routeService = new RouteServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("pageQuery")){
            pageQuery(request,response);
        }
    }

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //处理参数
        int currentPage = 0;//当前页码，如果不传递，默认为第1页
        if (currentPageStr != null && currentPageStr.length()>0 ){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        int pageSize = 0;//每页显示的条数，如果不传递，默认显示5条
        if (pageSizeStr != null && pageSizeStr.length()>0 ){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        int cid = 0;
        if (cidStr != null && cidStr.length()>0 ){
            cid = Integer.parseInt(cidStr);
        }

        //调用service层查询pageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);
        //将pageBean对象序列化为json,返回到客户端
        //writerValue(pb,response);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),pb);
    }


}
