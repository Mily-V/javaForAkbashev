package mechanics;

/**
 * Created by Vadim on 30.11.14.
 */

public interface Cell {
    int getX();
    void setX(int x);

    int getY();
    void setY(int y);

    void setXY(int x, int y);

    boolean isFired();
    boolean isDeck();
    boolean isAvailable();
    void setFire(boolean fired);
    void setDeck(boolean ship);
    void setAccess(boolean available);
    void setShip(Ship ship);

    Codes isKilled();
    void clearCell();
    void killShip();
}