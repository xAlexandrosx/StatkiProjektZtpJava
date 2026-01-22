package registrationservice;

import java.util.List;

public interface IRegistrationService { // Interfejs klasy RegistrationService

    List<PlayerProfile> loadPlayers();

    void writePlayer(PlayerProfile playerProfile);

    void updatePlayer(PlayerProfile playerProfile);

    boolean isLoggedIn(String playerName);

    void logIn();

    void logOut();

    void signIn();
}
