package cn.itcast.Servlets;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/CheckCodeServle")
public class CheckCodeServle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
        * 表单提交的过程
        * 1.用户名 密码 验证码(xyzb) 提交 = 一个表单四个内容
        * 2. 表单提交 action = "/虚拟目录/loginservlet/
        * ---------------(loginservlet)------------
        *   1 获取表单参数
        *       1.设置编码格式 (获取的可能是中文)
        *       2.获取请求参数
        *           1.验证码
        *              1.判断验证码是否正确 (验证码跟谁比?跟实际生成的验证码对比 )
        *                   1. 生成的验证码要先存入session中
        *                   2. 在当前loginservlet中获取实际生成的验证码
        *                   3. 比较
        *                          1.如果不相同 重新回到登录页面
        *                          2.如果相同 获取用户名 密码
        *                               1.如果用户名密码不存在 重写登录
        *                               2.如果存在 就进入欢迎页面
        *
        *           2. 用户名 (从用户的体验来说 先验证验证码好点 如果验证码不对 就直接让他重写输入)
        *           3. 密码
        *
        *
        *
        * */



        //创建图片 存储验证码 width 跟 height 跟参数一起定义
        int width = 100;
        int height = 60;
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取绘画工具对象
        Graphics pen = img.getGraphics();
        pen.setColor(Color.pink);
        pen.fillRect(0,0,width,height);

        //边框
        pen.setColor(Color.black);
        pen.drawRect(0,0,width-1,height-1);

        //书写内容 横向 纵向
        pen.setFont(new Font("楷体",Font.PLAIN,30));
        //小写字母变大写 abc = ABC ctrl + shift + Y
        String chars = "abcdefghijklmnopqrstuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        //要把生成的验证码跟用户输入的验证码对比 需要拼成字符串
        StringBuilder sb = new StringBuilder();

        Random r = new Random();
        //随机生成四位验证码
        for (int i = 1; i < 5; i++) {
            int index = r.nextInt(chars.length());
            char c = chars.charAt(index);
            String var = c + "";
            pen.drawString(var,width/5*i-8,height/2+10);

             sb.append(var);
        }

        //这是页面的请求 用户的请求是loginservlet请求 不同请求之间共享数据用session
        String servercode = sb.toString();

        HttpSession session = request.getSession();
        session.setAttribute("Servercode",servercode);


/*
        pen.drawString("a",10,40);
        pen.drawString("c",30,35);
        pen.drawString("b",50,45);
        pen.drawString("d",70,40);
*/

        //写那个图片 后缀名 写出到哪里和图片命名 response.getOutputStream 输出到当前页面上
        ImageIO.write(img,"jpg",response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }


}
