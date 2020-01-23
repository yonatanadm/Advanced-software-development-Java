package commands;

import test.SymbolTable;
import test.Variable;

public class BindCommand implements Command {

	@Override
	public Double doCommand(String[] commandArguments) 
	{
		if (!SymbolTable.map.containsKey(commandArguments[1]))
		{
			SymbolTable.map.put(commandArguments[1],new Variable(0, commandArguments[1]));
		}
		
		SymbolTable.map.get(commandArguments[1]).changeFlag();
		SymbolTable.map.put(commandArguments[0], SymbolTable.map.get(commandArguments[1]));

		return (double) 0;
	}

}
