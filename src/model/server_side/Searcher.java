package server_side;

import java.util.ArrayList;

public interface Searcher<T> {
	public ArrayList<State<T>> search(Searchable<T> s);
	public int getNumberOfNodesEvaluated();

}
