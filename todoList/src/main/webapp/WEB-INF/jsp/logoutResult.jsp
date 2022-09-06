<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja-jp">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<link rel="stylesheet" href="./css/top.css">
<title>Let's todo!</title>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>
<main>
<p>ログアウトしました。</p>
<a href="/todoList/top.jsp">TOPへ</a>

</main>
<jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>