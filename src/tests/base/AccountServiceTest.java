package base;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.UserProfile;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl();
    }

    private UserProfile[] addUsers(int cnt) {
        UserProfile[] userProfiles = new UserProfile[cnt];
        for (Integer i = 0; i < cnt; i++) {
            String username = "user";
            username += i.toString();
            String password = "pass";
            password += i.toString();
            String email = "email";
            email += i.toString() + "@mail.ru";
            userProfiles[i] = new UserProfile(username, password, email);
            accountService.addUser(userProfiles[i].getLogin(), userProfiles[i].getPassword(), userProfiles[i].getEmail()) ;
        }
        return userProfiles;
    }

    private Integer[] addSessions(UserProfile[] userProfiles, int cnt) {
        Integer[] sessions = new Integer[cnt];
        for (Integer i = 0; i < cnt; i++) {
            sessions[i] = i;
            accountService.addSession(sessions[i].toString(), userProfiles[i].getLogin());
        }
        return sessions;
    }

    @Test
    public void testCountOfUsers() throws Exception {
        UserProfile user1 = new UserProfile("login1", "password1", "email1");
        UserProfile user2 = new UserProfile("login2", "password2", "email2");
        long before = accountService.getCountOfUsers();
        accountService.addUser(user1.getLogin(), user1.getPassword(), user1.getEmail());
        assertEquals(1, accountService.getCountOfUsers() - before);
        accountService.addUser(user2.getLogin(), user2.getPassword(), user2.getEmail());
        assertEquals(2, accountService.getCountOfUsers() - before);
    }

    @Test
    public void testCountOfSessions() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        int before = accountService.getCountOfSessions();
        accountService.addSession("434",userProfiles[0].getLogin());
        assertEquals(1, accountService.getCountOfSessions() - before);
        accountService.addSession("476576",userProfiles[1].getLogin());
        assertEquals(2, accountService.getCountOfSessions() - before);
    }

    @Test
    public void testHaveUser() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        assertEquals(true, accountService.haveUser(userProfiles[0].getLogin()));
        assertEquals(true, accountService.haveUser(userProfiles[1].getLogin()));
    }

    @Test
    public void testAddUncorrectUser() throws Exception {
        UserProfile user1 = new UserProfile("", "password", "email1");
        UserProfile user2 = new UserProfile("sdgsdfhgsd", "", "email2");
        UserProfile user3 = new UserProfile("sadgdfnb", "pass", "");
        long before = accountService.getCountOfUsers();
        accountService.addUser(user1.getLogin(), user1.getPassword(), user1.getEmail());
        assertEquals(0, accountService.getCountOfUsers() - before);
        accountService.addUser(user2.getLogin(), user2.getPassword(), user2.getEmail());
        assertEquals(0, accountService.getCountOfUsers() - before);
        accountService.addUser(user3.getLogin(), user3.getPassword(), user3.getEmail());
        assertEquals(0, accountService.getCountOfUsers() - before);
    }

    @Test
    public void testAddRegisteredUserSession() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        int before = accountService.getCountOfSessions();
        addSessions(userProfiles, 1);
        int after = accountService.getCountOfSessions();
        assertEquals(1, after - before);
    }

    @Test
    public void testAddUnregisteredUserSession() throws Exception {
        String unregisteredLogin = "logind677hjtf";
        assertEquals(false, accountService.haveUser(unregisteredLogin));
        int before = accountService.getCountOfSessions();
        accountService.addSession("1", unregisteredLogin);
        int after = accountService.getCountOfSessions();
        assertEquals(0, after - before);
    }

    @Test
    public void testAddSameIdSession() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        int before = accountService.getCountOfSessions();
        accountService.addSession("1", userProfiles[0].getLogin());
        accountService.addSession("1", userProfiles[1].getLogin());
        int after = accountService.getCountOfSessions();
        assertEquals(1, after - before);
    }

    @Test
    public void testAddSameLoginSession() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        int before = accountService.getCountOfSessions();
        accountService.addSession("1", userProfiles[0].getLogin());
        accountService.addSession("2", userProfiles[0].getLogin());
        int after = accountService.getCountOfSessions();
        assertEquals(1, after - before);
    }

    @Test
    public void testHaveSession() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        Integer[] sessions = addSessions(userProfiles, 2);
        assertEquals(true, accountService.haveSession(sessions[0].toString()));
        assertEquals(true, accountService.haveSession(sessions[1].toString()));
    }

    @Test
    public void testDeleteSession() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        Integer[] sessions = addSessions(userProfiles, 2);
        accountService.deleteSession(sessions[1].toString());
        assertEquals(false, accountService.haveSession(sessions[1].toString()));
    }

    @Test
    public void testCorrectPassword() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        assertEquals(true, accountService.isPasswordCorrect(userProfiles[0].getLogin(),userProfiles[0].getPassword()));
    }

    @Test
    public void testUnCorrectPassword() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        String uncorrectPass= userProfiles[0].getPassword()+"fsdfs";
        assertEquals(false, accountService.isPasswordCorrect(userProfiles[0].getLogin(),uncorrectPass));
    }

    //made by Mily-V
    @Test
    public void testAddSameUser() throws Exception {
        UserProfile user1 = new UserProfile("l", "p", "e");
        long before = accountService.getCountOfUsers();
        accountService.addUser(user1.getLogin(), user1.getPassword(), user1.getEmail());
        accountService.addUser(user1.getLogin(), user1.getPassword(), user1.getEmail());
        assertEquals(1, accountService.getCountOfUsers() - before);
    }

    /*@Test
    /*public void testGetUserProfileByCorrectLogin() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        Integer[] sessions = addSessions(userProfiles, 2);
        assertEquals(userProfiles[0],accountService.getUserProfileByLogin(userProfiles[0].getLogin()));
        assertEquals(userProfiles[1],accountService.getUserProfileByLogin(userProfiles[1].getLogin()));
    }

    @Test
   public void testGetUserProfileByUncorrectLogin() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        Integer[] sessions = addSessions(userProfiles, 1);
        String uncorrectLogin = userProfiles[0].getLogin()+"123";
        assertEquals(null,accountService.getUserProfileByLogin(uncorrectLogin));
    }

    @Test
   public void testGetUserProfileByCorrectSessionId() throws Exception {
        UserProfile[] userProfiles = addUsers(2);
        Integer[] sessions = addSessions(userProfiles, 2);
        assertEquals(userProfiles[0],accountService.getUserProfileBySessionId(sessions[0].toString()));
        assertEquals(userProfiles[1],accountService.getUserProfileBySessionId(sessions[1].toString()));
    }

    @Test
  public void testGetUserProfileByUncorrectSessionId() throws Exception {
        UserProfile[] userProfiles = addUsers(1);
        Integer[] sessions = addSessions(userProfiles, 1);
        String uncorrectSessionId = sessions[0].toString()+"123";
        assertEquals(null,accountService.getUserProfileBySessionId(uncorrectSessionId));
    }*/
}