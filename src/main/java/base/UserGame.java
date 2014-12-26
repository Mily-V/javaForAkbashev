package base;

import mechanics.*;

import java.util.ArrayList;

/**
 * Created by Dmitry on 025 25.10.14.
 * Updated by Vadim
 */
public interface UserGame {
    String getMyName();
    String getEnemyName();
    Field getMyField();
    Field getEnemyField();
    Codes fireMyField(int x, int y);
    Codes fireEnemyField(int x, int y);
    void setMyField(Field myField);
    void setEnemyField(Field enemyField);
    void setEnemyName(String enemyName);
    void setMyShips(ArrayList<Ship> ships);
    void setEnemyShips(ArrayList<Ship> ships);
}
