package base;

import mechanics.*;

import java.util.ArrayList;

/**
 * Created by Vadim on 28.11.14
 */

public class UserGameImpl implements UserGame{
    private final String myName;
    private String enemyName;
    private Field myField;
    private Field enemyField;
    private ArrayList<Ship> myShips;
    private ArrayList<Ship> enemyShips;


    public UserGameImpl(String myName) {
        this.myName = myName;
        this.enemyName = null;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public Field getMyField() {
        return myField;
    }

    public void setMyField(Field myField) {
        this.myField = myField;
    }

    public void setEnemyField(Field enemyField) {
        this.enemyField = enemyField;
    }

    public Field getEnemyField() {
        return enemyField;
    }

    public Codes fireMyField(int x, int y) {
        return myField.fire(x, y);
    }

    public Codes fireEnemyField(int x, int y) {
        return enemyField.fire(x, y);
    }

    public void setMyShips(ArrayList<Ship> ships) {
        myShips = ships;
    }

    public void setEnemyShips(ArrayList<Ship> ships) {
        enemyShips = ships;
    }

}
