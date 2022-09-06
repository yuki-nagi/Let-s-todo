package main;

public class TaskData {
	private String taskname;
	private String taskdate;
	private boolean status;
	private String memo;
	private int userID;
	private int priority;
	private String updatetime;
	
	public TaskData(String taskname,String taskdate,boolean status, String memo, int userID,int priority,String updatetime){
		this.taskname = taskname;
		this.taskdate = taskdate;
		this.status = status;
		this.memo = memo;
		this.userID = userID;
		this.priority = priority;
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

	public String getMemo() {
		return memo;
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
