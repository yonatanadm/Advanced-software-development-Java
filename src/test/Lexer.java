package test;

public class Lexer 
{	
	public static Double lexering (String[] lines)
	{
		Double result = null;
		int flag=0;
		StringBuilder st=new StringBuilder();
		for(int i=0;i<lines.length;i++)
		{
			String split[]= lines[i].split("\\s");
			if(split[0].equals("while") || split[0].equals("if") || flag==1)
			{
				int j=0;
				while(split.length>j)
				{
					if (!split[j].equals("}"))
					{
						if(split[j].equals(""))
						{
							st.append(split[j+1] +" ");	
							j=j+2;
							flag=1;
						}
						else
						{
							st.append(split[j] +" ");	
							j++;
							flag=1;
						}

					}
					else
					{
						
						flag =0;
						st.append(split[j] +" ");
						j++;
						Parser.parsing(st.toString().split(" "));
					}
				}
				st.append(" ");
		
			
			}
			else
			{
				result=Parser.parsing(split);

			}
		}


	return result;

	}
}
