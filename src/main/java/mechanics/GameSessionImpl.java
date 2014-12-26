package mechanics;

import base.UserGame;

import java.util.ArrayList;

/**
 * Created by Vadim on 09.11.14.
 */

public class GameSessionImpl implements GameSession {
    private static final int FIELD_ROW_SIZE = 10;
    private static final int FIELD_COL_SIZE = 10;

    private UserGame user1;
    private UserGame user2;
    private Field fieldUser1;
    private Field fieldUser2;

    public UserGame getFirst() {
        return user1;
    }

    public UserGame getSecond() {
        return user2;
    }

    public GameSessionImpl(UserGame user1, UserGame user2) {
        this.user1 = user1;
        this.user2 = user2;

        // Это когда-куда?
        this.user1.setMyField(fieldUser1);
        this.user1.setEnemyField(fieldUser2);
        this.user2.setMyField(fieldUser2);
        this.user2.setEnemyField(fieldUser1);


        // HARD CODE
        /*Ship fourDecker1 = new Ship(0, 0, 3, 0);

        Ship threeDecker1 = new Ship(5, 0, 7, 0);
        Ship threeDecker2 = new Ship(0, 2, 2, 2);

        Ship twoDecker1 = new Ship(4, 2, 5, 2);
        Ship twoDecker2 = new Ship(7, 2, 8, 2);
        Ship twoDecker3 = new Ship(0, 4, 1, 4);

        Ship oneDecker1 = new Ship(3, 4, 3, 4);
        Ship oneDecker2 = new Ship(5, 4, 5, 4);
        Ship oneDecker3 = new Ship(7, 4, 7, 4);
        Ship oneDecker4 = new Ship(9, 4, 9, 4);

        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(fourDecker1);
        ships.add(threeDecker1);
        ships.add(threeDecker2);
        ships.add(twoDecker1);
        ships.add(twoDecker2);
        ships.add(twoDecker3);
        ships.add(oneDecker1);
        ships.add(oneDecker2);
        ships.add(oneDecker3);
        ships.add(oneDecker4);

        this.setShips(user1.getMyName(), ships);
        this.setShips(user2.getMyName(), ships);*/
    }

    public Codes setShips(String emailUser, ArrayList<Ship> ships) {
        UserGame userGame = getUserGameByEmail(emailUser);
        if (userGame == null)
            return Codes.USER_NOT_FOUND;

        if (!checkCorrectShips(ships))
            return Codes.ERROR;

        // Расстановка кораблей
        FieldImpl field = new FieldImpl(FIELD_ROW_SIZE, FIELD_COL_SIZE);

        for (Ship ship : ships) {
            if (!field.setShip(ship)) {
                field.clearField();
                return Codes.ERROR;
            }
        }

        if (userGame == user1) {
            fieldUser1 = field;
            user1.setMyField(fieldUser1);
            user1.setMyShips(ships);
            user2.setEnemyField(fieldUser1);
            user2.setEnemyShips(ships);
        }
        else {
            fieldUser2 = field;
            user1.setEnemyField(fieldUser2);
            user1.setEnemyShips(ships);
            user2.setMyField(fieldUser2);
            user2.setMyShips(ships);
        }

        return Codes.OK;
    }

    public Codes fire(UserGame userGame, int x, int y) {
        Field field = userGame.getEnemyField();
        if (field == null)
            return Codes.FIELD_IS_EMPTY;
        return field.fire(x, y);
    }

    public Field getField(String emailUser) {
        if (user1.getMyName().equals(emailUser))
            return fieldUser1;
        if (user2.getMyName().equals(emailUser))
            return fieldUser2;
        return null;
    }

    public UserGame getUserGameByEmail(String emailUsr) {
        UserGame userGame = null;
        if (user1.getMyName().equals(emailUsr)) {
            userGame = user1;
        }
        else if (user2.getMyName().equals(emailUsr)) {
            userGame = user2;
        }
        return  userGame;
    }

    // The Best method
    private boolean checkCorrectShips(ArrayList<Ship> ships) {
        return true;
    }

    /*private UserGame getOpponentGame(String user) {
        //if (!userInSession(user))
        //    return null;
        //if (user.equals(user1))
        //    return user2;
        //return user1;
        return null;
    }*/

    /*
    private boolean userInSession(String user) {
        return  (user.equals(user1) || (user.equals(user2)));
    }

    public boolean isGameOver() {
        if (getNumberNotFiredDecks(this.user1) == 0) {
            return true;
        }
        if (getNumberNotFiredDecks(this.user2) == 0) {
            return true;
        }
        return false;
    }

    public boolean isWinner(String user) {
        if (getNumberNotFiredDecks(getIdOpponent(user)) == 0)
            return true;
        return false;
    }


    /*private int getNumberNotFiredDecks(String user) {
        if (!userInSession(user))
            return -1;
        Field field;
        if (user.equals(user1))
            field = fieldUser1;
        else
            field = fieldUser2;

        return field.getNumberNotFiredDecks();
    }*/
}
