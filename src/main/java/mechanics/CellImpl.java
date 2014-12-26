package mechanics;

/**
 * Created by Vadim on 08.11.14.
 */
public class CellImpl implements Cell {
    private int x;
    private int y;
    private boolean fired;
    private boolean deck;
    private boolean available; // Доступ для размещения кораблей
    private Ship ship;

    public CellImpl(int x, int y) {
        this.x = x;
        this.y = y;
        fired = false;
        deck = false;
        available = true;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean isFired() {
        return fired;
    }
    public boolean isDeck() {
        return deck;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setFire(boolean fired) {
        this.fired = fired;
    }
    public void setDeck(boolean ship) {
        this.deck = ship;
    }
    public void setAccess(boolean available) {
        this.available = available;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Codes isKilled() {
        if (fired == true) {
            return Codes.IS_FIRED;
        }

        if (deck == true) {
            if (ship.isKilled()) {
                return Codes.KILLED;
            }
            else return Codes.DECK;
        }

        return Codes.EMPTY;
    }

    public void clearCell() {
        fired = false;
        deck = false;
        available = true;
        ship = null;
    }
    public void killShip() {
        if (ship != null) ship.kill();
    }

}
