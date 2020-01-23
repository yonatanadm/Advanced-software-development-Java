package commands;

import expression.ExpressionUtils;
import test.DataReaderServer;

public class OpenDataServerCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts) {
		Double port = ExpressionUtils.calculateComplexEpression(commandParts[1]);
		Double rate = ExpressionUtils.calculateComplexEpression(commandParts[2]);
		new Thread(() -> {
			DataReaderServer server = new DataReaderServer(port.intValue(), rate.intValue());
			server.runServer();

		}).start();
		return (double) 0;
	}

}
