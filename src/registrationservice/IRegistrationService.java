package registrationservice;

import java.util.List;

public interface IRegistrationService {

    List<PlayerProfile> loadPlayers();

    void writePlayer(PlayerProfile playerProfile);

    boolean isLoggedIn(String playerName);

    void logIn();

    void logOut();

    void signIn();
}
