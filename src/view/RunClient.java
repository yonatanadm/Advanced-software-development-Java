package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.ModelFlight;
import model.SolverModel;
import viewModel.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class RunClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ModelFlight m=new ModelFlight();
			SolverModel s=new SolverModel();
			ViewModel viewModel =new ViewModel(m,s);
			s.addObserver(viewModel);
			FXMLLoader fxl=new FXMLLoader();
			BorderPane root = fxl.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController mwc = fxl.getController();
			viewModel.addObserver(mwc);
			mwc.setViewModel(viewModel);
			Scene scene = new Scene(root, 1100, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}