package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	private int port;
	private volatile boolean stop;

	
	public MySerialServer(int port) {
		this.port=port;
		this.stop=false;
	}
		
	@Override
	public void runServer(ClientHandler ch) throws Exception 
	{
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1200000);
		while(!stop){
		try{
		Socket aClient=server.accept(); // blocking call
		try {
		ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
		aClient.close();
		} catch (IOException e) {e.printStackTrace();}
		}catch(SocketTimeoutException e) {e.printStackTrace();}
		}		
		server.close(); 
		
	}
	
	public void start(){
		new Thread(()->{
			try {
				runServer(new MyClientHandler());
			} catch (Exception e) {
			}
		}).start();;
		}

	@Override
	public void stop() {
		stop=true;

	}
	


}
