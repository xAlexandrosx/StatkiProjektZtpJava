package command;

import board.Board;

public class ShootCommand implements ICommand { // Klasa odpowiadająca za obsługę strzału
    Board board; // Plansza
    int x; // Współrzędna X planszy
    int y; // Współrzędna Y planszy
    int previousState; // Zmienna do przechowywania pop
    boolean result; // Wynik

    public ShootCommand(Board board, int x, int y){ // Konstruktor
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public boolean execute() { // Metoda wykonująca strzał
        previousState = board.getTile(x, y);
        result = board.registerShot(x, y);
        return result;
    }

    public void undo() {
        board.setTile(x, y, previousState);
    } // Metoda cofająca strzał
}
