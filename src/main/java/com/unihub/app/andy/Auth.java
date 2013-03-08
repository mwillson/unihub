/************
*ANDY'S FILE*
************/
package com.unihub.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/login")
public class Auth extends HttpServlet {
	/*
	Purpose of this class is to get the request from a users login
	and authenticate it, then redirect the user to where they were last
	*/
	@Override
	public void doPost(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String userName = req.getParameter("username");
		String password = req.getParameter("password");

		String servletPath = (session.getAttribute("path_for_login") != null) ?
			((String)session.getAttribute("path_for_login")).replaceFirst("/", "") : "home";

		if(AuthUtilities.authenticate(userName, password)) {
			//successfully logged in so create cookie or session
			//also remove calling path since it served its purpose
			session.removeAttribute("path_for_login");
			session.setAttribute("username", userName);
                	session.setAttribute("user", 
                                              new User(userName, 
                                                       password,
                                                       "test@example.edu",
                                                       "oswego"));
			res.sendRedirect(servletPath);
		}
		else
			res.sendRedirect("login");

	}//end of doPost method

	/*
	I decided to actually implement a get request for authenticate
	which is only used for making login appear and passing it the right stuff
	like what the previous
	*/
	public void doGet(HttpServletRequest req,
		HttpServletResponse res) throws ServletException, IOException {

		String path = (String)req.getSession().getAttribute("path_for_login");


		if(req.getSession().getAttribute("user") == null) {
			RequestDispatcher dis = req.getRequestDispatcher("login-form");
			dis.forward(req, res);
		} else {
			if(path == null)
				res.sendRedirect("home");
			else
				res.sendRedirect(path.replaceFirst("/", ""));
		}


	}//end of doGet method

}//end class
