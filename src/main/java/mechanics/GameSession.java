package mechanics;

import base.UserGame;

import java.util.ArrayList;

/**
 * Created by Vadim on 23.11.14.
 */

public interface GameSession {
    UserGame getFirst();
    UserGame getSecond();

    UserGame getUserGameByEmail(String emailUsr);

    Codes fire(UserGame user, int x, int y);
    Field getField(String user);
    Codes setShips(String user, ArrayList<Ship> ships);
}