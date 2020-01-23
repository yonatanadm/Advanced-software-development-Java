package test;

import commands.AssignmentCommand;
import commands.Command;
import commands.ConnectCommand;
import commands.DisconnectCommand;
import commands.OpenDataServerCommand;
import commands.PrintCommand;
import commands.ReturnCommand;
import commands.VarCommand;
import commands.WhileCommand;
import commands.SleepCommand;

public class Parser {
	public Parser() {

	}

	public static Double parsing(String[] commandParts) {
		// ["return", "5", "+", "6"]
		if (commandParts.length == 0) {
			return null;
		}
		Command c;
		String firstWord = commandParts[0];
		Double expression = null;

		switch (firstWord) {
		case "return":
			c = new ReturnCommand();
			expression = c.doCommand(commandParts);
			break;
		case "var":
			c = new VarCommand();
			c.doCommand(commandParts);
			break;
		case "connect":
			c = new ConnectCommand();
			c.doCommand(commandParts);
			break;
		case "disconnect":
			c = new DisconnectCommand();
			c.doCommand(commandParts);
			break;
		case "openDataServer":
			c = new OpenDataServerCommand();
			c.doCommand(commandParts);
			break;
		case "while":
			c = new WhileCommand();
			c.doCommand(commandParts);
			break;
		case "print":
			c = new PrintCommand();
			c.doCommand(commandParts);
			break;
		case "sleep":
			c = new SleepCommand();
			c.doCommand(commandParts);
			break;
		default:
			c = new AssignmentCommand();
			c.doCommand(commandParts);
			break;

		}
		return expression;

	}

}
