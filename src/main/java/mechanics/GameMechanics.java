package mechanics;


import base.UserGame;

import java.util.ArrayList;

/**
 * Created by Vadim on 08.11.14.
 */

public interface GameMechanics {

    boolean addUser(String emailUser);

    Codes fire(String user, int x, int y);

    UserGame getUserGame(String emailUser);

    Codes setShips(String emailUser, ArrayList<Ship> ships);

    void closeGame(String emailUser);

    int getCountGameSessions();

    int getCountUsersInQueue();
    //public void run();
}
