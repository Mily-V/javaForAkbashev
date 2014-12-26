package frontend;

import base.AccountService;
import servlets.*;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.*;
import static org.mockito.Mockito.*;

public class LogoutServletImplTest {
    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession httpSession = mock(HttpSession.class);
    final private static AccountService accountService = mock(AccountService.class);
    private LogoutServletImpl logoutServlet = new LogoutServletImpl(accountService);

    private String sessionId = "sessionId";

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getId()).thenReturn(sessionId);
    }

    @Test
    public void testDoPostHaveSessionTrue() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(true);

        logoutServlet.doPost(request, response);
        verify(accountService).deleteSession(sessionId);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).sendRedirect("/main");
        verify(response).setContentType("text/html;charset=utf-8");

    }

    @Test
    public void testDoPostHaveSessionFalse() throws Exception {

        when(accountService.haveSession(sessionId)).thenReturn(false);

        logoutServlet.doPost(request, response);

        verify(accountService, never()).deleteSession(sessionId);
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).sendRedirect("/main");
        verify(response).setContentType("text/html;charset=utf-8");
    }
}