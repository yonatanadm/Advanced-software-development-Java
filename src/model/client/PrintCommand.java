package commands;

import expression.ExpressionUtils;
import test.SymbolTable;

public class PrintCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts) {
		StringBuilder builder=new StringBuilder();
		for (int i=1;i<commandParts.length;i++)
		{
			builder.append(commandParts[i]);
		}
		if(SymbolTable.map.containsKey(builder.toString()))
		{
			System.out.println(ExpressionUtils.calculateComplexEpression(builder.toString()));

		}
		else
		{
			System.out.println(builder.toString());
		}
		return (double) 0;
	}

}
