package registrationservice;

import java.util.List;

public class RegistrationServiceAccessProxy implements IRegistrationService {

    private final IRegistrationService realService;

    public RegistrationServiceAccessProxy(IRegistrationService realService) {
        this.realService = realService;
    }

    // ai - uzytkownik nazywa sie computer
    private boolean isAiProfile(PlayerProfile profile) {
        if (profile == null) return false;
        String name = profile.getName();
        return name.startsWith("Computer");
    }

    // gośc pasożyt - profil nazywa się Guest
    private boolean isGuestProfile(PlayerProfile profile) {
        if (profile == null) return false;
        String name = profile.getName();
        return name.startsWith("Guest");
    }

    // Tylko zarejestrowani ludzie muszą być zalogowani
    private void ensureHumanLoggedIn(PlayerProfile profile) {

        // kąkuter nie musi  się logować
        if (isAiProfile(profile)) {
            return;
        }

        // goście też nie
        if (isGuestProfile(profile)) {
            return;
        }

        //tylko user musi
        if (!realService.isLoggedIn(profile.getName())) {
            throw new SecurityException("Brak dostępu — musisz być zalogowany!");
        }
    }

    @Override
    public List<PlayerProfile> loadPlayers() {
        // załadowanie graczy jest zawsze dostępne
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
