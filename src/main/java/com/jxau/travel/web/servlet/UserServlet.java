package com.jxau.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxau.travel.domain.ResultInfo;
import com.jxau.travel.domain.User;
import com.jxau.travel.service.UserSevice;
import com.jxau.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 14:04
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    //声明UserService业务对象
    private UserSevice userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("register")){
            register(request,response);
        } else if (uri.contains("active")){
            active(request,response);
        } else if (uri.contains("login")){
            login(request,response);
        } else if (uri.contains("findOne")){
            findOne(request,response);
        } else if (uri.contains("exist")){
            exist(request,response);
        }
    }


    //校验验证码
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 校验验证码
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //保证验证码只用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json数据写回到客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
    }
      //注册
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 校验验证码
       String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //保证验证码只用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json数据写回到客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象--BeanUtils
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service层完成注册
        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();

        //4.响应结果
        if(flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败，用户名已被占用！");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null){
            boolean flag = userService.active(code);
            //判断标记
            String msg = null;
            if (flag){
                String contextPath = request.getContextPath();
                msg = "激活成功，请<a href='"+contextPath+"/login.html'>登录</a>";
            }else {
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    // 登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        checkCode(request,response);

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);//将数据封装到对象值中
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User u = userService.login(user);

        //判断用户
        // 1.不存在
        ResultInfo info = new ResultInfo();
        if (u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //2.存在，判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())){
            //用户未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活该账号，请登入注册邮箱进行激活");request.getSession().setAttribute("user",u);
        }
        //登录成功
        if (u != null && "Y".equals(u.getStatus())){
            info.setFlag(true);
            request.getSession().setAttribute("user",u);
        }

        // 响应数据info,ajax做出提示
        //writerValue(info,response);
       response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),info);

    }

    // 查找登录的用户信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");

        // 响应数据info,ajax做出提示
        //writerValue(user,response);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),user);
    }

    // 退出
    public void exist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session ， 清除用户信息
        request.getSession().invalidate();
        //2.跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

}