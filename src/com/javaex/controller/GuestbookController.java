package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String act = request.getParameter("action");
	
	
	if("list".equals(act)) {
		
		GuestbookDao guestbookDao = new GuestbookDao();
		List<GuestbookVo> list = guestbookDao.getList();
		
		request.setAttribute("glist", list);
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/list.jsp");
		rd.forward(request, response);

		
	}else if("write".equals(act)) {
		
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String content = request.getParameter("content");
		
		GuestbookVo guestbookVo = new GuestbookVo(name, pw, content);
		GuestbookDao guestbookDao = new GuestbookDao();
		guestbookDao.insert(guestbookVo);
		
		response.sendRedirect("/guestbook2/gbc?action=list");
	
	
	}else if("deleteForm".equals(act)) {	
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/deleteForm.jsp");
		rd.forward(request, response);
		
		
	}else if("delete".equals(act)) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String pw = request.getParameter("pw");
		
		GuestbookVo guestobokVo = new GuestbookVo(id, pw);
		GuestbookDao guestbookDao = new GuestbookDao();
		
		guestbookDao.delete(guestobokVo);
		
		response.sendRedirect("/guestbook2/gbc?action=list");
		
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
