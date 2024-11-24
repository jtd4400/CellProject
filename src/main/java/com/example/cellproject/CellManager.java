package com.example.cellproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

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
    //endregion

    public CellManager(int cellCount) {
        this.cellCount = cellCount;
        RandomizeLayout();
    }

    public void RandomizeLayout(){
        int cellCount = this.cellCount;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                long randomNumber = Math.round(Math.random() * 1000);
                cellCount--;
                if (cellCount > 0 && randomNumber <= 30){
                    cells[row][col] = "Red";
                    redCount++;
                }
                else if (cellCount > 0 && randomNumber <= 60){
                    cells[row][col] = "Green";
                    greenCount++;
                }
                else if (cellCount > 0 && randomNumber <= 90){
                    cells[row][col] = "Blue";
                    blueCount++;
                }
                else {
                    cells[row][col] = "";
                    if (cellCount < this.cellCount) cellCount++;
                }
            }
        }
    }

    private int countNeighbors(int col, int row) {
        int neighbors = 0;
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (!cells[col + i][row + j].isEmpty()){
                    neighbors++;
                }
            }
        }
        return neighbors;
    }

    private String getNeighborColor(int col, int row) {
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (!cells[col + i][row + j].isEmpty()){
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
    //endregion
}
