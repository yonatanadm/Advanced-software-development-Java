package server_side;


import java.util.ArrayList;
import java.util.HashMap;

public class FileCacheManager implements CacheManager<Searchable<Point>, ArrayList<State<Point>>> 

{
	
	HashMap<Searchable<Point>,  ArrayList<State<Point>>> map;
	
	public FileCacheManager() {
		map = new HashMap<>();
	}
	



	@Override
	public ArrayList<State<Point>> searchSolution(Searchable<Point> problem)
	{

		return map.get(problem);
	
		 
	}
	
	public void saveSolution(Searchable<Point> problem,ArrayList<State<Point>> solution)
	{
		

		map.put(problem, solution);
	}

}
