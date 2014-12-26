package frontend;

import base.AccountService;
import servlets.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.UserProfile;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.mockito.Mockito.*;

public class SigninServletImplTest {
    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession httpSession = mock(HttpSession.class);
    final private static AccountService accountService = mock(AccountService.class);
    private SigninServletImpl signinServlet= new SigninServletImpl(accountService);
    final StringWriter stringWrite = new StringWriter();
    final PrintWriter writer = new PrintWriter(stringWrite);

    private String sessionId = "sessionId";
    private String login = "login1";
    private String password = "password";
    private String email = "email@mail.ru";

    private void check(){
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getId()).thenReturn(sessionId);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
    }

   /* @Test
    public void testDoGetHaveSessionFalse() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(false);
        UserProfile userProfile = mock(UserProfile.class);
        when(accountService.getUserProfileBySessionId(sessionId)).thenReturn(userProfile);
        when(userProfile.getLogin()).thenReturn(login);
        when(accountService.getUserProfileByLogin(login)).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn(email);
        signinServlet.doGet(request,response);
        check();
        String st = stringWrite.toString();
        Assert.assertTrue(st.contains("Enter yor login and password"));
    }*/

    @Test
    public void testDoGetHaveSessionTrue() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(true);
        signinServlet.doGet(request,response);
        //check();
        verify(response).sendRedirect("/main");
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        when(accountService.haveUser(login)).thenReturn(true);
        when(accountService.isPasswordCorrect(login, password)).thenReturn(true);
        signinServlet.doPost(request, response);
        verify(accountService).addSession(sessionId, login);
        verify(response).setStatus(200);
        verify(response).sendRedirect("/main");
    }

    @Test
    public void testDoPostHaveUserFalse() throws Exception {
        when(accountService.haveUser(login)).thenReturn(false);
        signinServlet.doPost(request, response);
        String st = stringWrite.toString();
       // System.out.append(st);
        Assert.assertTrue(st.contains("login does not exist"));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    public void testDoPostUncorrectPass() throws Exception {
        when(accountService.haveUser(login)).thenReturn(true);
        when(accountService.isPasswordCorrect(login, "password")).thenReturn(false);
        signinServlet.doPost(request, response);
        String st = stringWrite.toString();
       // System.out.append(st);
        Assert.assertTrue(st.contains("wrong password"));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}