package logic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import static logic.Game.testSymbol.X;
import static logic.Game.testSymbol.O;
import static logic.Game.testSymbol.L;

public class GameTest {

    @Test
    public void testFieldNotValid() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, L, L, L},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, O, O, O, O, O},
        };
        Direction direction = Direction.SOUTH;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertNotEquals(0, game.isTrackValid());
    }

    @Test
    public void testMultipleLine() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, O, L, O, O, O, X},
                { X, O, O, O, O, L, O, O, O, X},
                { X, X, O, O, O, L, O, O, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, L, O, O, O, O, X},
                { X, O, O, O, L, O, O, O, O, X},
                { X, X, O, O, L, O, O, O, X, X},
                { X, X, X, X, X, X, X, X, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(5, game.isTrackValid());
    }

    @Test
    public void testOneLineToLong() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, L, L, L, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(1, game.isTrackValid());
    }

    @Test
    public void testToLongInX() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, O, L, O, O, O, X},
                { X, O, O, O, O, L, O, O, O, X},
                { X, X, O, O, O, L, O, O, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
                { X, X, O, O, O, O, O, O, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, X, X, X, X, X, X, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(3, game.isTrackValid());
    }

    @Test
    public void testToLongInY() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X, X, X, X, O, O, O, O, O, O, X, X, X},
                { X, O, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, O, X, X},
                { X, O, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, O, L, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, O, O, O, O, L, O, O, O, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, X, O, O, O, L, O, O, X, X, X, O, O, O, X, X, O, O, O, X, X},
                { X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(4, game.isTrackValid());
    }

    @Test
    public void testToShortInX() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X, X},
                { X, X, O, O, L, O, O, O, X, X},
                { X, O, O, O, L, O, O, O, O, X},
                { X, O, O, O, L, O, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, X, X, O, O, O, X},
                { X, O, O, O, O, O, O, O, O, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(1, game.isTrackValid());
    }

    @Test
    public void testToShortInY() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, X, X, L, L, L},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, O, O, O, O, O},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(1, game.isTrackValid());
    }

    @Test
    public void testNoLine() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, X, X, X, X, X, X, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(6, game.isTrackValid());
    }

    @Test
    public void testLineNotValid() {
        Game.testSymbol[][] track = {
                { X, X, X, X, X, X, X, X, X},
                { X, X, O, O, O, O, O, O, X},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, O, O, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, X, X, O, O, O},
                { X, O, O, O, L, L, O, O, O},
                { X, X, X, X, X, X, X, X, X},
        };
        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[1] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[2] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);
        players[3] = new Player(false, "", false, new int[] {0,0}, new int[] {0,0}, 0);

        int currentPlayer = 0;

        Game game = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(2, game.isTrackValid());
    }

    @Test
    public void testIsPlayerInLap1() {
        Game.testSymbol[][] track =
                {
                        { X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
                        { X, X, X, X, O, O, O, O, O, O, O, O, O, O, O, O, X, X, X, X },
                        { X, X, X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X, X, X },
                        { X, X, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, X, X },
                        { X, O, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, O, X },
                        { X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
                        { X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, X, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, X, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, L, L, L, L, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, X, X, X, X, X, X, X, X, X, X, O, O, O, O, X },
                        { X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
                        { X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
                        { X, O, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, O, X },
                        { X, X, O, O, O, O, O, O, O, X, X, O, O, O, O, O, O, O, X, X },
                        { X, X, X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X, X, X },
                        { X, X, X, X, O, O, O, O, O, O, O, O, O, O, O, O, X, X, X, X },
                        { X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X }
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] = new Player(true, "C. Speedhörnchen", false, new int[] { 25, 2 },
                new int[] { 31, 2 }, 1);
        players[1] =
                new Player(true, "N. Kannicht", false, new int[] { 26, 15 }, new int[] { 26, 15 },
                        0);
        players[2] =
                new Player(true, "G. Kalkuliert", true, new int[] { 34, 11 }, new int[] { 33, 14 },
                        1);
        players[3] = new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 1;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());
        assertEquals(1, game.getPlayer()[0].getLap());
        assertEquals(0, game.getPlayer()[1].getLap());
        assertEquals(1, game.getPlayer()[2].getLap());
        assertEquals(0, game.getPlayer()[3].getLap());
        assertTrue(game.getPlayer()[0].isActive());
    }
    @Test
    public void testHasPlayerWon () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 7, 7 }, new int[] { 5, 7 }, 1);
        players[1] =
                new Player(true, "N. Kannicht", true, new int[] { 5, 7 }, new int[] { 3, 5 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        boolean finish = game.turn(new int[] {10,7});
        finish = game.turn(new int[] {9,7});
        assertEquals(2, game.getPlayer()[0].getLap());
        assertEquals(1, game.getPlayer()[1].getLap());
        assertEquals(0, game.getPlayer()[2].getLap());
        assertEquals(0, game.getPlayer()[3].getLap());
        assertTrue(finish);
    }

    @Test
    public void testWhichPlayerHasWon () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 6, 7 }, new int[] { 4, 7 }, 1);
        players[1] =
                new Player(true, "N. Kannicht", true, new int[] { 7, 7 }, new int[] { 5, 7 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        game.turn(new int[] {10,7});
        game.turn(new int[] {9,7});
        assertEquals(1, game.whichOneIsWinner());
    }

    @Test
    public void testCalculatedStraightWay () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 6, 7 }, new int[] { 4, 7 }, 1);
        players[1] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        ArrayList<int[]> calcWay = game.calculateField();
        ArrayList<int[]> testWay = new ArrayList<>();
        testWay.add(new int[] {6,7});
        testWay.add(new int[] {7,7});
        testWay.add(new int[] {8,7});

        assertArrayEquals(calcWay.get(0), testWay.get(0));
        assertArrayEquals(calcWay.get(1), testWay.get(1));
        assertArrayEquals(calcWay.get(2), testWay.get(2));
    }

    @Test
    public void testCalculatedDiagonalWay () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, O},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 6, 7 }, new int[] { 4, 5 }, 1);
        players[1] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        ArrayList<int[]> calcWay = game.calculateField();
        ArrayList<int[]> testWay = new ArrayList<>();
        testWay.add(new int[] {6,7});
        testWay.add(new int[] {7,8});
        testWay.add(new int[] {8,9});

        assertArrayEquals(calcWay.get(0), testWay.get(0));
        assertArrayEquals(calcWay.get(1), testWay.get(1));
        assertArrayEquals(calcWay.get(2), testWay.get(2));
    }

    @Test
    public void testWayOverGravel () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, O},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 4, 3 }, new int[] { 4, 1 }, 1);
        players[1] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;
        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        game.turn(new int[] {4,6});
        assertArrayEquals(new int[] { 4, 3 }, game.getPlayer()[0].getCurrent());
    }

    @Test
    public void testWayOverOtherPlayer () {
        Game.testSymbol[][] track =
                {
                        {X, X, X, X, X, X, X, X, X, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, O},
                        {X, O, O, O, X, X, L, L, L, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, X, X, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, O, O, O, O, O, O, O, O, X},
                        {X, X, O, O, O, O, O, O, X, X},
                        {X, X, X, X, X, X, X, X, X, X},
                };

        Direction direction = Direction.EAST;

        Player[] players = new Player[4];
        players[0] =
                new Player(true, "J. Spotti", false, new int[] { 4, 2 }, new int[] { 2, 2 }, 1);
        players[1] =
                new Player(true, "C. Speedhörnchen", false, new int[] { 3, 2 }, new int[] { 3, 2 }, 1);
        players[2] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);
        players[3] =
                new Player(false, "", false, new int[] { 0, 0 }, new int[] { 0, 0 }, 0);

        int  currentPlayer = 0;

        Game game          = new Game(track, direction, currentPlayer, players, new FakeGUI());

        game.turn(new int[] {6,2});
        assertArrayEquals(new int[] { 6, 2 }, game.getPlayer()[0].getCurrent());
    }

}
