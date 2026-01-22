package battleship;

import java.util.ArrayList;
import java.util.List;

public class BattleshipAdapter implements IBattleship { // Adapter klasy Battleship
    private final Battleship s; // Zmienna statku
    @Override
    public List<int[]> getTiles() { // Metoda zwracająca pary koordynatow zajmowane przez statek
        List<int[]> ret = new ArrayList<>();
        int x = s.posX, y = s.posY;
        if (s.vertical) {
            for (; y < s.posY + s.length; y++) {
                ret.add(new int[] {x, y});
            }
        } else {
            for (; x < s.posX + s.length; x++) {
                ret.add(new int[] {x, y});
            }
        }
        return ret;
    }

    public BattleshipAdapter(Battleship b) {
        this.s = b;
    } // Konstruktor
}
