package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.ModelFlight;
import model.SolverModel;
import server_side.Point;

public class ViewModel extends Observable implements Observer 
{
	ModelFlight m;
	SolverModel s;
	public DoubleProperty rudder;
	public DoubleProperty aileron;
	public DoubleProperty elevator;
	public DoubleProperty throttle; 
	Point initVm;
	Point goalVm;
	public DoubleProperty latitude;
	public DoubleProperty longitude;

	
	public ViewModel(ModelFlight m, SolverModel s)
	{
		this.m = m;
		this.s=s;
		rudder=new SimpleDoubleProperty();
		aileron=new SimpleDoubleProperty();
		elevator=new SimpleDoubleProperty();
		throttle=new SimpleDoubleProperty();
        latitude=new SimpleDoubleProperty();
        longitude=new SimpleDoubleProperty();
     
	}
	
	public void connect(String ip, int port )
	{
		m.connect(ip, port);
	}
	
	public void autoPilot(String[] arr)
	{
		m.autoPilot(arr);
	}


	public void sendRudderValues() {
		m.sendCommandToSimulator("set /controls/flight/rudder ",rudder.get());
	}
	public void sendAileronValues() {
		m.sendCommandToSimulator("set /controls/flight/aileron ",aileron.get());
	}
	public void sendThrottleValues() {
		m.sendCommandToSimulator("set /controls/engines/current-engine/throttle ",throttle.get());
	}
	public void sendElevatorValues() {
		m.sendCommandToSimulator("set /controls/flight/elevator ",elevator.get());
	}

	public String connectSolver(String ipSolver, int portSolver , String[][] matrixProblem, int initX, int initY, int goalX, int goalY)
	{
		return s.connectSolver(ipSolver,portSolver,matrixProblem,initX,initY,goalX,goalY);
		
	}
	
	public String getSolution(int initX, int initY, int golaX, int goalY)
	{
		return s.getSolution(initX, initY, golaX, goalY);
	}
    public void getAircraftPosition(){
        s.getAircraftPosition();
    }
	@Override
	public void update(Observable o, Object arg)
	{
        latitude.setValue(Double.parseDouble(((String[])arg)[0]));
        longitude.setValue(Double.parseDouble(((String[])arg)[1]));
        this.setChanged();
        this.notifyObservers();

   
		
	}
	
}
