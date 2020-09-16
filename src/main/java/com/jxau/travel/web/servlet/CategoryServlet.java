package com.jxau.travel.web.servlet;

import com.jxau.travel.domain.Category;
import com.jxau.travel.service.CategoryService;
import com.jxau.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 14:04
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    
    private CategoryService categoryService = new CategoryServiceImpl();

    //查询所有
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> list = categoryService.findAll();
        //序列化json返回
        writerValue(list,response);

    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
