package battleship;

public class Battleship {
    public final int posX, posY;
    public final int length;
    public final boolean vertical;

    private int health;

    public Battleship(int x, int y, int len, boolean vert) {
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
    }
    public int getHp() {
        return health;
    }
    public boolean isSunken() {
        return health <= 0;
    }

    public boolean intersects(int cx, int cy) {
        if (vertical) {
            return cx == posX && cy >= posY && cy < posY + length;
        } else {
            return cy == posY && cx >= posX && cx < posX + length;
        }
    }

    public boolean collision(Battleship so) {
        int myX2 = this.posX + (this.vertical ? 1 : this.length);
        int myY2 = this.posY + (this.vertical ? this.length : 1);
        int theirX2 = so.posX + (so.vertical ? 1 : so.length);
        int theirY2 = so.posY + (so.vertical ? so.length : 1);

        boolean overlapX = this.posX < theirX2 && myX2 > so.posX;
        boolean overlapY = this.posY < theirY2 && myY2 > so.posY;

        return overlapX && overlapY;
    }

    public String toString() {
        return String.format("ShipObj[x:%d,y:%d,len:%d,vert:%s]", posX, posY, length, vertical ? "yes" : "no");
    }
}
