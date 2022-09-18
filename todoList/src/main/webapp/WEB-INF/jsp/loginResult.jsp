<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  lang="ja-jp">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<title>Let's todo!</title>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"></jsp:include>

<p>ログインに成功しました。</p>
<p>ようこそ ${account.username} さん！</p>
<form action = "/todoList/TodoList" method="post">
<input type = "submit" value = "Let's todo!">
</form>
<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>