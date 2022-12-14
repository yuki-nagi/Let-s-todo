<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja-jp>
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
	<h2>アカウント作成</h2>
	<form action="/todoList/CreateAccount" method = "post">
		ユーザー名:<input type="text" name="userName" maxlength="100"><br>
		メールアドレス:<input type="email" name=mail maxlength="254"><br>
		パスワード:<input type="password" name="password" maxlength="20"><br>
		パスワードの再入力<input type="password" name="passCheck" maxlength="20"><br>

		<input type="submit" value="登録">
	</form>
</main>
<jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>