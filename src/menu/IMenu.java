package menu;

import registrationservice.PlayerProfile;

public interface IMenu {

    void display();

    int handleInput();

    int userChooseAiDifficulty();

    void displayRanking();
    void displayHistory();
}
