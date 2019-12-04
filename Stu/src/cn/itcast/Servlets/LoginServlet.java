package cn.itcast.Servlets;

import cn.itcast.Dao.Admin;
import cn.itcast.Dao.AdminDao;
//import com.alibaba.druid.sql.visitor.SQLASTOutputVisitorUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //设置请求参数格式
        request.setCharacterEncoding("utf-8");

        //获取验证码(用户输入验证码)
        String c_code = request.getParameter("verifycode");

        //从session中取系统随机的验证码
        HttpSession session  = request.getSession();
        String s_code = (String) session.getAttribute("Servercode");

        //判断用户输入的验证码是否正确
        if(!c_code.equalsIgnoreCase(s_code)) {
            //如果验证不正确 跳转到登录页面 一次请求
            request.setAttribute("msg","验证码有误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        //如果验证码正确
        String username = request.getParameter("username");
        String password = request.getParameter("password");

       //去数据中查找
        Admin admin = new Admin(username,password);

        //创建AdminDao
        AdminDao dao = new AdminDao();
        Admin result = dao.findAdminByNameAndPswd(admin);

        //判断
        if(result != null) {
            //跳转到欢迎页面
            request.setAttribute("admin",result);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } else {
            System.out.println("222222222");
            //用户名或密码有误 跳转到登录页面
            request.getSession().setAttribute("msg","用户名或密码有误");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
