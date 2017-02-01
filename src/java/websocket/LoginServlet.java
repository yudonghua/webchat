/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author PC
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
                HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verifyc  = request.getParameter("verifycode");
		String svc =(String) request.getSession().getAttribute("sessionverify");
		String psw =new UserDao().findUsername(username);
		if(!svc.equalsIgnoreCase(verifyc)){
			request.setAttribute("msg", "验证码错误！");
			request.getRequestDispatcher("/login.htm").forward(request, response);
			return;
		}
		if(psw ==null){
			request.setAttribute("msg", "没有这个用户！");
                        session.setAttribute("username", null);
                        session.setAttribute("password", null);
			request.getRequestDispatcher("/login.htm").forward(request, response);
			return;
		}
		if(psw!=null&&!psw.equals(password)){
			request.setAttribute("msg", "密码错误！");
                        session.setAttribute("username", null);
                        session.setAttribute("password", null);
			request.getRequestDispatcher("/login.htm").forward(request, response);
			return;
		}
		if(psw.equals(password)){
			request.setAttribute("dd", "用户："+username+",欢迎访问");
                        request.setAttribute("username", username);
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        session.setAttribute("author", username);
                     //   Data.username = username;
			request.getRequestDispatcher("/index.htm").forward(request, response);
			//response.setHeader("Refresh","1;url=welcome.jsp");
		}
		
	}

}

