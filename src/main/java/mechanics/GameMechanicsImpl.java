package mechanics;

import base.UserGame;
import base.UserGameImpl;
import sockets.WebSocketService;

import java.util.*;

/**
 * Created by Vadim on 09.11.14.
 */

public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;
    private WebSocketService webSocketService;

    private Queue<String> queueUsers = new LinkedList<>();

    private Map<UserGame, GameSession> gameSessionsMap = new HashMap<>();
    private Set<GameSession> allGameSessions = new HashSet<>();
    private Map<String, UserGame> userGameMap = new HashMap<>();

    private String waiter;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /*public void run() {
        System.out.append("Game Mechanics Run");
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }*/

    public boolean addUser(String user) {
        if (waiter != null) {
            startGame(user);
            waiter = null;
        } else {
            waiter = user;
        }

        return true;
    }

    public Codes fire(String myEmail, int x, int y) {
        UserGame userGame = userGameMap.get(myEmail);
        if (userGame == null)
            return Codes.USER_NOT_FOUND;
        GameSession gameSession = gameSessionsMap.get(userGame);
        return gameSession.fire(userGame, x, y);
    }

    public Codes setShips(String emailUser, ArrayList<Ship> ships) {
        UserGame userGame = userGameMap.get(emailUser);
        // Вот тут можно урезать
        GameSession gameSession = gameSessionsMap.get(userGame);
        return gameSession.setShips(emailUser, ships);
    }

    private void startGame(String user1) {
        String user2 = waiter;

        UserGame userGame1 = new UserGameImpl(user1);
        userGame1.setEnemyName(user2);
        UserGame userGame2 = new UserGameImpl(user2);
        userGame2.setEnemyName(user1);

        GameSession gameSession = createGameSession(userGame1, userGame2);

        //Вот тут должна начинаться игра
        webSocketService.notifyStartGame(user1, user2);
    }

    private GameSession createGameSession(UserGame user1, UserGame user2) {
        GameSession gameSession = new GameSessionImpl(user1, user2);
        gameSessionsMap.put(user1, gameSession);
        gameSessionsMap.put(user2, gameSession);
        allGameSessions.add(gameSession);
        userGameMap.put(user1.getMyName(), user1);
        userGameMap.put(user2.getMyName(), user2);
        return gameSession;
    }

    private void closeGameSession(UserGame user1, UserGame user2) {
        GameSession gameSession = gameSessionsMap.remove(user1);
        gameSessionsMap.remove(user2);
        allGameSessions.remove(gameSession);
    }

    public UserGame getUserGame(String emailUser) {
        return userGameMap.get(emailUser);
    }

    public GameSession getGameSession(String user) {
        return gameSessionsMap.get(user);
    }

    public void closeGame(String emailUser) {
        try {
            UserGame userGame1 = userGameMap.remove(emailUser);
            UserGame userGame2 = userGameMap.remove(userGame1.getEnemyName());
            closeGameSession(userGame1, userGame2);
        }
        catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public int getCountGameSessions() {
        return allGameSessions.size();
    }

    public int getCountUsersInQueue() {
        return queueUsers.size();
    }

    private boolean userInQueue(String user) {
        return queueUsers.contains(user);
    }

    /*private void gmStep() {
        for (GameSession session : allGameSessions) {
            if (session.isGameOver()) {
                boolean firstWin = session.isWinner(session.getFirst());

                //webSocketService.notifyGameOver(session.getFirst(), firstWin);
                //webSocketService.notifyGameOver(session.getSecond(), !firstWin);

                closeGameSession(session.getFirst(), session.getSecond());
            }
        }
    }*/

}
