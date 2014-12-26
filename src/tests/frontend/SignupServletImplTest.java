package frontend;

import base.AccountService;
import servlets.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignupServletImplTest {
    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession httpSession = mock(HttpSession.class);
    final private static AccountService accountService = mock(AccountService.class);
    private SignupServletImpl signupServlet= new SignupServletImpl(accountService);
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
        when(request.getParameter("email")).thenReturn(email);
    }

   /* @Test
    public void testDoGetHaveSessionFalse() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(false);
        UserProfile userProfile = mock(UserProfile.class);
        when(accountService.getUserProfileBySessionId(sessionId)).thenReturn(userProfile);
        when(userProfile.getLogin()).thenReturn(login);
        when(accountService.getUserProfileByLogin(login)).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn(email);
        signupServlet.doGet(request,response);
        check();
        String st = stringWrite.toString();
        Assert.assertTrue(st.contains("Registration Form"));
    }*/

    @Test
    public void testDoGetHaveSessionTrue() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(true);
        signupServlet.doGet(request,response);
        //check();
        verify(response).sendRedirect("/main");
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        when(accountService.haveUser(login)).thenReturn(false);
        signupServlet.doPost(request, response);
        //verify(accountService).addUser(login, password, email);
        verify(accountService).addSession(sessionId, login);
        check();
        verify(response).sendRedirect("/main");
    }

    @Test
    public void testDoPostHaveUser() throws Exception {
        when(accountService.haveUser(login)).thenReturn(true);
        signupServlet.doPost(request, response);
        String st = stringWrite.toString();
        // System.out.append(st);
        Assert.assertTrue(st.contains("uncurrect login"));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}