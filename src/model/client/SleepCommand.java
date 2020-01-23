package commands;

public class SleepCommand implements Command {

	@Override
	public Double doCommand(String[] commandArguments) {
		
		try {
			Thread.sleep(Integer.parseInt(commandArguments[1]));
		} catch (InterruptedException e) {

		}
		return (double) 0;
	}

}
