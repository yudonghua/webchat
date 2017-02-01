package websocket;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "RegistServlet", urlPatterns = {"/RegistServlet"})
public class RegistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rpsw = request.getParameter("rpsw");//得到表单输入的内容
		if(username==null||username.trim().isEmpty()){
			request.setAttribute("msg", "帐号不能为空");
			request.getRequestDispatcher("/regist.htm").forward(request, response);
			return;
		}
                if(username.matches("^[0-9].*$")){
			request.setAttribute("msg", "账号请勿以数字开头");
			request.getRequestDispatcher("/regist.htm").forward(request, response);
			return;
		}
		if(password==null||password.trim().isEmpty()){
			request.setAttribute("msg", "密码不能为空");
			request.getRequestDispatcher("/regist.htm").forward(request, response);
			return;
		}
		if(!password.equals(rpsw)){
			request.setAttribute("msg", "两次输入的密码不同");
			request.getRequestDispatcher("/regist.htm").forward(request, response);
			return;
		}
		UserDao u = new UserDao();
                String psw =u.findUsername(username);
                if(psw!=null){
                    request.setAttribute("msg", "账号已存在");
                    request.getRequestDispatcher("/regist.htm").forward(request, response);
                    return;
                }
		u.addUser(username,password);//调用addUser（）方法
                Home h = new Home();
                h.createuser(username);
		request.setAttribute("msg", "恭喜："+username+"，注册成功");
		request.getRequestDispatcher("/login.htm").forward(request, response);

	}

}
