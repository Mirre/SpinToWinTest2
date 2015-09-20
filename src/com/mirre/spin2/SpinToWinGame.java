package com.mirre.spin2;
	
import com.mirre.spin2.api.Controller;
import com.mirre.spin2.view.SpinViewController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SpinToWinGame extends Application {
	
	private BorderPane rootLayout;
	private static Stage stage;

	@Override
	public void start(Stage stage) {
		SpinToWinGame.stage = stage;
		rootLayout = new BorderPane();
		rootLayout.setCenter(Controller.loadController(SpinViewController.class));
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}

}
