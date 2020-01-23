package commands;



import expression.ExpressionUtils;


public class ReturnCommand implements Command {

	int lenCommand;
	
	public ReturnCommand() {
		lenCommand=2;
	}

	@Override
	public Double doCommand(String[] commandParts) {
		
		StringBuilder s=new StringBuilder();

		
			for (int i=1;i<commandParts.length;i++)
			{
				s.append(commandParts[i]);	
			}

			 return ExpressionUtils.calculateComplexEpression(s.toString());
			 
		
	}


}
