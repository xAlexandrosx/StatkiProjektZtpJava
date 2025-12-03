
void main() {

    Menu menu = new Menu();

    int option;
    do {
        menu.display();
        option = menu.handleInput();
    } while (option != -1);
}
