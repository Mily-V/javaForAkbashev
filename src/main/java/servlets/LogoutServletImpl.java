package servlets;

import base.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitry on 013 13.09.14.
 */
public class LogoutServletImpl extends HttpServlet {
    private AccountService accountService;

    public LogoutServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        // Разрыв существующей сессии
        String sessionId = request.getSession().getId();
        if (accountService.haveSession(sessionId)) {
            accountService.deleteSession(sessionId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        // Формирование ответа
        response.sendRedirect("/main");
        response.setContentType("text/html;charset=utf-8");
    }
}