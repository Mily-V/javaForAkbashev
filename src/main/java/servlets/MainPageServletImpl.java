package servlets;

import base.AccountService;
import utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry on 013 13.09.14.
 */
public class MainPageServletImpl extends HttpServlet {
    private AccountService accountService;

    public MainPageServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        // Получение логина активного пользователя
        String sessionId = request.getSession().getId();
        String login = null;
        if (accountService.haveSession(sessionId)) {
            login = accountService.getUserLoginBySessionId(sessionId);
            pageVariables.put("login", login);
        }

        response.getWriter().println(PageGenerator.getPage("main.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}