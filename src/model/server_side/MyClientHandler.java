package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class MyClientHandler implements ClientHandler {
	private Solver<Searchable<Point>, ArrayList<State<Point>>> solver;
	private CacheManager<Searchable<Point>, ArrayList<State<Point>>> cm;

	public MyClientHandler()
	{
	this.cm = new FileCacheManager();	
	this.solver = new MySolver(new BestFirstSearch<Point>());
	}
	
	@Override
	public void handleClient(InputStream inputStream, OutputStream outputStream)
	{
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter pw = new PrintWriter(outputStream);
		ArrayList<String> clientProblem = new ArrayList<>();
		ArrayList<State<Point>> clientSolution = new ArrayList<>();
		ArrayList<String> test = new ArrayList<>();
		String lineReader = new String();
		String lineReaderInit = new String();
		String lineReaderGoal = new String();
		
		int row = 0;
		int col=0;
		try {
			while (!(lineReader = bufferedReader.readLine()).equals("end"))
			{
				row++;
				String[] split=lineReader.split(",");
				for(String s : split)
				{
					clientProblem.add(s);
				}
			}
			col=clientProblem.size()/row;
			int[][] array = createMatrix(clientProblem,row,col);
			printArray(array,row,col);
			lineReaderInit=bufferedReader.readLine();
			String[] splitInit=lineReaderInit.split(",");
			for(String s : splitInit)
			{
				test.add(s);
			}
			int initX= Integer.parseInt(test.get(0));
			int initY= Integer.parseInt(test.get(1));
			test.clear();
			lineReaderGoal=bufferedReader.readLine();
			String[] splitGoal=lineReaderGoal.split(",");
			for(String s : splitGoal)
			{
				test.add(s);
			}
			int goalX= Integer.parseInt(test.get(0));
			int goalY= Integer.parseInt(test.get(1));
			Point pointInit= new Point(initX,initY);
			Point pointGoal= new Point(goalX,goalY);
			MyGame problem=new MyGame(array, pointInit, pointGoal) ;
			clientSolution=cm.searchSolution(problem);
			if(clientSolution!=null)
			{
				String solution=covertToString(clientSolution,pointInit);
				pw.println(solution);
				pw.flush();
			}
			else
			{

				clientSolution=solver.solve(problem);
				
				cm.saveSolution(problem, clientSolution);
			
				Collections.reverse(clientSolution);
				String solution=covertToString(clientSolution,pointInit);
			
				pw.println(solution);
				pw.flush();
			}
			
		} catch (IOException e) {

		}
		pw.close();
		try {
			bufferedReader.close();
		} catch (IOException e) {

		}

	}
	private void printArray(int[][] array, int row, int col)
	{
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				System.out.print(array[i][j] +" ");
			}
			System.out.println();
		}
		
	}

	private String covertToString(ArrayList<State<Point>> clientSolution,Point pointInit)
	{
		StringBuilder stringSolution=new StringBuilder();
		int x=pointInit.getX();
		int y=pointInit.getY();
		for(int i=1;i<clientSolution.size();i++)
		{
			if((clientSolution.get(i).getState().getX()==x+1)&&(clientSolution.get(i).getState().getY()==y))
			{
				stringSolution.append("Down,");
				x=x+1;
			}
			else if ((clientSolution.get(i).getState().getX()==x-1)&&(clientSolution.get(i).getState().getY()==y))
			{
				stringSolution.append("Up,");
				x=x-1;
			}
			else if ((clientSolution.get(i).getState().getX()==x)&&(clientSolution.get(i).getState().getY()==y+1))
			{
				stringSolution.append("Right,");
				y=y+1;
			}
			else if ((clientSolution.get(i).getState().getX()==x)&&(clientSolution.get(i).getState().getY()==y-1))
			{
				stringSolution.append("Left,");
				y=y-1;
			}
	
		}
		

		stringSolution.deleteCharAt(stringSolution.length()-1  );
		return stringSolution.toString();
	}

	public int[][] createMatrix(ArrayList<String> array,int row,int col)
	{
		int k=0;
		int[][] matrix = new int[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				matrix[i][j]= Integer.parseInt(array.get(k));
				k++;
			}
		}
		return matrix;
		
	}

}


