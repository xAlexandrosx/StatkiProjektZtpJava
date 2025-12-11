package registrationservice;

import java.util.List;

public class RegistrationServiceAccessProxy implements IRegistrationService {

    private final IRegistrationService realService;

    public RegistrationServiceAccessProxy(IRegistrationService realService) {
        this.realService = realService;
    }

    //jezeli profi jest Ai, jego nazwa zaczyna się od komputer (przynajmniej póki co)
    private boolean isAiProfile(PlayerProfile profile) {
        if (profile == null) return false;
        String name = profile.getName();
        return name.startsWith("Computer");
    }
    private boolean isGuestProfile(PlayerProfile profile) {
        if (profile == null) return false;
        String name = profile.getName();
        return name.startsWith("Guest");
    }
    //tylko człowieki musza być  zalogowane
    private void ensureHumanLoggedIn(PlayerProfile profile) {

        // ai nie musi być zalogowane
        if (isAiProfile(profile)) {
            return;
        }

        // guest nie musi być zalogowany
        if (isGuestProfile(profile)) {
            return;
        }

        // człowieki z profilem musza bycc zalogowane
        if (!realService.isLoggedIn(profile.getName())) {
            throw new SecurityException("Brak dostępu — musisz być zalogowany!");
        }
    }


    // oddeleogwanie  metod

    @Override
    public List<PlayerProfile> loadPlayers() {
        // czytanie grczy nie wymaga logowania
        return realService.loadPlayers();
    }

    @Override
    public void writePlayer(PlayerProfile playerProfile) {
        ensureHumanLoggedIn(playerProfile);
        realService.writePlayer(playerProfile);
    }

    @Override
    public void updatePlayer(PlayerProfile playerProfile) {
        ensureHumanLoggedIn(playerProfile);
        realService.updatePlayer(playerProfile);
    }

    @Override
    public boolean isLoggedIn(String playerName) {
        return realService.isLoggedIn(playerName);
    }

    @Override
    public void logIn() {
        realService.logIn();
    }

    @Override
    public void logOut() {
        realService.logOut();
    }

    @Override
    public void signIn() {
        realService.signIn();
    }
}
