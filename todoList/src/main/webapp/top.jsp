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
	<form class="main-login" action="/todoList/Login" method = "post">
	ユーザー名:    <input type = "text" name = "username"><br>
	メールアドレス:<input type ="email" name = "mail"><br>
	パスワード:    <input type = "password" name = "pass"><br>
	<input type = "submit" value = "ログイン">
	</form>
	<p class="guestLogin-title">お試しログインはこちら</p>
	<form class="guest-login">
	<input type = "submit" value = "ゲストログイン">
	</form>
	<p class="caution">※お試しログインは「ゲストユーザー」で1つのアカウントを共有しますので、個人情報の書き込みはご遠慮下さい。</p>
	<a class="newAccount" href="/todoList/createAccount.jsp">アカウント作成はこちら</a>
</main>
<jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>