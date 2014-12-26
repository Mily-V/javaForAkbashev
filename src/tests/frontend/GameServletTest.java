package frontend;

import base.AccountService;
import org.junit.Before;
import org.junit.Test;
import servlets.GameServlet;
import servlets.MainPageServletImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameServletTest extends AssertResponse {
    private Integer port = 42;
    final private HttpServletRequest request = mock(HttpServletRequest.class);
    final private HttpServletResponse response = mock(HttpServletResponse.class);
    final private HttpSession httpSession = mock(HttpSession.class);
    final private AccountService accountService = mock(AccountService.class);
    private GameServlet gameServlet = new GameServlet(accountService, port);
    final StringWriter stringWrite = new StringWriter();
    final PrintWriter writer = new PrintWriter(stringWrite);

    private String sessionId = "sessionId";
    private String login = "login1";

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getId()).thenReturn(sessionId);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoGetHaveSessionTrue() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(true);
        when(accountService.getUserLoginBySessionId(sessionId)).thenReturn(login);
        gameServlet.doGet(request, response);
        assertResponseOk(response);
        String st = stringWrite.toString();
        verify(accountService).getUserLoginBySessionId(sessionId);
        assertTrue(st.contains(login));
        assertTrue(st.contains(port.toString()));
        assertTrue(st.contains("<!DOCTYPE html>"));
    }

    @Test
    public void testDoGetHaveSessionFalse() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(false);
        gameServlet.doGet(request, response);
        verify(accountService,never()).getUserLoginBySessionId(sessionId);
        verify(response).sendRedirect("/main");
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}