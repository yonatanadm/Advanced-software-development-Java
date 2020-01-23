package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
//import server_side.MySerialServer;
import server_side.Server;
import test.SymbolTable;

public class SolverModel extends Observable {
	BufferedReader in;
	PrintWriter out_Solver;
	int initX;
	int initY;
	int goalX;
	int goalY;
	String[][] matrixProblem;
	String solution;
	Server server;

//	public SolverModel(int port) {
//		server = new MySerialServer(port);
//		server.start();
//	}
	
	public SolverModel() {

}

	public String connectSolver(String ipSolver, int portSolver, String[][] matrixProblem, int initX, int initY,
			int goalX, int goalY) {
		Socket server = null;
		try {
			server = new Socket(ipSolver, portSolver);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			out_Solver = new PrintWriter(server.getOutputStream());
			System.out.println("Connected to a solver server.");
		} catch (IOException e) {
		}

		this.matrixProblem = matrixProblem;
		this.initX = initX;
		this.initY = initY;
		this.goalX = goalX;
		this.goalY = goalY;
		solution = getSolution(initX, initY, goalX, goalY);
		return solution;
	}

	public String getSolution(int initX, int initY, int golaX, int goalY) {
		System.out.println("Sending the problem to the server ");
		for (int i = 0; i < matrixProblem.length; i++) {
			for (int j = 0; j < matrixProblem[i].length; j++) {
				out_Solver.print(matrixProblem[i][j] + ",");
			}
			out_Solver.println();
		}
		out_Solver.println("end");
		String init = initX + "," + initY;
		out_Solver.println(init);
		String goal = goalX + "," + goalY;
		out_Solver.println(goal);

		out_Solver.flush();
		try {
			solution = in.readLine();
		} catch (IOException e) {

		}

		return solution;
	}

	public void getAircraftPosition() {
		new Thread(() -> {
			while (true) {
				String[] postions = new String[2];
				postions[0] = String.valueOf(SymbolTable.map.get("/position/latitude-deg").value);
				postions[1] = String.valueOf(SymbolTable.map.get("/position/longitude-deg").value);
				this.setChanged();
				this.notifyObservers(postions);
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
				}
			}
		}).start();
		;
	}

}
