package base;

import dataSets.UserDataSet;

/**
 * Created by serg on 21.11.14.
 */
public interface DatabaseService {
    UserDataSet addUser(String login, String password, String email);
    UserDataSet getUser(String login);
    long countOfUsers();
    boolean checkUserRegistered(String login);
    void deleteAllUsers();
//    boolean checkUserPassword (String login, String password);
}
