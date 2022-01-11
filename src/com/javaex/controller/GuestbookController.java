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
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String act = request.getParameter("action");
	
	
	if("list".equals(act)) {
		
		GuestbookDao guestbookDao = new GuestbookDao();
		List<GuestbookVo> list = guestbookDao.getList();
		
		request.setAttribute("glist", list);
		
		WebUtil.forward(request, response, "/WEB-INF/list.jsp");
		
		
	}else if("write".equals(act)) {
		
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String content = request.getParameter("content");
		
		GuestbookVo guestbookVo = new GuestbookVo(name, pw, content);
		GuestbookDao guestbookDao = new GuestbookDao();
		guestbookDao.insert(guestbookVo);
		
		WebUtil.redirect(request, response, "/guestbook2/gbc");
	
		
	}else if("deleteForm".equals(act)) {	
		
		WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");
		
		
	}else if("delete".equals(act)) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String pw = request.getParameter("pw");
		
		GuestbookVo guestobokVo = new GuestbookVo(id, pw);
		GuestbookDao guestbookDao = new GuestbookDao();
		
		guestbookDao.delete(guestobokVo);
		
		WebUtil.redirect(request, response, "/guestbook2/gbc");
		
		
	}else {//기본값
		GuestbookDao guestbookDao = new GuestbookDao();
		List<GuestbookVo> list= guestbookDao.getList();
		
		request.setAttribute("guestList", list);
		
		WebUtil.forward(request, response, "/WEB-INF/list.jsp");
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
