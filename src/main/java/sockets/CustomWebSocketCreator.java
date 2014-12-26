package sockets;

import base.AccountService;
import mechanics.GameMechanics;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author Dmitry
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(AccountService accountService,
                                  GameMechanics gameMechanics,
                                  WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = accountService.getUserLoginBySessionId(sessionId);
        GameWebSocket result = webSocketService.getWebSocket(name);
        if (result == null) {
            result = new GameWebSocket(name, gameMechanics, webSocketService);
            webSocketService.addUser(result);
        }
        return result;
    }
}
