package server_side;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class BestFirstSearch<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<State<T>> search(Searchable<T> searchable) {

		PriorityQueue<State<T>> openList = new PriorityQueue<>((s1, s2) -> Double.compare(s1.getCost(), s2.getCost()));

		openList.add(searchable.getInitialState());
 
		while (!openList.isEmpty()) {
			State<T> currentState = openList.remove();
			closedList.add(currentState);

			if (currentState.equals(searchable.getGoalState()))
					{
				return backTrace(searchable.getInitialState(),currentState);
			}
			ArrayList<State<T>> possibleStates = searchable.getAllPossibleStates(currentState);

			for (State<T> child : possibleStates) {
				if (!closedList.contains(child) && !openList.contains(child))
				{
					child.setCameFrom(currentState);
					openList.add(child);
				} 
				else 
				{
					if (!openList.contains(child)) 
					{
						openList.add(child);

					}

				}
			}
		}
		return null;
}
}