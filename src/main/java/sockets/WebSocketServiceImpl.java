package sockets;


import base.UserGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry on 025 25.10.14.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public GameWebSocket getWebSocket(String login) {
        if ( userSockets.containsKey(login) )
            return userSockets.get(login);
        else
            return null;
    }

    public void notifyMyNewScore(UserGame user) {
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    public void notifyEnemyNewScore(UserGame user) {
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    public void notifyStartGame(String user1, String user2) {
        GameWebSocket gameWebSocket1 = userSockets.get(user1);
        gameWebSocket1.startGame(user2);
        GameWebSocket gameWebSocket2 = userSockets.get(user2);
        gameWebSocket2.startGame(user1);
    }

    @Override
    public void notifyGameOver(UserGame user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }
}
