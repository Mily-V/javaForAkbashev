package mechanics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sockets.WebSocketService;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Vadim on 29.11.14.
 */
public class GameMechanicsTest {
    WebSocketService webSocketService = mock(WebSocketService.class);
    GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

    String first = "first.player@mail.ru";
    String second = "second.player@mail.ru";


    @Before
    public void setUp() {
        gameMechanics.addUser(first);
        gameMechanics.addUser(second);
    }

    @After
    public void after() {
        gameMechanics.closeGame(first);
    }


    public ArrayList<Ship> createShips() {
        Ship fourDecker1 = new ShipImpl(0, 0, 3, 0);

        Ship threeDecker1 = new ShipImpl(5, 0, 7, 0);
        Ship threeDecker2 = new ShipImpl(0, 2, 2, 2);

        Ship twoDecker1 = new ShipImpl(4, 2, 5, 2);
        Ship twoDecker2 = new ShipImpl(7, 2, 8, 2);
        Ship twoDecker3 = new ShipImpl(0, 4, 1, 4);

        Ship oneDecker1 = new ShipImpl(3, 4, 3, 4);
        Ship oneDecker2 = new ShipImpl(5, 4, 5, 4);
        Ship oneDecker3 = new ShipImpl(7, 4, 7, 4);
        Ship oneDecker4 = new ShipImpl(9, 4, 9, 4);

        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(fourDecker1);
        ships.add(threeDecker1);
        ships.add(threeDecker2);
        ships.add(twoDecker1);
        ships.add(twoDecker2);
        ships.add(twoDecker3);
        ships.add(oneDecker1);
        ships.add(oneDecker2);
        ships.add(oneDecker3);
        ships.add(oneDecker4);

        return ships;
    }

    public void setShips(String emailUser, ArrayList<Ship> ships) {
        gameMechanics.setShips(emailUser, ships);
    }


    @Test
    public void testGetUserGameIsNull() {
        String user = "tmp";
        assertNull(gameMechanics.getUserGame(user));
    }

    @Test
    public void testCountGameSession() {
        int before = gameMechanics.getCountGameSessions();
        gameMechanics.addUser("test1@mail.ru");
        gameMechanics.addUser("test2@mail.ru");
        assertEquals(1, gameMechanics.getCountGameSessions() - before);
    }

    @Test
    public void testCloseGame() {
        gameMechanics.addUser("test1@mail.ru");
        gameMechanics.addUser("test2@mail.ru");
        int before = gameMechanics.getCountGameSessions();
        gameMechanics.closeGame("test1@mail.ru");
        assertEquals(1, before - gameMechanics.getCountGameSessions());
        gameMechanics.closeGame("test2.mail.ru");
        assertEquals(1, before - gameMechanics.getCountGameSessions());
    }

    @Test
    public void testSetShips() {
        ArrayList<Ship> ships = createShips();

        assertNull(gameMechanics.getUserGame(first).getMyField());
        assertNull(gameMechanics.getUserGame(first).getEnemyField());
        assertNull(gameMechanics.getUserGame(second).getMyField());
        assertNull(gameMechanics.getUserGame(second).getEnemyField());

        setShips(first, ships);
        setShips(second, ships);;

        assertNotNull(gameMechanics.getUserGame(first).getMyField());
        assertNotNull(gameMechanics.getUserGame(first).getEnemyField());
        assertNotNull(gameMechanics.getUserGame(second).getMyField());
        assertNotNull(gameMechanics.getUserGame(second).getEnemyField());
    }

    @Test
    public void testFire() {
        ArrayList<Ship> ships = createShips();
        setShips(first, ships);
        setShips(second, ships);

        Codes status = gameMechanics.fire(first, 0, 0);
        assertEquals(Codes.DECK, status);
        assertTrue(gameMechanics.getUserGame(second).getMyField().getCell(0, 0).isFired());

        status = gameMechanics.fire(first, 0, 0);
        assertEquals(Codes.IS_FIRED, status);
        status = gameMechanics.fire(first, -1, 0);
        assertEquals(Codes.CELL_DOES_NOT_EXIST, status);
        status = gameMechanics.fire(first, 0, 1000);
        assertEquals(Codes.CELL_DOES_NOT_EXIST, status);

    }

    @Test
    public void testGameOver() {
        ArrayList<Ship> ships = createShips();
        setShips(first, ships);
        setShips(second, ships);

        Codes status = Codes.EMPTY;
        for (int i = 0; (i < 10) && (status != Codes.GAME_OVER); ++i) {
            for (int j = 0; (j < 10) && (status != Codes.GAME_OVER); ++j) {
                status = gameMechanics.fire(first, i, j);
            }
        }
        assertEquals(Codes.GAME_OVER, status);
    }

    //made by Mily - V
    @Test
    public void testNearShips() {
        Ship fourDecker = new ShipImpl(0, 0, 3, 0);
        Ship threeDecker = new ShipImpl(3, 0, 5, 0);
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(fourDecker);
        ships.add(threeDecker);
        Codes status = gameMechanics.setShips(first, ships);
        assertEquals(Codes.ERROR, status);
    }

    @Test
    public void testCoverShips() {
        Ship fourDecker = new ShipImpl(0, 0, 3, 0);
        Ship threeDecker = new ShipImpl(2, 0, 4, 0);
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(fourDecker);
        ships.add(threeDecker);
        Codes status = gameMechanics.setShips(first, ships);
        assertEquals(Codes.ERROR, status);
    }

    @Test (expected = java.lang.ArrayIndexOutOfBoundsException.class)
    public void testShipBeyond() {
        Ship fourDecker = new ShipImpl(7, 0, 10, 0);
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(fourDecker);
        gameMechanics.setShips(first, ships);
    }

}
