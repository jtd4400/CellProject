package com.example.cellproject;

import javafx.application.Application;
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
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application {
    static Label[][] grid;
    private int activeCells;

    private final int gridWidth = 20;
    private final int gridHeight = 20;
    private static GridPane gp;
    private Label rc;
    private Label bl;
    private Label green;
    private Button starter;
    private Button ender;
    CellManager cm = new CellManager(60, this);

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Cell Simulation");
        grid = new Label[gridWidth][gridHeight];
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        BorderPane bp = new BorderPane();
        bp.setBackground(Background.fill(Color.DIMGRAY));
        gp = new GridPane();
        gp.setHgap(1);
        gp.setVgap(1);
        gp.setMaxWidth(150);
        gp.setMaxHeight(150);

        for (int r = 0; r < gridWidth; r++) {
            for (int c = 0; c < gridHeight; c++) {
                Label l = cm.ParseStringToLabel(r, c);
                l.setMinSize(30,30);
                l.setMaxSize(30,30);
                gp.add(l, c, r);
                grid[c][r] = l;
            }
        }
        bp.setCenter(gp);
        GridPane dis_info = new GridPane();
        dis_info.setHgap(1);
        dis_info.setVgap(5);
        rc = new Label("Red Cells: " + cm.getRedCount());
        rc.setStyle( """
                            -fx-text-fill: white;
                            -fx-font-size: 40;
                """);
        bl = new Label("Blue Cells: " + cm.getBlueCount());
        bl.setStyle( """
                            -fx-text-fill: white;
                            -fx-font-size: 40;
                """);
        green = new Label("Green Cells: " + cm.getGreenCount());
        green.setStyle( """
                            -fx-text-fill: white;
                            -fx-font-size: 40;
                """);
        dis_info.add(rc, 0, 0);
        dis_info.add(bl, 0, 1);
        dis_info.add(green, 0, 2);
        BorderPane left1 = new BorderPane();
        left1.setMinWidth(400);
        left1.setMinHeight(400);
        left1.setMaxWidth(400);
        left1.setBackground(Background.fill(Color.BLACK));
        GridPane leftButtons = new GridPane();
        leftButtons.setVgap(10);

        Label choiceLabel = new Label("Select BG Color:");
        choiceLabel.setStyle( """
                            -fx-text-fill: white;
                            -fx-font-size: 40;
                """);
        ChoiceBox<String> b2 = new ChoiceBox<>();
        b2.setMinSize(100, 50);
        leftButtons.add(choiceLabel,0,0);
        leftButtons.add(b2,0,1);
        GridPane left2 = new GridPane();
        Button b1 = new Button("Randomly Kill Half");
        b1.setMinSize(400,50);
        b1.setStyle("""
                            -fx-text-fill: black;
                            -fx-font-size: 40;
                """);
        b1.setOnAction(event -> killHalf());
        starter = new Button("Advance");
        starter.setOnAction(event -> {
            cm.AdvanceGeneration();
        });
        starter.setStyle("""
                            -fx-text-fill: black;
                            -fx-font-size: 40;
                """);
        starter.setMinSize(400,50);
        left2.setHgap(5);
        left2.add(b1,0,0);
        left2.add(starter, 0, 1);
        left1.setTop(dis_info);
        left1.setCenter(leftButtons);
        left1.setBottom(left2);
        bp.setLeft(left1);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
    }

    public void killHalf() {
        cm.RandomlyKillHalf();
        grid = cm.UpdateGrid(grid);
    }

    public void update() {
        for (int r = 0; r < gridWidth; r++) {
            for (int c = 0; c < gridHeight; c++) {
                Label l = cm.ParseStringToLabel(r, c);
                l.setMinSize(30,30);
                l.setMaxSize(30,30);
                gp.add(l, c, r);
                grid[c][r] = l;
            }
        }
        rc.setText(String.valueOf("Red Cells: " + cm.getRedCount()));
        bl.setText(String.valueOf("Blue Cells: " + cm.getBlueCount()));
        green.setText(String.valueOf("Green Cells: " + cm.getGreenCount()));
    }

    public static void main(String[] args) {
        launch();
    }
}