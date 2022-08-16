<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="todoList.GuestUser" %>
<%
GuestUser loginUser = (GuestUser) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<link rel="stylesheet" href="./css/login-result">
<title>Let's todo!</title>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"></jsp:include>
<% if(loginUser != null){ %>
<p>ログインに成功しました。</p>
<p>ようこそ<%=loginUser.getUserName() %>さん</p>
<a href= "/docotsubu_self/Main">Let's todo!</a>
<%}else{ %>
<p>ログインに失敗しました。</p>
<a href= "/todoList/top.jsp">TOPへ</a>
<%} %>
<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>