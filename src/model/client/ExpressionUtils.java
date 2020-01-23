package expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import test.SymbolTable;

public class ExpressionUtils 
{
	public static Double calculateComplexEpression(String expression)
	{
	LinkedList<String> list_exp =new LinkedList<>();
	Stack<String> stack_operators= new Stack<>();

	String[] split = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
	
		for(String s: split)
		{
			
			switch (s) {
			case "/":
			case "*":
			case "(":
				stack_operators.push(s);
				break;
			case "-":
			case "+":
				while(!stack_operators.isEmpty()&& !stack_operators.peek().equals("(") && !stack_operators.peek().equals(")"))
				{
					list_exp.add(stack_operators.pop());
				}
				stack_operators.add(s);
				break;
			case ")":
				while (!stack_operators.isEmpty() && !stack_operators.peek().equals("("))
				{
					list_exp.add(stack_operators.pop());
				}
				stack_operators.pop();	
				break;
			default: 
				//list_exp.add(s);
				if (SymbolTable.map.containsKey(s))
				{
					
					
//					if(SymbolTable.bindMap.containsKey(s))
//					{
//						String st=SymbolTable.bindMap.get(s);
//						list_exp.add(SymbolTable.simMap.get(st).toString());
//
//					}
//					else
//					{
//						s=SymbolTable.map.get(s).toString();
//						list_exp.add(s);
//
//
//					}
					Double d=SymbolTable.map.get(s).getValue();
					
					list_exp.add(d.toString());
				}
				else
				{
					list_exp.add(s);

				}
				break;
			}
		 }

		while (!stack_operators.isEmpty())
		{
			list_exp.add(stack_operators.pop());
		}
		Collections.reverse(list_exp);
		Expression e=reverstoExp(list_exp);
		return (double)e.calculate();
	}
	public static  Expression reverstoExp(LinkedList<String> list_exp) 
	{
	
		Expression right=null;
		Expression left=null;
		Expression result=null;
		String str =list_exp.removeFirst();
		if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/"))
		{
			right=reverstoExp(list_exp);
			left=reverstoExp(list_exp);
		}
		
		switch (str) {
		case "+":
		{
			 result= new Plus(left, right);
			 break;
		}
		case "-":
				result= new Minus(left, right);	
				break;
		case "*":
			 result= new Mul(left, right);
			 break;
		case "/":
			 result=new Div(left, right);
			 break;
		default:
			 result=new Number(Double.parseDouble(str));
			break;
			
		}
		return result;
		
		
		
	}


}

