<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
	String id = request.getParameter("id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/guestbook2/gbc" method="post">
		<table>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pw" value=""></td>
				<td><button type="submit">확인</button></td>			
			</tr>
			<tr>
				<td colspan="2">
				<a href="/guestbook2/gbc?action=list">메인으로 돌아가기</a>
				</td>
			</tr>
			<tr>
				<td>
				<input type="hidden" name="id" value="<%=id%>">
				<input type="hidden" name="action" value="delete">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>