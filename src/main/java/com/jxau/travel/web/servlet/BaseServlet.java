package com.jxau.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 12:44
 */

//该servlet不用被访问
public class BaseServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求路径
        String uri = req.getRequestURI(); //虚拟路径： travel/user/add
        //3.获取方法名
        String methodName = uri.substring(uri.lastIndexOf('/')+1);

        //3.获取方法对象Method
        //this---谁调用我？我就代表谁
        //UserServlet的对象

        Method method = null;
        try {
            method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(this, req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /*
    因为Servlet的方法被protected修饰，所以我们要暴力反射，忽略权限修饰符,
     getDeclaredMethod();
     method.setAccessible(true);
     但这不是最好的，因为有的方法不想被访问
    要访问的直接用public修饰即可
    */

    //直接将传入的对象序列化为json，并写回客户端
    public void writerValue(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),obj);
    }

    //直接将传入的对象序列化为json，返回
    public String writerValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(obj);
        return s;
    }

}
