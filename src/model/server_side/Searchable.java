package server_side;

//import java.awt.List;
import java.util.ArrayList;


public interface Searchable<T> 
{
	public State<T> getInitialState();
	public State<T> getGoalState();
	public ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
