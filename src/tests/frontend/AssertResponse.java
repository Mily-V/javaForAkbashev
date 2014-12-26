package frontend;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;

/**
 * Created by serg on 20.12.14.
 */
public abstract class AssertResponse {
    public void assertResponseOk(HttpServletResponse response){
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
