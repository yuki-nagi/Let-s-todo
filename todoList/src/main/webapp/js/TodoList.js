const taskValue = document.getElementsByClassName('task_value')[0];
const taskDate = document.getElementsByClassName('datepicker')[0];
const taskSubmit = document.getElementsByClassName('task_submit')[0];
const taskList = document.getElementsByClassName('task_list')[0];
const taskDateList = document.getElementsByClassName('task_dateList')[0];


// 追加ボタンを作成
const addTasks = (task,date) => {
  // 入力したタスクを追加・表示
  const listItem = document.createElement('li');
  const showItem = taskList.appendChild(listItem);
  showItem.innerHTML = task
  
  const listDate = document.createElement('p');
  const showDate = taskDateList.appendChild(listDate);
  showDate.innerHTML = date
  listItem.appendChild(showDate)
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
		addTasks(task,date);
		taskValue.value = ''
	}
	else{
		console.log("空文字が入力されました。");
	}
});
