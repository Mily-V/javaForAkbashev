package mechanics;

import java.util.ArrayList;

/**
 * Created by Vadim on 08.11.14.
 */
public class ShipImpl implements Ship {
    private int x0;
    private int y0;
    private int xn;
    private int yn;
    private int countDeck;
    private boolean killed;
    private ArrayList<Cell> cells = new ArrayList<>();

    public ShipImpl(int x0, int y0, int xn, int yn) {
        // Ordering coordinates
        if (x0 >= xn && y0 >= yn) {
            // Swap points
            int bufX = x0;
            int bufY = y0;
            x0 = xn;
            y0 = yn;
            xn = bufX;
            yn = bufY;
        }

        this.x0 = x0;
        this.y0 = y0;
        this.xn = xn;
        this.yn = yn;
        calculateCountDeck();
        this.killed = false;
    }

    private int calculateCountDeck() {
        if (x0 < 0 || y0 < 0) {
            countDeck = -1;
            return countDeck;
        }

        // The vertical orientation
        if (x0 == xn )
            countDeck = yn - y0 + 1;

        // The horizontal orientation
        else if (y0 == yn)
            countDeck = xn - x0 + 1;

        else
            countDeck = -1;

        return countDeck;
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public int getXn() {
        return xn;
    }

    public int getYn() {
        return yn;
    }

    public int getCountDeck() {
        return countDeck;
    }


    public boolean isKilled() {
        return killed;
    }

    public void kill() {
        killed = true;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

}