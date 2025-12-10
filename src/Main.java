import Game.Game;

void main() {
    Game g = new Game();

    int option;
    do {
        g.consoleMenu.display();
        option = g.consoleMenu.handleInput();
    } while (option != -1);
}
