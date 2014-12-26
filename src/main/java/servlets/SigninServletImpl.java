package servlets;

import base.AccountService;
import org.json.simple.JSONObject;
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
public class SigninServletImpl extends HttpServlet {
    private AccountService accountService;

    public SigninServletImpl(AccountService accountService) {
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
            pageVariables.put("text", "Enter yor login and password");
            response.getWriter().println(PageGenerator.getPage("signin.html", pageVariables));
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
        String password = request.getParameter("password");

        // Обработка запроса + формирование ответа
        if (accountService.haveUser(login)) {
            if (accountService.isPasswordCorrect(login, password)) {
                accountService.addSession(sessionId, login);
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/main");
            } else {
                error(response, "wrong password");
            }
         } else {
            error(response, "login does not exist");
        }
    }

    private void error(HttpServletResponse response, String error) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("error", error);
        JSONObject json = new JSONObject();
        json.put("error", error);
        response.setContentType("application/json");
        response.getWriter().println(json.toString());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //response.getWriter().println(PageGenerator.getPage("signin.html", pageVariables));
        //response.setContentType("text/html;charset=utf-8");
    }
}
