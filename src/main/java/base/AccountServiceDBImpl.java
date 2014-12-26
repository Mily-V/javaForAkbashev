package base;

import dataSets.UserDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by serg on 21.11.14.
 */
public class AccountServiceDBImpl implements AccountService {
    private DatabaseService databaseService;
    private Map<String, String> sessionIdByLogin = new HashMap<String, String>();
    private Map<String, UserDataSet> sessions = new HashMap<String, UserDataSet>();

    public AccountServiceDBImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    private boolean checkLogin(String login) {
        return sessionIdByLogin.containsKey(login);
    }

    private void removeSession(String login) {
        sessions.remove(sessionIdByLogin.get(login));
        sessionIdByLogin.remove(login);
    }

    public boolean addUser(String login, String password, String email) {
        if ( login.isEmpty() || password.isEmpty() || email.isEmpty()
                || haveUser(login)) {
            return false;
        } else {
            databaseService.addUser(login, password, email);
            return true;
        }
    }

    public boolean addSession(String id, String login) {
        if ( !haveUser(login) )
            return false;
        UserDataSet user;
        if ( checkLogin(login) ) {
            user = sessions.get(sessionIdByLogin.get(login));
            removeSession(login);
        } else {
            if ( sessions.containsKey(id) ) {
                return false; // сделать enum
            }
            user = databaseService.getUser(login);
        }
        sessions.put(id, user);
        sessionIdByLogin.put(login, id);
        return true;
    }

    public long getCountOfUsers() {
        return (int)databaseService.countOfUsers();
    }

    public int getCountOfSessions() {
        return sessions.size();
    }

    public void deleteSession(String id) {
        sessionIdByLogin.remove(sessions.get(id).getLogin());
        sessions.remove(id);
    }

    public boolean haveSession(String id) {
        return  sessions.containsKey(id);
    }

    public boolean haveUser(String login) {
        return  databaseService.checkUserRegistered(login);
    }

    public boolean isPasswordCorrect(String login, String password) {
        UserDataSet user = databaseService.getUser(login);
        return password.equals(user.getPassword());
    }

    public String getUserEmailBySessionId(String id) {
        if (sessions.containsKey(id)) {
            return sessions.get(id).getEmail();
        } else {
            return null;
        }
    }

    public String getUserLoginBySessionId(String id) {
        if (sessions.containsKey(id)) {
            return sessions.get(id).getLogin();
        } else  {
            return null;
        }
    }
}
