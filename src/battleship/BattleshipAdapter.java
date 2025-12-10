package battleship;

import java.util.ArrayList;
import java.util.List;

/* ADAPTER dla MatchHistoryService */
public class BattleshipAdapter implements IBattleship {
    private final Battleship s;
    public BattleshipAdapter(Battleship b) {
        this.s = b;
    }

    /* zwraca pary koordynatow zajmowane przez statek */
    @Override
    public List<int[]> getTiles() {
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
}
