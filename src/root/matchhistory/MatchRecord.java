package root.matchhistory;

import java.util.ArrayList;
import java.util.List;

public class MatchRecord {
    public String gameID;
    public String date;
    public String time;
    public String player1;
    public String player2;
    public String winner;
    public int boardSize;
    public List<List<int[]>> ships1;
    public List<List<int[]>> ships2;
    public List<TurnRecord> turns = new ArrayList<>();
}
