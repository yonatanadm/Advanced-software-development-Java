package server_side;

public class State<T> implements Comparable<State<T>> {
	private T state;
	private double cost;
	private State<T> cameFrom;

	public State(T state) {
		this.state = state;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int compareTo(State<T> s) {
		return (int) (this.cost - s.getCost());
	}

	@Override
	public boolean equals(Object o) {
		@SuppressWarnings("unchecked")
		State<T> s = (State<T>) o;
		return state.equals(s.state);
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(state.hashCode());
//	}

}
