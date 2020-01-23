package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class MyTestClientHandler implements ClientHandler {
	
public MyTestClientHandler(Solver<String,String> solver, CacheManager<String,String> cm) {
		this.solver = solver;
		this.cm = cm;
	}




//public MyTestClientHandler() {
//	// TODO Auto-generated constructor stub
//}




Solver<String,String> solver;
CacheManager<String,String> cm;
	
	
	@Override
	public void handleClient(InputStream inputStream, OutputStream outputStream) 
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter pw = new PrintWriter(outputStream);
		String problem=null;
//		try {
//			problem = bufferedReader.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			while(!(problem = bufferedReader.readLine()).equals("end"))
			{
				String solution=cm.searchSolution(problem);
				if(solution!=null)
				{
//					try {
//						outputStream.write(solution.getBytes());
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					pw.println(solution);
					pw.flush();
				}
				else
				{
					solution= solver.solve(problem);
					cm.saveSolution(problem, solution);

					pw.println(solution);
					pw.flush();

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.close();
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
