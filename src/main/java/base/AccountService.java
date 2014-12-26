package base;

/**
 * Created by Dmitry on 019 19.10.14.
 */
public interface AccountService {
    boolean addUser(String login, String password, String email);
    boolean addSession(String id, String login);
    long getCountOfUsers();
    int getCountOfSessions();
    void deleteSession(String id);
    boolean haveSession(String id);
    boolean haveUser(String login);
    boolean isPasswordCorrect(String login, String password);
    String getUserEmailBySessionId(String id);
    String getUserLoginBySessionId(String id);
}
