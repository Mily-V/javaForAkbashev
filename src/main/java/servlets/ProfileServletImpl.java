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
 * Created by Dmitry on 027 27.09.14.
 */
public class ProfileServletImpl extends HttpServlet {
    private AccountService accountService;

    public ProfileServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        // Получение логина активного пользователя
        String sessionId = request.getSession().getId();

        if (accountService.haveSession(sessionId)) {
            String login = accountService.getUserLoginBySessionId(sessionId);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("login", login);
            pageVariables.put("email", accountService.getUserEmailBySessionId(sessionId));
            response.getWriter().println(PageGenerator.getPage("profile.html", pageVariables));
            response.setContentType("text/html;charset=utf-8"); //"application/json"
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            response.sendRedirect("/auth/signin");
        }
   }
}

