package resources;

import java.io.Serializable;

/**
 * Created by Dmitry on 010 10.11.14.
 */
public class GameResources implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private int fieldSize;
    private int countOfShips;

    public GameResources() {
        fieldSize = 10;
        countOfShips = 10;
    }

    public GameResources(int fieldSize, int countOfShips) {
        this.fieldSize = fieldSize;
        this.countOfShips = countOfShips;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public int getCountOfShips() {
        return countOfShips;
    }

    public void setCountOfShips(int fieldSize) {
        this.countOfShips = fieldSize;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public String toString() {
        return "GameResources: " + fieldSize + ", " + countOfShips;
    }
}