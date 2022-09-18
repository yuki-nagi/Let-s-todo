package mainModel;

import java.io.Serializable;

//タスクのデータを他メソッドの引数にする時に、データをまとめるのに使用するクラス
public class TaskDataBean implements Serializable {
	private String taskname;
	private String taskdate;
	private boolean status;
	private int userID;
	private int priority;
	private String updatetime;
	
	public TaskDataBean() {}
	
	public TaskDataBean(String taskname,String taskdate,boolean status, int userID,int priority,String updatetime){
		this.taskname = taskname;
		this.taskdate = taskdate;
		this.status = status;
		this.userID = userID;
		this.priority = priority;
		this.updatetime = updatetime;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public void setTaskdate(String taskdate) {
		this.taskdate = taskdate;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getTaskname() {
		return taskname;
	}

	public String getTaskdate() {
		return taskdate;
	}

	public boolean getStatus() {
		return status;
	}

	public int getUserID() {
		return userID;
	}

	public int getPriority() {
		return priority;
	}
	
	public String getUpdatetime() {
		return updatetime;
	}

}
