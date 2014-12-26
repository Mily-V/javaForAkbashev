package sockets;

import base.UserGame;

/**
 * Created by Dmitry on 025 25.10.14.
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    GameWebSocket getWebSocket(String login);

    void notifyMyNewScore(UserGame user);

    void notifyEnemyNewScore(UserGame user);

    void notifyStartGame(String user1, String user2);

    void notifyGameOver(UserGame user, boolean win);
}
