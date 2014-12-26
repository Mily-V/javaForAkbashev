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

public class ProfileServletImplTest {
    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession httpSession = mock(HttpSession.class);
    final private static AccountService accountService = mock(AccountService.class);
    private ProfileServletImpl profileServlet= new ProfileServletImpl(accountService);
    final StringWriter stringWrite = new StringWriter();
    final PrintWriter writer = new PrintWriter(stringWrite);

    private String sessionId = "sessionId";
    private String login = "login1";
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
    }

   /*@Test
    public void testDoGetHaveSessionTrue() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(true);
        UserProfile userProfile = mock(UserProfile.class);
        when(accountService.getUserProfileBySessionId(sessionId)).thenReturn(userProfile);
        when(userProfile.getLogin()).thenReturn(login);
        when(accountService.getUserProfileByLogin(login)).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn(email);
        profileServlet.doGet(request,response);
        check();
        String st = stringWrite.toString();
        //System.out.append(st);
        //Assert.assertTrue(st.contains("<!DOCTYPE html>"));
        Assert.assertTrue(st.contains(login));
        Assert.assertTrue(st.contains(email));
    }*/

    @Test
    public void testDoGetHaveSessionFalse() throws Exception {
        when(accountService.haveSession(sessionId)).thenReturn(false);
        profileServlet.doGet(request,response);
        //check();
        verify(response).sendRedirect("/auth/signin");
    }
}