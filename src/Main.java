import Game.Game;

public class Main {
    public static void main(String[] args) {

        Game g = new Game();

        int option;
        do {
            g.consoleMenu.display();
            option = g.consoleMenu.handleInput();
        } while (option != -1);
    }
}

