package server_side;

public class RunServer {

	public static void main(String[] args) 
	{

		Server server =new MySerialServer(7773);
		server.start();

	}

}
