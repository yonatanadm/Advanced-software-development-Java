package commands;

import test.DataReaderServer;

public class DisconnectCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts)

	{

		ConnectCommand.out.println("bye");
		ConnectCommand.out.flush();
		ConnectCommand.out.close();
		DataReaderServer.close();
		return (double) 0;
	}

}
