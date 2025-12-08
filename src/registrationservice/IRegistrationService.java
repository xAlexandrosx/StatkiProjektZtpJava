package registrationservice;

import java.util.List;

public interface IRegistrationService {

    List<String> readPlayers();

    void writePlayer(String name);

    boolean isLoggedIn(String playerName);

    void logIn();

    void logOut();

    void signIn();
}
