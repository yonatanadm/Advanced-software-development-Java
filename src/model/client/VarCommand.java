package commands;

public class VarCommand implements Command {

	@Override
	public Double doCommand(String[] commandParts) {
		String[] arr = new String[commandParts.length - 1];

		int k = 0;
		// build the string which holds varx=simx
		for (int i = 1; i < commandParts.length; i++) {
			arr[k] = commandParts[i];
			k++;
		}

		AssignmentCommand ac = new AssignmentCommand();
		ac.doCommand(arr);
		return (double) 0;
	}

}
