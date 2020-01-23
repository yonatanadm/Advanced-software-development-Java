package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class DataReaderServer {

	int port;
	int rate;
	public ArrayList<String> vars;
	public static volatile boolean stop;
	public String fileName;

	public DataReaderServer(int port, int rate) {
		this.port = port;
		this.rate = rate;
		stop = false;
		this.vars = new ArrayList<>();
		this.fileName = "Vars.txt";
		try {

			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String s = null;
			try {
				while ((s = reader.readLine()) != null) {
					vars.add(s);
				}
			} catch (IOException e) {

			}
		} catch (FileNotFoundException e) {

		}
	}

	public void runServer() {
		try {
			ServerSocket theClient = new ServerSocket(port, rate);
			Socket client = theClient.accept();
			System.out.println("Client connected");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			while (!stop) {
				int i = 0;
				String[] input = in.readLine().split(",");
				for (String s : input) {
					double lin = Double.parseDouble(s);
					if (SymbolTable.map.containsKey(vars.get(i))) {
						Variable v = SymbolTable.map.get(vars.get(i));
						v.setValue(lin);
					//	double d = v.getValue();
						SymbolTable.map.put(vars.get(i), v);
						//double d2 = SymbolTable.map.get(vars.get(i)).getValue();
					} else {
						SymbolTable.map.put(vars.get(i), new Variable(lin, vars.get(i)));

					}
					i++;

				}
			}
			in.close();
			client.close();
			theClient.close();
		} catch (IOException e) {

		}

	}

	public static void close() {
		stop = true;
	}
}
