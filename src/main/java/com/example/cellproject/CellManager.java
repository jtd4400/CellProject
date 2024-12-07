package com.example.cellproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import java.util.Observer;

import java.util.Dictionary;

public class CellManager {
    String[][] cells = new String[20][20];
    int cellCount;
    int redCount;
    int greenCount;
    int blueCount;

    //region Getters
    public int getCellCount() {
        return cellCount;
    }
    public int getRedCount() {
        return redCount;
    }
    public int getGreenCount() {
        return greenCount;
    }
    public int getBlueCount() {
        return blueCount;
    }
    public HelloApplication app;
    //endregion


    public CellManager(int cellCount, HelloApplication app) {
        this.cellCount = cellCount;
        this.app = app;
        RandomizeLayout();
    }

    public void RandomizeLayout(){
        int cellCount = this.cellCount;
        while (cellCount > 0){
            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[row].length; col++) {
                    long randomNumber = Math.round(Math.random() * 1000);
                    if ((cells[row][col] == null || cells[row][col].isEmpty()) && randomNumber <= 30){
                        cells[row][col] = "Red";
                        redCount++;
                        cellCount--;
                    }
                    else if ((cells[row][col] == null || cells[row][col].isEmpty()) && randomNumber <= 60){
                        cells[row][col] = "Green";
                        greenCount++;
                        cellCount--;
                    }
                    else if ((cells[row][col] == null || cells[row][col].isEmpty()) && randomNumber <= 90){
                        cells[row][col] = "Blue";
                        blueCount++;
                        cellCount--;
                    }
                    else if (cells[row][col] == null){
                        cells[row][col] = "";
                    }
                    if (cellCount == 0) return;
                }
            }
        }
    }

    private int countNeighbors(int col, int row) {
        int neighbors = 0;
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (row > 0 && col > 0 && row < 19 && col < 19
                && !cells[col + i][row + j].isEmpty()){
                    neighbors++;
                }
            }
        }
        return neighbors;
    }

    private String getNeighborColor(int col, int row) {
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (!cells[col + i][row + j].isEmpty() && cells[col + i][row + j] != null){
                    return cells[col + i][row + j];
                }
            }
        }
        return "";
    }

    public void AdvanceGeneration() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                //If empty, make a new cell with the neighbor's color
                if (cells[row][col].isEmpty() && countNeighbors(col, row) == 3){
                    cells[row][col] = getNeighborColor(col, row);
                }
                //If cell has < 2 or > 3 neighbors, it dies and becomes empty
                if (!cells[row][col].isEmpty() && (countNeighbors(col, row) < 2 || countNeighbors(col, row) > 3 )){
                    cells[row][col] = "";
                }
            }
        }
        app.update();
    }

    public void RandomlyKillHalf(){
        int killCount = cellCount / 2;
        while (killCount > 0){
            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[row].length; col++) {
                    long randomNumber = Math.round(Math.random() * 2);
                    if (randomNumber <= 1){
                        switch (cells[row][col]){
                            case "Red":
                                redCount--;
                                break;
                            case "Green":
                                greenCount--;
                                break;
                            case "Blue":
                                blueCount--;
                                break;
                            default:
                                break;
                        }
                        cells[row][col] = "";
                        cellCount--;
                        killCount--;
                    }
                }
            }
        }
        this.app.update();

    }

    //region Converting To Labels
    public Label ParseStringToLabel(int row, int col) {
        if (cells[row][col] == null) return null;

        Label l = new Label("");
        l.setMinSize(20, 20);
        l.setBackground(Background.fill(StringToColor(cells[row][col])));
        return l;
    }

    private Color StringToColor(String color) {
        switch (color) {
            case "Red" -> {
                return Color.RED;
            }
            case "Blue" -> {
                return Color.BLUE;
            }
            case "Green" -> {
                return Color.GREEN;
            }
            default -> {
                return Color.DARKGRAY;
            }
        }
    }

    public Label[][] UpdateGrid(Label[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                grid[r][c] = ParseStringToLabel(r, c);
            }
        }
        return grid;
    }
    //endregion
}
