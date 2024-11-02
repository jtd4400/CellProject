package com.example.cellproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Cell Simulation");
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        gp.setHgap(0);
        gp.setVgap(0);
        gp.setMaxWidth(150);
        gp.setMaxHeight(150);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Label l = new Label("");
                l.setBackground(Color.RED);
                l.setBounds(15,15,15,15);
                gp.add(l, i, j);

            }
        }
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}