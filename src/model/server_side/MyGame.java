package server_side;

import java.util.ArrayList;

public class MyGame implements Searchable<Point> {

	
	private int[][] array;
	private Point pInit;
	private Point pGoal;
	 
	
	
	public MyGame(int[][] array, Point pinit, Point pgoal) {
		this.array = array;
		this.pInit = pinit;
		this.pGoal = pgoal;
	}

	@Override
	public State<Point> getInitialState()
{
		State<Point> stateInit=new State<Point>(pInit);
		stateInit.setCost(array[pInit.getX()][pInit.getY()]);

		return stateInit;
	}

	@Override
	public State<Point> getGoalState() 
	{
		
		State<Point> stateGoal=new State<Point>(pGoal);
		stateGoal.setCost(array[pGoal.getX()][pGoal.getY()]);

		return 	stateGoal;	
	}

	@Override
	public ArrayList<State<Point>> getAllPossibleStates(State<Point> s) 
	{
		
		ArrayList<State<Point>> arrayPoss= new ArrayList<>();
		int x=s.getState().getX();
		int y=s.getState().getY();
		if(x-1>0)
		{
			Point pointUp= new Point(x-1, y);
			State<Point> stateUp=new State<Point>(pointUp);
			stateUp.setCost(array[x-1][y] +s.getCost());
			arrayPoss.add(stateUp);
		}
		if(y-1>0)
		{
			Point pointLeft= new Point(x, y-1);
			State<Point> stateLeft=new State<Point>(pointLeft);
			stateLeft.setCost(array[x][y-1]+s.getCost());
			arrayPoss.add(stateLeft);
		}
		int rows=array.length -1;
		int cols= array[0].length -1;
		if(x+1<=rows)
		{
			Point pointDown= new Point(x+1, y);
			State<Point> stateDown=new State<Point>(pointDown);
			stateDown.setCost(array[x+1][y]+s.getCost());
			arrayPoss.add(stateDown);
		}
		if(y+1<=cols)
		{
			Point pointRight= new Point(x, y+1);
			State<Point> stateRight=new State<Point>(pointRight);
			stateRight.setCost(array[x][y+1]+s.getCost());
			arrayPoss.add(stateRight);
		}
		return arrayPoss;
	}



}
