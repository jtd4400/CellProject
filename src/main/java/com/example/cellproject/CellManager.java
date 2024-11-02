package com.example.cellproject;

public class CellManager {
    String[][] cells = new String[20][20];
    int cellCount;

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
}
