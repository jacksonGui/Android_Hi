package socket;


import javax.servlet.http.HttpServlet;

public class MySocketServ extends HttpServlet {
	

	
	
	public void init(){
		System.out.println("Æô¶¯socket");
		new MySocketT().start();
		
	
	}
	


}
