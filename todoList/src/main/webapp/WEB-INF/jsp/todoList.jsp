<%@page import="java.util.Calendar"%>
<%@page import= "java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="./css/todoList.css">
<title>Let's todo!</title>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>
<main>
	<p>ユーザー名: ${account.username}</p>
	<form name="logout_form" action ="/todoList/Logout" method="post">
		<a class="logout" href="javascript:logout_form.submit()">ログアウト</a>
	</form>
	<form name = "form1" class="form1">
	<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); %>
	<% Calendar cl = Calendar.getInstance(); %>
		<input class="task_value" type="text"/>
		<input  class = "datepicker" type="text" value="<%= sdf.format(cl.getTime()) %>"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
		<script>
			$(function() {
				$.datepicker.setDefaults($.datepicker.regional["ja"]);
				$(".datepicker").datepicker({
					minDate: new Date()
					});
			});
		</script>
		<select id= "priority">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4" selected>4</option>
		</select>
		<input type="hidden" class="updatetime">
		<button class="task_submit" type="button">Add Task</button>
	</form>
	<div class = "table">
	    <h2>todoList</h2>
    	<button class="todoSort" id="addSort">追加順</button>
    	<button class="todoSort" id="dateSort">日付順</button>
    	<button class="todoSort" id="prioritySort">優先度順</button>
    	<ul class="task_list"></ul>
    	<ul class="task_dateList"></ul>
    	<ul class="task_priorityList"></ul>
    	<ul class="hidden_task_updateList"></ul>
	</div>
	

<script src="js/TodoList.js">

</script>

</main>
<jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>