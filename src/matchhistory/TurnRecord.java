package matchhistory;

public class TurnRecord { // Klasa odpowiedzialna za zapisywanie tury gracza
    public String player;
    public int x;
    public int y;

    public TurnRecord(String player, int x, int y) { // Konstruktor
        this.player = player;
        this.x = x;
        this.y = y;
    }
}
