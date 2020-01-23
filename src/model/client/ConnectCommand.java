package commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectCommand implements Command {
		public static Socket theServer;
		public static PrintWriter out;
	@Override
	public Double doCommand(String[] commandParts) {
		
		int port=Integer.parseInt(commandParts[2]);
		
			try {
				   theServer=new Socket(commandParts[1], port);
				   out= new PrintWriter(theServer.getOutputStream());
				   System.out.println("Server connected");
				   
			} catch (UnknownHostException e) {
		
				
			} catch (IOException e) {

			}
			
		return (double) 0;
	}

}
