package menu;

import registrationservice.PlayerProfile;

public interface IMenu { // Interfejs klasy Menu

    void display();

    int handleInput();

    int userChooseAiDifficulty();

    void displayRanking();
    void displayHistory();
}
