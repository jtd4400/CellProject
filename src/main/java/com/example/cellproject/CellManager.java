package com.example.cellproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import java.util.Observer;

import java.util.Dictionary;

public class CellManager {
    String[][] cells;
    int gridWidth, gridHeight;
    int cellCount;
    int startingCellCount;
    int redCount, greenCount, blueCount;
    Color backgroundColor = Color.DIMGRAY;

    //region Getters
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


    public CellManager(int gridWidth, int gridHeight, int cellCount, HelloApplication app) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        cells = new String[gridHeight][gridWidth];
        startingCellCount = cellCount;
        this.app = app;
        RandomizeLayout(false);
    }

    public void RandomizeLayout(boolean reset){
        this.cellCount = startingCellCount;
        int cellCount = this.cellCount;
        if (reset){
            redCount = 0;
            greenCount = 0;
            blueCount = 0;
        }
        do {
            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[row].length; col++) {
                    if (cells[row][col] == null || reset){
                        cells[row][col] = "";
                    }
                    if (cellCount > 0){
                        long randomNumber = Math.round(Math.random() * 1000);
                        if (randomNumber <= 30 && cells[row][col].isEmpty()){
                            cells[row][col] = "Red";
                            cellCount--;
                        }
                        else if (randomNumber <= 60 && cells[row][col].isEmpty()){
                            cells[row][col] = "Green";
                            cellCount--;
                        }
                        else if (randomNumber <= 90 && cells[row][col].isEmpty()){
                            cells[row][col] = "Blue";
                            cellCount--;
                        }
                    }
                }
            }
            reset = false;
        } while (cellCount > 0);
        SetColorCounts();
    }

    public void ResetLayout() {
        RandomizeLayout(true);
        this.app.update();
    }

    private int countNeighbors(int col, int row) {
        int neighbors = 0;
        for(int i = -1; i < 2; i++){
            if (row + i >= 0 && row + i <= gridHeight - 1){
                for (int j = -1; j < 2; j++){
                    if (col + j >= 0 && col + j <= gridWidth - 1 && !cells[row + i][col + j].isEmpty() && !(i == 0 && j == 0)){
                        neighbors++;
                    }
                }
            }
        }
        return neighbors;
    }

    private String getNeighborColor(int col, int row) {
        for(int i = -1; i < 2; i++){
            if (row + i >= 0 && row + i <= gridHeight - 1){
                for (int j = -1; j < 2; j++){
                    if (col + j >= 0 && col + j <= gridWidth - 1 && !cells[row + i][col + j].isEmpty() && cells[row + i][col + j] != null){
                        return cells[row + i][col + j];
                    }
                }
            }

        }
        return "";
    }

    public void AdvanceGeneration() {
        String[][] newGrid = new String[20][20];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                newGrid[row][col] = cells[row][col];
                //If empty, make a new cell with the neighbor's color
                if (cells[row][col].isEmpty() && countNeighbors(col, row) == 3){
                    newGrid[row][col] = getNeighborColor(col, row);
                    cellCount++;
                }
                //If cell has < 2 or > 3 neighbors, it dies and becomes empty
                if (!cells[row][col].isEmpty() && (countNeighbors(col, row) < 2 || countNeighbors(col, row) > 3 )){
                    newGrid[row][col] = "";
                    cellCount--;
                }
            }
        }
        cells = newGrid;
        SetColorCounts();
        app.update();
    }

    public void RandomlyKillHalf(String color){
        if (cellCount < 2) {
            this.app.update();
            return;
        }
        int killCount = cellCount / 2;
        while (killCount > 0){
            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[row].length; col++) {
                    long randomNumber = Math.round(Math.random() * 2);
                    if (randomNumber <= 1 && !cells[row][col].isEmpty() && !cells[row][col].equals(color)){
                        cells[row][col] = "";
                        cellCount--;
                        killCount--;
                    }
                    if (killCount == 0){
                        SetColorCounts();
                        this.app.update();
                        return;
                    }
                }
            }
        }
        SetColorCounts();
        this.app.update();
    }

    public void SetBackgroundColor(Color color){
        backgroundColor = color;
        this.app.update();
    }

    public void SetColorCounts() {
        redCount = 0;
        greenCount = 0;
        blueCount = 0;
        for (String[] cell : cells) {
            for (String s : cell) {
                switch (s) {
                    case "Red":
                        redCount++;
                        break;
                    case "Green":
                        greenCount++;
                        break;
                    case "Blue":
                        blueCount++;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //region Converting To Labels
    public Label ParseStringToLabel(int row, int col) {
        if (cells[row][col] == null) return null;

        Label l = new Label("");
        l.setMinSize(20, 20);
        l.setBackground(Background.fill(StringToColor(cells[row][col])));
        return l;
    }

    public Color StringToColor(String color) {
        switch (color) {
            case "Red" -> {
                return Color.RED;
            }
            case "Blue" -> {
                return Color.BLUE;
            }
            case "Green" -> {
                return Color.LIME;
            }
            case "None" -> {
                return Color.DIMGRAY;
            }
            default -> {
                return backgroundColor;
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
