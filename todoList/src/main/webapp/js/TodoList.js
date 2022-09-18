var taskPriority = document.getElementById('priority');//タスク優先度

var taskValue = document.getElementsByClassName('task_value')[0];//タスク名
var taskDate = document.getElementsByClassName('datepicker')[0];//タスク日付

var taskSubmit = document.getElementsByClassName('task_submit')[0];
var taskList = document.getElementsByClassName('task_list')[0];//生成したタスク名を入れておく場所
var taskDateList = document.getElementsByClassName('task_dateList')[0];//生成したタスク日付を入れておく場所
var priorityList = document.getElementsByClassName('task_priorityList')[0];//生成したタスク優先度を入れておく場所
var updatetimeList = document.getElementsByClassName('hidden_task_updateList')[0];//生成したタイムスタンプ（クライアント側）を入れておく場所


//タスク一覧の更新の際にリセット
const resetShowTasks = () => {
	$(".task_list").empty();
}

//タスクのソートメソッド
const taskSort = (modeNum) => {
	var sortMode = null;
	if (modeNum === 1) {
		sortMode = "select1";
	} else if (modeNum === 2) {
		sortMode = "select2";
	} else if (modeNum === 3) {
		sortMode = "select3";
	} else {
		alert("引数は整数の1（追加順ソート）,2(日付順ソート),3(優先度順ソート)の中から選んで下さい。");
	}

	return $.ajax({
			type: 'GET',
			url: '/todoList/TodoList',
			data: {
				Mode: sortMode
			},
			dataType: "json"
		})
		.done(function(response) {
			console.log("ajax done.");
			console.log("sortMode:" + sortMode);
			resetShowTasks();

			// response（二次元json）から各種キーを取り出して検索して変数にデータを突っ込んでaddTasks処理

			for (let i = 0; i < Object.keys(response).length; i++) {
				if (response["j" + i].status === false) {

					var task = response["j" + i].taskname;
					var date = response["j" + i].taskdate;
					var priority = response["j" + i].priority;
					var updatetime = response["j" + i].updatetime;
					addTasks(task, date, priority, updatetime);
					taskValue.value = ''
				}
			}

		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("ajax failed")
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);

		});
}

// taskdateの入力確認
const regex = /^[0-9]{4}\/(0[1-9]|1[0-2])\/(0[1-9]|[12][0-9]|3[01])$/;

// タイムスタンプ取得時のデータの整形用のフォーマット
const formatDate = (date, format) => {

	format = format.replace(/YYYY/, date.getFullYear());
	format = format.replace(/MM/, date.getMonth() + 1);
	format = format.replace(/DD/, date.getDate());
	format = format.replace(/hh/, date.getHours());
	format = format.replace(/mm/, date.getMinutes());
	format = format.replace(/ss/, date.getSeconds());

	return format;
}

// ページ更新時にユーザーのタスクを表示する処理
window.addEventListener('pageshow', evt => {
	if (evt.persisted) {
		console.log('キャッシュから表示');
		evt.preventDefault();
		taskSort(1);
	} else {
		console.log('新しいページを受信して表示');
		evt.preventDefault();
		taskSort(1);
	}
});

//各ソートボタンで発生するイベント
$("#addSort").on('click', function() {
	taskSort(1);
})

$("#dateSort").on('click', function() {
	taskSort(2);
});

$("#prioritySort").on('click', function() {
	taskSort(3);
});


//Add Taskボタンを押した時、POSTでデータをTodoList.javaサーブレットへ送信

$(".task_submit").on('click', function() {

	var taskPriority = document.getElementById('priority');
	var taskValue = document.getElementsByClassName('task_value')[0];
	var taskDate = document.getElementsByClassName('datepicker')[0];

	var date = formatDate(new Date(), "YYYY/MM/DD hh:mm:ss");

	if (taskValue.value && regex.test(taskDate.value)){
		$(".updatetime").val(date);

		//サーブレットに登録するタイムスタンプの作成	
		var updateTime = document.getElementsByClassName('updatetime')[0];

		var request = {
			hidden: "AddTask",
			taskname: taskValue.value,
			taskdate: taskDate.value,
			priority: taskPriority.value,
			updatetime: updateTime.value
		}
		console.log(request);

		$.ajax({
			type: 'POST',
			url: '/todoList/TodoList',
			data: request
			}).done(function(response) {
			//何もしない
			}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("ajax failed")
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message)
			});
	
	}
});

// クライアント側の画面にタスク追加（タスク表示させる）メソッド
const addTasks = (task, date, priority, updatetime) => {
	// 入力したタスクを追加・表示
	const listItem = document.createElement('li');
	listItem.className = "taskname";
	const showItem = taskList.appendChild(listItem);
	showItem.innerHTML = task

	const listDate = document.createElement('p');
	listDate.className = "taskdate";
	const showDate = taskDateList.appendChild(listDate);
	showDate.innerHTML = date;

	const listPriority = document.createElement('p');
	listPriority.className = "taskpriority";
	const showPriority = priorityList.appendChild(listPriority);
	showPriority.innerHTML = priority;


	var taskUpdatetime = document.getElementsByClassName('updatetime')[0];
	const listUpdatetime = document.createElement('p');
	listUpdatetime.className = "hidden";
	const showUpdatetime = updatetimeList.appendChild(listUpdatetime);
	showUpdatetime.innerHTML = updatetime;

	listItem.appendChild(showDate);
	listItem.appendChild(showPriority);
	listItem.appendChild(showUpdatetime);


	// タスクに削除ボタンを付与
	const deleteButton = document.createElement('button');
	deleteButton.innerHTML = '完了';
	listItem.appendChild(deleteButton);


	$(".taskname,.taskdate,.taskpriority,button,li").hide().fadeIn();
	$(".hidden").hide();


	// 削除ボタンをクリックし、イベントを発動（タスクが削除）
	deleteButton.addEventListener('click', evt => {
		evt.preventDefault();
		deleteTasks(deleteButton);
	});
};

// 削除ボタンにタスクを消す機能を付与
const deleteTasks = (deleteButton) => {
	const chosenTask = deleteButton.closest('li');

	var chosenUpdatetime = chosenTask.getElementsByClassName("hidden")[0];
	var complete_request = {
		hidden: "Complete",
		updatetime: chosenUpdatetime.innerText,
	}

	$.ajax({
		type: 'POST',
		url: '/todoList/TodoList',
		data: complete_request
		//	  processData: false
		}).done(function(response) {
			console.log("ajax(タスク完了）done");
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("リクエスト時になんらかのエラーが発生しました");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message)
		});
	taskList.removeChild(chosenTask);
};




// 追加ボタンをクリックし、イベントを発動（タスクが追加）
taskSubmit.addEventListener('click', evt => {
	if (taskValue.value && regex.test(taskDate.value)){
		console.log("add taskボタンが押されました");
		evt.preventDefault();
		const task = taskValue.value;
		const date = taskDate.value;
		const priority = taskPriority.value;


		var datetime = formatDate(new Date(), "YYYY/MM/DD hh:mm:ss");


		$(".updatetime").val(datetime);
		var taskUpdatetime = document.getElementsByClassName('updatetime')[0];
		const updatetime = taskUpdatetime.value;
		addTasks(task, date, priority, updatetime);
		taskValue.value = '';
	}
	 else if(taskValue.value && taskDate.value){
		alert("タスク目標日は yyyy/MM/dd の形式で入力してください。");
	}
	 else {
		alert("空文字が入力されました。");
	}
});