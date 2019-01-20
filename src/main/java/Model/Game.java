package Model;

import Helper.ResponseParser;

public class Game {

    private Field[][] board = new Field[5][5];

    public Game() {
        for (int i = 0 ; i < 5 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                this.board[i][j] = new Field();
            }
        }
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }

    public void updateBoard(String command) throws Exception {
        int[] values = ResponseParser.parseBoardInfo(command);
        if(values.length == 4) {
            this.board[values[0] - 1][values[1] - 1].setOwner(values[2]);
            this.board[values[0] - 1][values[1] - 1].setCubeCount(values[3]);
        } else
            throw new Exception("Zle dane PLANSZA");

    }

    public void printBoard () {
        for (int i = 0; i < 5 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                System.out.println("Owner: " + this.board[i][j].getOwner() + " CUBES: " + this.board[i][j].getCubeCount());
            }
            System.out.println();
        }
    }

    public Field getField(int x, int y) {
        return this.board[x][y];
    }
}
