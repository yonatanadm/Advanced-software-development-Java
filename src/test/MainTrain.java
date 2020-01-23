package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
;

public class MainTrain {

	public static void main(String[] args) throws IOException 
	{

		ArrayList<String> arrayList = new ArrayList<>();	
		String fileName = "Simulator.txt";
		String s=null;
		BufferedReader reader = null;
			try 
			{
				reader = new BufferedReader(new FileReader(fileName));
			} 
			catch (FileNotFoundException e) {}
			try 
			{
				while((s=reader.readLine())!=null)
				{
					arrayList.add(s);
				}
				String[] strings= new String[arrayList.size()];
				for(int i=0;i<arrayList.size();i++)
				{
					strings[i]=arrayList.get(i);
					
				}
				MyInterpreter.interpret(strings);

			} 
			catch (IOException e) {}

	}

}
