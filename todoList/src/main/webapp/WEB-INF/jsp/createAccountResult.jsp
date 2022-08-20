<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  lang="ja-jp">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<link rel="stylesheet" href="./css/login-result">
<title>Let's todo!</title>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"></jsp:include>
<main>
<h1>Enjoy todo!</h1>
<p>TOP画面からログインして、当機能をお楽しみください</p>
<a href="/todoList/top.jsp">TOPへ</a>
</main>
<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>