package ph.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ph.dao.UserDAO;
import ph.po.User;

//@javax.servlet.annotation.WebServlet(name = "LoginServlet")
@javax.servlet.annotation.WebServlet("/LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet
{
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        try{
            /* 0.取出表单中的用户提交数据 */
            String username=request.getParameter("username");
            System.out.println(username);
            String pwd=request.getParameter("pwd");
            String usercode=request.getParameter("usercode");//用户输入的验证码

            //1.验证验证码
            String realcode=(String) request.getSession(true).getAttribute("realcode");//session中的验证码
//            if(!realcode.equalsIgnoreCase(usercode))//如果两个验证码不一致
            if(false)
            {
                request.setAttribute("msg", "验证码输入错误");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            else
            {
                User user = new UserDAO().getByName(username);
                if(null == user)
                {
                    request.setAttribute("msg", "用户名不存在");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
                else if(!user.getPwd().equals(pwd))
                {
                    request.setAttribute("msg", "密码输入错误");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
                else
                {
                    request.getSession(true).setAttribute("user", user);
                    request.setAttribute("msg", "登录成功");
                    request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
                }
            }
        }
        catch(Exception e)
        {
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {

    }
}