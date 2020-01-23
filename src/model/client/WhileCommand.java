package commands;


import expression.ExpressionUtils;
import test.Lexer;

import test.SymbolTable;

public class WhileCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts)
	{	int i=1;
		StringBuilder str=new StringBuilder();
		while (!commandParts[i].equals("{"))
		{
				str.append(commandParts[i]);
				i++;
		}
		StringBuilder strno=new StringBuilder();
		String[] s= new String[commandParts.length-i-2];
		int k=0;
		int start=i+2;
		for (int j=start;j<commandParts.length-1;j++)
		{
			if(!commandParts[j].equals(""))
			{
				strno.append(commandParts[j]);
				strno.append(" ");
			}
			else
			{
				s[k]=strno.toString();
				strno.delete(0, strno.length());
				k++;
			}
		}
		String[] end= new String[k];
		for(int j=0;j<k;j++)
		{
			end[j]=s[j];
		}
		String [] spliter = str.toString().split("(?<=[<>()])|(?=[<>()])");
		if (spliter[1].equals("<")) 
		{
			while((SymbolTable.map.get(spliter[0]).getValue())<(ExpressionUtils.calculateComplexEpression(spliter[2])))
				{ 
					Lexer.lexering(end);
				}
		}
		else if( spliter[1].equals(">"))
		{
			while((SymbolTable.map.get(spliter[0]).getValue())>(ExpressionUtils.calculateComplexEpression(spliter[2])))
				{ 
					Lexer.lexering(end);
				}
		}
		
		return (double) 0;


}
}
