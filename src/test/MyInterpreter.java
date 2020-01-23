package test;


import test.Lexer;

public class MyInterpreter
{
	
	public static  Double interpret(String[] lines)
	
	{
		double result=0;
		result= Lexer.lexering(lines);
		//SymbolTable.bindMap.clear();
		SymbolTable.map.clear();
		//SymbolTable.simMap.clear();
		return result;
		
	}
	


}
