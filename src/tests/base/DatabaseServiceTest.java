package base;

import dataSets.UserDataSet;
import org.junit.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.Assert.*;

public class DatabaseServiceTest {

    private static DatabaseService databaseService = new DBServiceImpl("TEST_EPICGAME_TP");
    private UserDataSet[] userDataSets;
    @Before
    public void setUp() throws Exception {
        userDataSets = new dataSets.UserDataSet[6];
        for (Integer i = 0; i < userDataSets.length; i++) {
            String username = "user" + i.toString();
            String password = "pass" + i.toString();
            String email = "email" + i.toString() + "@mail.ru";
            userDataSets[i] = new dataSets.UserDataSet(username, password, email);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        databaseService.deleteAllUsers();
    }

    @Test
    public void testAddUser () throws Exception {
        UserDataSet user = userDataSets[0];
        UserDataSet resUser = databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        assertEquals(user.getLogin(), resUser.getLogin());
        assertEquals(user.getEmail(), resUser.getEmail());
        assertEquals(user.getPassword(), resUser.getPassword());
        assertFalse(user.getId()==resUser.getId());
    }

    @Test
    public void testCountOfUsers() throws Exception {
        long before = databaseService.countOfUsers();
        UserDataSet user = userDataSets[1];
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        assertEquals(1l, databaseService.countOfUsers() - before);
        user = userDataSets[2];
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        assertEquals(2, databaseService.countOfUsers() - before);
    }

    @Test
    public void testCheckUserRegisteredTrue() throws Exception {
        UserDataSet user = userDataSets[3];
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        assertEquals(true, databaseService.checkUserRegistered(user.getLogin()));
    }

    @Test
    public void testCheckUserRegisteredFalse() throws Exception {
        assertEquals(false, databaseService.checkUserRegistered("uncorrect login"));
    }

    @Test
    public void testAddExistingUser() throws Exception {
        UserDataSet user = userDataSets[4];
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        assertEquals(null, databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail()));
    }

    @Test
    public void testGetExistingUser() throws Exception {
        UserDataSet user = userDataSets[5];
        UserDataSet addedUser = databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        UserDataSet resUser = databaseService.getUser(user.getLogin());
        assertEquals(addedUser.getLogin(), resUser.getLogin());
        assertEquals(addedUser.getEmail(), resUser.getEmail());
        assertEquals(addedUser.getPassword(), resUser.getPassword());
        assertEquals(addedUser.getId(), resUser.getId());
    }

    @Test
    public void testGetNonexistentUser() throws Exception {
        UserDataSet resUser = databaseService.getUser("nonexistent user login");
        assertEquals(null, resUser);
    }

    //made by Mily - V
    @Test (expected = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    public void testAddSameUser() throws Exception {
        UserDataSet user = userDataSets[5];
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        databaseService.addUser(user.getLogin(), user.getPassword(), user.getEmail());
    }

}