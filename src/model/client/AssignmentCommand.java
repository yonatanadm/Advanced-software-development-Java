package commands;


import expression.ExpressionUtils;
import test.SymbolTable;
import test.Variable;

public class AssignmentCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts) {
		int flag=0;
		StringBuilder s=new StringBuilder();
		String[] spliter;
		for (int i=0;i<commandParts.length;i++)
		{
			if (commandParts[i].equals("bind"))
				flag=1;
			s.append(commandParts[i]);
		}	
		//no binding 
		if (flag==0) {
			spliter=s.toString().split("=");
			double newVal=ExpressionUtils.calculateComplexEpression(spliter[1].toString());
			// if the map hold the var so we change the val to the new
			if (SymbolTable.map.containsKey(spliter[0]))
				{
					if(SymbolTable.map.get(spliter[0]).bindFlag==1)
					{
			    		ConnectCommand.out.println("set" +" "+SymbolTable.map.get(spliter[0]).name+" "+newVal);
			     		ConnectCommand.out.flush();
					}
					SymbolTable.map.get(spliter[0]).setValue(newVal);
				}
			else
				{
					SymbolTable.map.put(spliter[0], new Variable(newVal, spliter[0]));
				}			
	}
		// here we have bind so we take care of it outside
		else {
			spliter=s.toString().split("="+"bind");
			BindCommand bc=new BindCommand();
			bc.doCommand(spliter);
			 }
      return (double) 0;
}
}