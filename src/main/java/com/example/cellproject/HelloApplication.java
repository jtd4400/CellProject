package com.example.cellproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    static Label[][] grid;
    private int activeCells;

    private final int gridWidth = 20;
    private final int gridHeight = 20;
    static CellManager cm = new CellManager(30);

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Cell Simulation");
        grid = new Label[gridWidth][gridHeight];
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        gp.setHgap(1);
        gp.setVgap(1);
        gp.setMaxWidth(150);
        gp.setMaxHeight(150);

        for (int r = 0; r < gridWidth; r++) {
            for (int c = 0; c < gridHeight; c++) {
                Label l = cm.ParseStringToLabel(r, c);
                gp.add(l, c, r);
                grid[c][r] = l;
            }
        }
        bp.setCenter(gp);
        GridPane dis_info = new GridPane();
        dis_info.setHgap(1);
        dis_info.setVgap(5);
        Label rc = new Label("Red Cells: " + cm.getRedCount());
        rc.setStyle( """
                            -fx-text-fill: white;
                """);
        Label bl = new Label("Blue Cells: " + cm.getBlueCount());
        bl.setStyle( """
                            -fx-text-fill: white;
                """);
        Label green = new Label("Green Cells: " + cm.getGreenCount());
        green.setStyle( """
                            -fx-text-fill: white;
                """);
        dis_info.add(rc, 0, 0);
        dis_info.add(bl, 0, 1);
        dis_info.add(green, 0, 2);
        BorderPane left1 = new BorderPane();
        left1.setMinWidth(200);
        left1.setMinHeight(400);
        left1.setMaxHeight(400);
        left1.setBackground(Background.fill(Color.BLACK));
        GridPane leftButtons = new GridPane();
        leftButtons.setVgap(10);
        Button b1 = new Button("Randomly Kill Half");
        Label choiceLabel = new Label("Select BG Color:");
        ChoiceBox<String> b2 = new ChoiceBox<>();
        leftButtons.add(b1,0,0);
        leftButtons.add(choiceLabel,0,1);
        leftButtons.add(b2,0,2);
        GridPane left2 = new GridPane();
        left2.setVgap(10);
        Label generation = new Label("Generation: ");
        Button starter = new Button("Start");
        left2.add(generation, 0, 0);
        left2.add(starter, 0, 1);
        left1.setTop(dis_info);
        left1.setCenter(leftButtons);
        left1.setBottom(left2);
        bp.setLeft(left1);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}