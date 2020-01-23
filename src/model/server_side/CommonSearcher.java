package server_side;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public abstract class CommonSearcher<T> implements Searcher<T> {
	protected PriorityQueue<State<T>> openList;
	private int evaluatedNodes;
	protected Set<State<T>> closedList;
	public CommonSearcher() 
	{
	openList=new PriorityQueue<State<T>>();
	evaluatedNodes=0;
	closedList = new HashSet<>();
	}
	protected State<T> popOpenList()
	{
	evaluatedNodes++;
	return openList.poll();
	}

		
	public void addToOpenList(State<T> s)
	{
		openList.add(s);
	}
	

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
		}
	
	public ArrayList<State<T>> backTrace(State<T> init, State<T> goal)
	{
		ArrayList<State<T>> stp = new ArrayList<>();
		while (!goal.equals(init))
		{
			stp.add(goal);
			goal = goal.getCameFrom();
		}
		stp.add(init);
		return stp;
	}


}
