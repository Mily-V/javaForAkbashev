package mechanics;

/**
 * Created by Vadim on 30.11.14.
 */
public interface Ship {
    int getX0();
    int getY0();
    int getXn();
    int getYn();
    int getCountDeck();
    boolean isKilled();
    void kill();
    void addCell(Cell cell);
}
