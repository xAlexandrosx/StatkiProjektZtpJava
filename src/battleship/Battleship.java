package battleship;

public class Battleship { // Klasa statku
    public final int posX, posY; // Pozycja statku na planszy
    public final int length; // Długość (rozmiar statku)
    public final boolean vertical; // Orientacja statku (pozioma/pionowa)

    private int health; // Ile żyć (segmentów statku) pozostało do jego kompletnego zatopienia

    public Battleship(int x, int y, int len, boolean vert) { // Konstruktor
        this.posX = x;
        this.posY = y;
        this.length = len;
        if (len == 1) {
            vert = false;
        }
        this.vertical = vert;
        this.health = len;
    }

    public void hit() {
        if (health > 0) health--;
    } // Metoda dekrementująca liczbę żyć statku

    public int getHp() {
        return health;
    } // Metoda zwracająca liczbę pozostałych żyć statku

    public boolean isSunk() {
        return health <= 0;
    } // Metoda zwracająca informację, czy statek jest zatopiony

    public boolean intersects(int cx, int cy) { // Metoda sprawdzająca, czy dla danych współrzędnych jest pole statku
        if (vertical) {
            return cx == posX && cy >= posY && cy < posY + length;
        } else {
            return cy == posY && cx >= posX && cx < posX + length;
        }
    }

    public boolean collision(Battleship so) { // Metoda wykrywająca kolizcję statków
        int myX2 = this.posX + (this.vertical ? 1 : this.length);
        int myY2 = this.posY + (this.vertical ? this.length : 1);
        int theirX2 = so.posX + (so.vertical ? 1 : so.length);
        int theirY2 = so.posY + (so.vertical ? so.length : 1);

        boolean overlapX = this.posX < theirX2 && myX2 > so.posX;
        boolean overlapY = this.posY < theirY2 && myY2 > so.posY;

        return overlapX && overlapY;
    }

    public String toString() { // Metoda zwracająca informacje o statku w formie tekstowej
        return String.format("ShipObj[x:%d,y:%d,len:%d,vert:%s]", posX, posY, length, vertical ? "yes" : "no");
    }
}
