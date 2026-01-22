package players;

import ServiceLocator.ServiceLocator;
import observer.notifications.TurnTakenNotification;

import java.util.ArrayList;
import java.util.List;

public abstract class AiPlayerBase extends Player {

    // stan “hunt & target”
    private boolean hitLastTurn = false;
    private int lastHitX = -1;
    private int lastHitY = -1;

    public AiPlayerBase(String name) {
        super(name);
    }

    @Override
    public void takeTurn() {
        int size = ServiceLocator.getInstance().getGlobalVariables().getBoardSize();

        // 1) TARGET mode: jeśli ostatnio był hit, spróbuj sąsiadów (4-kierunki)
        int[] target = pickTargetShot(size);
        int x = target[0];
        int y = target[1];

        // wykonaj strzał i zaktualizuj stan
        boolean hit = shootAndReport(x, y);

        if (hit) {
            hitLastTurn = true;
            lastHitX = x;
            lastHitY = y;
        } else {
            hitLastTurn = false;
        }

        // opóźnienie zależne od poziomu
        sleepDelay();
    }

    /**
     * Wybiera strzał:
     * - jeśli był hit w poprzedniej turze -> spróbuj sąsiadów
     * - inaczej -> tryb hunt (szukanie)
     */
    private int[] pickTargetShot(int size) {

        if (hitLastTurn) {
            List<int[]> candidates = new ArrayList<>(4);

            // 4 kierunki: góra/dół/lewo/prawo
            addIfShootable(candidates, lastHitX - 1, lastHitY, size);
            addIfShootable(candidates, lastHitX + 1, lastHitY, size);
            addIfShootable(candidates, lastHitX, lastHitY - 1, size);
            addIfShootable(candidates, lastHitX, lastHitY + 1, size);

            if (!candidates.isEmpty()) {
                // losuj z kandydatów (nie będzie “mielenia”, bo max 4 pola)
                int idx = ServiceLocator.getInstance().getRandom().nextInt(candidates.size());
                return candidates.get(idx);
            }

            // jeśli nie ma wolnych sąsiadów, spadamy do hunt mode
            hitLastTurn = false;
        }

        // 2) HUNT mode: wybierz pole z szachownicy (szybsze znajdowanie statków)
        // (Dla statków długości >= 2 to zmniejsza przestrzeń poszukiwań ~ o połowę)
        int[] hunt = pickHuntShotCheckerboard(size);
        if (hunt != null) return hunt;

        // 3) Awaryjnie: znajdź pierwsze wolne pole skanem
        return pickFirstShootable(size);
    }

    /**
     * HUNT: strzały w "szachownicę": (x+y)%2==0
     * Z limiterem prób, żeby nie mielić w końcówce.
     */
    private int[] pickHuntShotCheckerboard(int size) {
        int maxAttempts = size * size; // bezpiecznie, ale i tak szybko wyjdzie

        for (int attempts = 0; attempts < maxAttempts; attempts++) {
            int x = ServiceLocator.getInstance().getRandom().nextInt(size);
            int y = ServiceLocator.getInstance().getRandom().nextInt(size);

            // szachownica
            if (((x + y) & 1) != 0) continue;

            if (isShootable(x, y, size)) {
                return new int[]{x, y};
            }
        }
        return null;
    }

    private int[] pickFirstShootable(int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (isShootable(x, y, size)) {
                    return new int[]{x, y};
                }
            }
        }

        // teoretycznie nie powinno się zdarzyć, bo mecz powinien się skończyć wcześniej
        return new int[]{0, 0};
    }

    private void addIfShootable(List<int[]> list, int x, int y, int size) {
        if (isShootable(x, y, size)) {
            list.add(new int[]{x, y});
        }
    }

    /**
     * U Ciebie: 0 lub 1 oznacza pole, do którego można strzelić.
     * Reszta oznacza: już strzelone / zablokowane.
     */
    private boolean isShootable(int x, int y, int size) {
        if (x < 0 || y < 0 || x >= size || y >= size) return false;

        int tile = enemyBoard.getTile(x, y);
        return tile == 0 || tile == 1;
    }

    /**
     * Wykonuje strzał i zwraca czy to był hit.
     * Zakładam (jak w HumanPlayer), że registerShot zwraca coś, co da się rozpoznać jako hit.
     * Jeśli registerShot zwraca boolean/int – dopasuj 2 linijki w środku.
     */
    private boolean shootAndReport(int x, int y) {
        System.out.println(playerProfile.getName() + " shoots at " + x + ", " + y);

        // W zależności od Twojej implementacji:
        // - jeśli registerShot zwraca boolean -> hit = enemyBoard.registerShot(x,y);
        // - jeśli zwraca int/status -> zmapuj.
        Object result = enemyBoard.registerShot(x, y);

        // Rejestrujemy strzał
        ServiceLocator.getInstance().getNotificationManager().publish(new TurnTakenNotification(this, x, y, (boolean) result));

        System.out.println(enemyBoard.displayBoard(false));

        // Spróbujmy sensownie zinterpretować wynik bez zgadywania za mocno:
        // Jeśli registerShot zwraca boolean -> obsłuż.
        if (result instanceof Boolean) {
            return (Boolean) result;
        }

        // Jeśli zwraca np. Integer gdzie 1=hit 0=miss:
        if (result instanceof Integer) {
            return ((Integer) result) != 0;
        }

        // Jeśli nic sensownego nie zwraca (void / null),
        // to trudno wykryć hit -> wtedy target mode będzie mniej skuteczny.
        // W takim przypadku najlepiej, żeby registerShot zwracało boolean/int.
        return false;
    }

    protected void sleepDelay() {
        try {
            Thread.sleep(ServiceLocator.getInstance().getGlobalVariables().getAiDelay());
        } catch (InterruptedException ignored) {
        }
    }
}
