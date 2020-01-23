package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server_side.Point;
import viewModel.ViewModel;
import view.MapDisplayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.text.Position;

public class MainWindowController implements Initializable, Observer {
	@FXML
	private Circle joyCirc;
	@FXML
	private Circle frameCirc;
	double sceneX, sceneY;
	double translateX, translateY;
	@FXML
	public MapDisplayer md;
	@FXML
	public TextArea myTextbox;
	ViewModel viewModel;
	@FXML
	Slider rudderSlider;
	@FXML
	Slider throttleSlider;
	public DoubleProperty vaileron;
	public DoubleProperty velevator;
	@FXML
	public RadioButton autoPilot;
	@FXML
	public RadioButton manualPilot;
	@FXML
	public Aircraft aircraft;
	@FXML
	public Path path;
	public ToggleGroup group;
	public static boolean autop;
	public static boolean manup;
	Position airplaneLocation;
	public String ipSolver;
	public int portSolver;
	public Point initCoordinate;
	public boolean connectedToCalc = false;
	public boolean loadFile = false;
	public boolean calcPath = false;
	public boolean connectToSim = false;
	public int[][] array;
	Point goal;
	int initX;
	int initY;
	Double pixel;
	String[][] matrixProblem;
	String solution;
	int h;
	int w;
	double initXSol;
	double initYsol;


	
	
	public void calcPath() {
		if (loadFile) {

			goal = new Point(md.xDes, md.yDes);
			initXSol = aircraft.nxlong;
			initYsol = aircraft.nx;
			initX = (int) (Math.ceil(aircraft.nxlong / w) - 1);
			initY = (int) (Math.ceil(aircraft.nx / h) - 1);
			if (initX < 0) {
				initX = 0;
			}
			if (initY < 0) {
				initY = 0;
			}

			//if (goal.getX() >= 0 && goal.getY() >= 0) {
			if (md.flag) {

				if (!connectedToCalc) {

					Stage stage = new Stage();
					VBox box = new VBox(20);
					Label ipLabel = new Label("Enter ip of a solver server:");
					Label portpLabel = new Label("Enter port of a solver server:");
					TextField ipInput = new TextField();
					TextField portInput = new TextField();
					Button button = new Button("Connect");
					box.getChildren().addAll(ipLabel, ipInput, portpLabel, portInput, button);
					stage.setScene(new Scene(box, 350, 250));
					stage.show();
					button.setOnAction(e -> {
						ipSolver = ipInput.getText();
						portSolver = Integer.parseInt(portInput.getText());
						stage.close();
						solution = viewModel.connectSolver(ipSolver, portSolver, matrixProblem, initX, initY,goal.getX(), goal.getY());
						connectedToCalc = true;
						path.setVals(h, w, initXSol, initYsol);
						path.drawPath(solution);
					});

					
				} else {
		
					solution = viewModel.connectSolver(ipSolver, portSolver, matrixProblem, initX, initY,goal.getX(), goal.getY());
					path.setVals(h, w, initXSol, initYsol);
					path.drawPath(solution);
				}
			} else {
				Stage stage = new Stage();
				VBox box = new VBox(20);
				Label ipLabel = new Label("   YOU MUST TO CHOOSE DESTINITON.");
				box.getChildren().add(ipLabel);
				stage.setScene(new Scene(box, 350, 50));
				stage.setTitle("WARNING");
				stage.show();
			}

		} 
	else {
			Stage stage = new Stage();
			VBox box = new VBox(20);
			Label ipLabel = new Label("   YOU MUST LOAD YOUR CSV FILE BEFORE.");
			box.getChildren().add(ipLabel);
			stage.setScene(new Scene(box, 350, 50));
			stage.setTitle("WARNING");
			stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vaileron = new SimpleDoubleProperty();
		velevator = new SimpleDoubleProperty();
		rudderSlider.valueProperty().addListener((ov, old_val, new_val) -> { // Slider data binding
			if (manup)
				viewModel.sendRudderValues();
		});
		throttleSlider.valueProperty().addListener((ov, old_val, new_val) -> { // Slider data binding
			if (manup)
				viewModel.sendThrottleValues();
		});

	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.rudder.bind(this.rudderSlider.valueProperty());
		viewModel.throttle.bind(this.throttleSlider.valueProperty());
		viewModel.aileron.bind(vaileron);
		viewModel.elevator.bind(velevator);
		this.aircraft.latitude.bind(viewModel.latitude);
		this.aircraft.longitude.bind(viewModel.longitude);
	}

	public void radioClick() 
	{
		group = new ToggleGroup();
		autoPilot.setToggleGroup(group);
		manualPilot.setToggleGroup(group);
		if(connectToSim)
		{
		Reflection ref = new Reflection();
		ref.setInput(new SepiaTone());
		if (group.getSelectedToggle().equals(manualPilot)) {
			manualPilot.setEffect(ref);
			autoPilot.setEffect(null);
			manup = true;
			autop = false;
			System.out.println("in manual");
		}
		if (group.getSelectedToggle().equals(autoPilot)) {
			manualPilot.setEffect(null);
			autoPilot.setEffect(ref);
			manup = false;
			autop = true;
			autoPilotfunc();
		}
		}
		else
		{
			Stage stage = new Stage();
			VBox box = new VBox(20);
			Label ipLabel = new Label("   YOU MUST CONNECT TO THE SIMULATOR.");
			box.getChildren().add(ipLabel);
			stage.setScene(new Scene(box, 350, 50));
			stage.setTitle("WARNING");
			stage.show();
		}
	}

	public void connect() {
		if(!connectToSim)
		{
		connectToSim = true;
		String[] ip = new String[1];
		int[] port = new int[1];
		Stage stage = new Stage();
		VBox box = new VBox(20);
		Label ipLabel = new Label("Enter the ip number:");
		Label portpLabel = new Label("Enter the port number:");
		TextField ipInput = new TextField();
		TextField portInput = new TextField();
		Button button = new Button("Submit");
		box.getChildren().addAll(ipLabel, ipInput, portpLabel, portInput, button);
		stage.setScene(new Scene(box, 350, 250));
		stage.show();
		button.setOnAction(e -> {
			ip[0] = ipInput.getText();
			port[0] = Integer.parseInt(portInput.getText());
			viewModel.connect(ip[0], port[0]);
			stage.close();
		});

	}
		else
		{
			Stage stage = new Stage();
			VBox box = new VBox(20);
			Label ipLabel = new Label("  YOU'VE ALREADY CONNECTED.");
			box.getChildren().add(ipLabel);
			stage.setScene(new Scene(box, 350, 50));
			stage.setTitle("WARNING");
			stage.show();
		}
	}

	public void chooseTxtfile() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Txt File");
		File choosen = fileChooser.showOpenDialog(stage);
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(choosen));
			try {
				while ((line = reader.readLine()) != null) {

					myTextbox.appendText(line + "\r\n");

				}
			} catch (IOException e) {

			}
		} catch (FileNotFoundException e) {

		}
	}

	public void autoPilotfunc() {
		 
		System.out.println("in autopilot");
		LinkedList<String> list = new LinkedList<>();
		if (!(myTextbox.getText()).isEmpty()) {
			for (String line : myTextbox.getText().split("\n")) {
				list.add(line);
			}
			String[] arr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = list.get(i);
			}
			viewModel.autoPilot(arr);
		} else {
			Stage stage = new Stage();
			VBox box = new VBox(20);
			Label ipLabel = new Label("  YOU MUST ENTER TEXT OR CHOOSE TEXT FILE.");
			box.getChildren().add(ipLabel);
			stage.setScene(new Scene(box, 350, 50));
			stage.setTitle("WARNING");
			stage.show();
		}
	

}

	public void loadFile() {
		loadFile = true;
		List<List<String>> lines = new ArrayList<>();
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Data File");
		File choosen = fileChooser.showOpenDialog(stage);

		if (choosen != null) {

			try {
				Scanner inputStream;
				inputStream = new Scanner(choosen);

				while (inputStream.hasNext()) {
					String line = inputStream.next();
					String[] values = line.split(",");

					// this adds the currently parsed line to the 2-dimensional string array
					lines.add(Arrays.asList(values));
				}

				h = lines.size() - 2;
				w = lines.get(4).size();

				List<String> allVals = new LinkedList<>();
				array = new int[h][w];
				int max = 0;
				int min = 100000;
				for (List<String> line : lines) {
					for (String value : line) {
						allVals.add(value);

					}
				}

				int k = 0;
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						array[i][j] = Integer.parseInt(allVals.get(k + 4));

						if (array[i][j] > max)
							max = array[i][j];
						else if (array[i][j] < min) {
							min = array[i][j];
						}
						k++;
					}
				}
				// covert the int array to string array
				matrixProblem = new String[array.length][array[2].length];
				for (int i = 0; i < array.length; i++) {
					for (int j = 0; j < array[i].length; j++) {
						matrixProblem[i][j] = array[i][j] + "";

					}
				}

				md.setMax(max);
				md.setMin(min);
				md.setMap(array);
				md.drawMap();
				md.putPlaneIcon();
				aircraft.setAircraft();
				viewModel.getAircraftPosition();
				// initX = Double.parseDouble(allVals.get(0));
				// initY = Double.parseDouble(allVals.get(1));

				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	public void innerDragged(MouseEvent e) {

		if (manup) {
			draggedJoystick(e);
			viewModel.sendElevatorValues(); // Sending orders to simulator
			viewModel.sendAileronValues();
		}

	}

	public void innerPressed(MouseEvent e) {
		if (connectToSim) {
			if (manup)
				pressedJoystick(e);
		} else {
			Stage stage = new Stage();
			VBox box = new VBox(20);
			Label ipLabel = new Label("   YOU MUST CONNECT TO THE SIMULATOR.");
			box.getChildren().add(ipLabel);
			stage.setScene(new Scene(box, 350, 50));
			stage.setTitle("WARNING");
			stage.show();
		}
	}

	public void innerReleased(MouseEvent e) {

		if (manup) {
			released(e);
			viewModel.sendElevatorValues();
			viewModel.sendAileronValues();
		}

	}

	public void pressedJoystick(MouseEvent m) {
//	public EventHandler<MouseEvent> pressEventHandler = new EventHandler<MouseEvent>() {

		sceneX = m.getSceneX();
		sceneY = m.getSceneY();
		translateX = ((Circle) (m.getSource())).getTranslateX();
		translateY = ((Circle) (m.getSource())).getTranslateY();

	}

	public void draggedJoystick(MouseEvent t) {
//	public EventHandler<MouseEvent> draggedEventHandler = new EventHandler<MouseEvent>() {

		double centerX = frameCirc.getTranslateX() + frameCirc.getRadius() - joyCirc.getRadius();
		double centerY = frameCirc.getTranslateY() - frameCirc.getRadius() - joyCirc.getRadius();
		double radius = frameCirc.getRadius();

		double offsetX = t.getSceneX() - sceneX;
		double offsetY = t.getSceneY() - sceneY;
		double newTranslateX = translateX + offsetX;
		double newTranslateY = translateY + offsetY;

		double slant = Math.sqrt(Math.pow(newTranslateX - centerX, 2) + Math.pow(newTranslateY - centerY, 2));
		if (slant <= radius) {
			((Circle) (t.getSource())).setTranslateX(newTranslateX);
			((Circle) (t.getSource())).setTranslateY(newTranslateY);
			frameCirc.maxWidth(centerY);

			double xMax = radius + centerX;
			double yMin = radius + centerY;
			double xMin = (radius - centerX) * -1;
			double yMax = (radius - centerY) * -1;
			double x_ailron = newTranslateX;
			double y_elevator = newTranslateY;
			double n_ailron = (((x_ailron - xMin) * (2)) / (xMax - xMin)) - 1;
			double n_elevator = (((y_elevator - yMin) * (2)) / (yMax - yMin)) - 1;
			vaileron.set(n_ailron);
			velevator.set(n_elevator);

		}
		// if the joystick go out so we make him stay on the big circle
		else {
			double alpha = Math.atan((newTranslateY - centerY) / (newTranslateX - centerX));
			if ((newTranslateX - centerX) < 0)
				alpha = alpha + Math.PI;
			double newX = Math.cos(alpha) * radius + translateX;
			double newY = Math.sin(alpha) * radius + translateY;
			((Circle) (t.getSource())).setTranslateX(newX);
			((Circle) (t.getSource())).setTranslateY(newY);
		}

	};

	public void released(MouseEvent m) {
		((Circle) (m.getSource()))
				.setTranslateX(frameCirc.getTranslateX() + frameCirc.getRadius() - joyCirc.getRadius());
		((Circle) (m.getSource()))
				.setTranslateY(frameCirc.getTranslateY() - frameCirc.getRadius() - joyCirc.getRadius());
	}

	@Override
	public void update(Observable o, Object arg) {
		this.aircraft.position();

	}

}