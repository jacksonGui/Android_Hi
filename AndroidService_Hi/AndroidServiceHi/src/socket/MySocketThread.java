package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import entity.SocketMsg;
import entity.SocketThread;

import net.sf.json.JSONObject;

public class MySocketThread extends Thread {
	
	private List<SocketMsg> msgList;
	private List<SocketThread> threadList;
	
	public MySocketThread(List<SocketMsg> msgList,List<SocketThread> threadList){
		super();
		this.msgList=msgList;
		this.threadList=threadList;

	}
	
	
	
	public void run(){
		
		
//		try {
//			DataOutputStream dos=new DataOutputStream(threadList.get(0).getSocket().getOutputStream());
//        
//			dos.writeUTF(msgList.get(0).getMsg());
//			dos.flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
		
		if(msgList!=null){
			for(int i=0;i<msgList.size();i++){
				for(int j=0;j<threadList.size();j++){
					if(msgList.get(i).getFid().equals(threadList.get(j).getUid())){
						
						try {
							if(threadList.get(j).getSocket()!=null){
								DataOutputStream dos=new DataOutputStream(threadList.get(j).getSocket().getOutputStream());
//								PrintWriter out=new PrintWriter(threadList.get(j).getSocket().getOutputStream());
								JSONObject jsonObject=new JSONObject();
								jsonObject.put("from", msgList.get(i).getUid());
//								System.out.println("cccccccccccccccc"+msgList.get(i).getUid());
								jsonObject.put("msg", msgList.get(i).getMsg());
//								out.println(jsonObject.toString());
//								out.flush();
								dos.writeUTF(jsonObject.toString());
						        dos.flush();
							}
							


							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							threadList.remove(j);
						}
				
						
					}
				}
			}
		}
		
		
	}
	

}
