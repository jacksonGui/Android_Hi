package entity;

import java.net.Socket;


public class SocketThread {
	private String uid;
	private Socket mySocket;
	public SocketThread(String uid,Socket mySocket){
		this.uid=uid;
		this.mySocket=mySocket;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Socket getSocket() {
		return mySocket;
	}

	public void setSocket(Socket myThread) {
		this.mySocket = mySocket;
	}

}
