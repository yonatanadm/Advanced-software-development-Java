package server_side;

public interface Server 
{
	void runServer(ClientHandler c) throws Exception;
	void stop();
	void start();

}
