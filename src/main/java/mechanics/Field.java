package mechanics;

/**
 * Created by Vadim on 30.11.14.
 */
public interface Field {
    int getNumberNotFiredDecks();
    Cell getCell(int x, int y);
    boolean setShip(Ship ship);
    Codes fire(int x, int y);
    boolean coordinatesInField(int x, int y);
    void clearField();
}
