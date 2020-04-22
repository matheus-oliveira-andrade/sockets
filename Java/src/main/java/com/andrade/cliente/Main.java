package com.andrade.cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {							
		Parent root = FXMLLoader.load(getClass().getResource("views/FXMLInicial.fxml"));
        primaryStage.setTitle("Cliente");
        primaryStage.setScene(new Scene(root, 587, 410));
        primaryStage.show();
	}

}
