package entity;

import socket.MySocketThread;



public class SocketMsg {
	
	private String fid;
	private String msg;
	
	
	
	public SocketMsg(String fid,String msg){

		this.fid=fid;
		this.msg=msg;
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
