package entity;

import socket.MySocketThread;



public class SocketMsg {
	
	private String fid;
	private String msg;
	private String uid;
	
	
	
	public SocketMsg(String uid,String fid,String msg){
		this.uid=uid;
		this.fid=fid;
		this.msg=msg;
	}
	
	
	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


	
	
	

}
