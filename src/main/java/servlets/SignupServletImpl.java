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
public class SignupServletImpl extends HttpServlet {
    private AccountService accountService;

    public SignupServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        // Проверка существования сессии
        String sessionId = request.getSession().getId();
        if (accountService.haveSession(sessionId)) {
            response.sendRedirect("/main");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            // Формирование формы
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("text", "Registration Form");
            //response.getWriter().println(PageGenerator.getPage("signup.html", pageVariables));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        // Разрыв существующей сессии
        String sessionId = request.getSession().getId();
        if (accountService.haveSession(sessionId)) {
            accountService.deleteSession(sessionId);
        }

        // Чтение формы
        String login = request.getParameter("login");
        login = login.trim();
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Обработка запроса + формирование ответа
        Map<String, Object> pageVariables = new HashMap<>();
        if (login.isEmpty() || accountService.haveUser(login)) {
            pageVariables.put("error", "uncorrect login");
           response.getWriter().println(PageGenerator.getPage("signup.html", pageVariables));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            accountService.addUser(login, password, email);
            accountService.addSession(sessionId, login);
            response.sendRedirect("/main");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setContentType("text/html;charset=utf-8");
    }
}
