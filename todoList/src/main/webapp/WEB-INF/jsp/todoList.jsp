<%@page import="java.util.Calendar"%>
<%@page import= "java.text.SimpleDateFormat" %>
<%@page import = "java.util.List" %>
<%@page import = "java.util.ArrayList" %>
<%@page import = "org.json.*" %>
<%@page import = "main.TodoListDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/reset.css">
<link rel="stylesheet" href="./css/header-footer.css">
<link rel="stylesheet" href="./css/login-result">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<title>Let's todo!</title>
<script src="https://code.jquery.com/jquery-3.x.x.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>
<main>
	<form name = "form1">
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
		
		<button class="task_submit" type="button">Add Task</button>
	</form>
	<div class = "table">
	    <h2>todoList</h2>
    	<ul class="task_list"></ul>
    	<ul class="task_dateList"></ul>
    	<ul class="task_priorityList"></ul>
	</div>
	
<!-- 	<script src = "js/TodoList.js"></script>   -->

<% Object userID = session.getAttribute("userID");
String user = userID.toString();

TodoListDAO dao  = new TodoListDAO();
 %>
<script type="text/javascript"> 


const priorityValue = document.getElementById('priority');
console.log(priorityValue.value);

const taskValue = document.getElementsByClassName('task_value')[0];
const taskDate = document.getElementsByClassName('datepicker')[0];

const taskSubmit = document.getElementsByClassName('task_submit')[0];
const taskList = document.getElementsByClassName('task_list')[0];
const taskDateList = document.getElementsByClassName('task_dateList')[0];
const priorityList = document.getElementsByClassName('task_priorityList')[0];


// 追加ボタンを作成
const addTasks = (task,date,priority) => {
  // 入力したタスクを追加・表示
  const listItem = document.createElement('li');
  const showItem = taskList.appendChild(listItem);
  showItem.innerHTML = task
  
  const listDate = document.createElement('p');
  const showDate = taskDateList.appendChild(listDate);
  showDate.innerHTML = date

  const listPriority = document.createElement('p');
  const showPriority = priorityList.appendChild(listPriority);
  showPriority.innerHTML = priority

  
  listItem.appendChild(showDate)
  listItem.appendChild(showPriority)
  // タスクに削除ボタンを付与
  const deleteButton = document.createElement('button');
  deleteButton.innerHTML = '完了';
  listItem.appendChild(deleteButton);

  // 削除ボタンをクリックし、イベントを発動（タスクが削除）
  deleteButton.addEventListener('click', evt => {
    evt.preventDefault();
    deleteTasks(deleteButton);
  });
};

// 削除ボタンにタスクを消す機能を付与
const deleteTasks = (deleteButton) => {
  const chosenTask = deleteButton.closest('li');
  taskList.removeChild(chosenTask);
  
};

// 追加ボタンをクリックし、イベントを発動（タスクが追加）
taskSubmit.addEventListener('click', evt => {
	if(taskValue.value){
		console.log("add taskボタンが押されました");
		evt.preventDefault();
		const task = taskValue.value;
		const date = taskDate.value;
		const priority = priorityValue.value;
		addTasks(task,date,priority);
		taskValue.value = ''
	}
	else{
		console.log("空文字が入力されました。");
	}
});





window.addEventListener('pageshow',evt => {
	  if (evt.persisted) {
	    console.log('キャッシュから表示');
		evt.preventDefault();
		const task = taskname;
		const date = taskdate;
		addTasks(task,date);
		taskValue.value = ''
	  } else {
	    console.log('新しいページを受信して表示');
		<%
		List<JSONObject> jsonList = dao.showTasksDAO(Integer.parseInt(user));
		 %>
		const jsonList = <%= jsonList%>
		
	    for(let i = 0; i < jsonList.length; i++){
			evt.preventDefault();
			var json = JSON.parse(JSON.stringify(jsonList[i]));
			if(json.status === false){
				var task = json.taskname;
				var date = json.taskdate;
				var priority = json.priority;
				addTasks(task,date,priority);
				taskValue.value = ''
	    	}
	  	}
	  }
	});

</script>

</main>
<jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>