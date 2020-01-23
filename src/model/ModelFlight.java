package model;


import java.util.Observable;
import commands.ConnectCommand;
import commands.OpenDataServerCommand;
import test.MyInterpreter;
import view.MainWindowController;

public class ModelFlight extends Observable {
	public Thread a;

	public ModelFlight() {
		OpenDataServerCommand o = new OpenDataServerCommand();
		String[] str = new String[3];
		str[0] = "b";
		str[1] = "5400";
		str[2] = "10";
		o.doCommand(str);
		a = null;
	}

	public void connect(String ip, int port) {
		String[] array = new String[3];
		array[0] = "connect";
		array[1] = ip;
		array[2] = port + "";
		ConnectCommand c = new ConnectCommand();
		c.doCommand(array);
	}

	public void autoPilot(String[] arr) {

		if (MainWindowController.autop) {

			a = new Thread(() -> {
				MyInterpreter.interpret(arr);

			});
			a.start();
		}

	}

	public void sendCommandToSimulator(String path, double new_val) {

		ConnectCommand.out.println(path + new_val);
		ConnectCommand.out.flush();
	}

}
