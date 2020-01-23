package server_side;

import java.util.ArrayList;

public class MySolver implements Solver<Searchable<Point>, ArrayList<State<Point>>> {

	private Searcher<Point> searcher;

	public MySolver(Searcher<Point> searcher) {
		this.searcher = searcher;
	}

	@Override
	public ArrayList<State<Point>> solve(Searchable<Point> problem) {
		ArrayList<State<Point>> temp = new ArrayList<>();
		temp = searcher.search(problem);

		return temp;

	}

}
