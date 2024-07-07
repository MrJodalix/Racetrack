package logic;

import org.junit.Test;

import static org.junit.Assert.*;

import static logic.Game.testSymbol.X;
import static logic.Game.testSymbol.O;
import static logic.Game.testSymbol.L;

public class GameDataTest {

    @Test public void testGameDataConstructor () {
        int[][]  track     = new int[20][10];
        int      direction = 0;
        Player[] player    = new Player[4];
        int      current   = 0;
        GameData gameData  = new GameData(track, direction, player, current);
        assertArrayEquals(gameData.getTrack(), track);
        assertEquals(gameData.getDirection(), direction);
        assertArrayEquals(gameData.getPlayer(), player);
        assertEquals(current, gameData.getCurrentPlayer());
    }

    @Test public void testGameDataConstructorOverGame () {
        int[][]  dataTrack     = new int[20][10];
        int      dataDirection = 0;
        Player[] player    = new Player[4];
        int      current   = 0;
        GameData gameData  = new GameData(dataTrack, dataDirection, player, current);
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
        gameData.toGameData(game.getGameTrack(), game.getGameDirection(), game.getPlayer(),
                game.getCurrentPlayer(), "BigOval3PlayerSave");
    }

    @Test public void testGameDataSaveOverGame () {
        Game game1 = new Game("OtherSimpleOval.json", new FakeGUI());
        game1.saveGame("OtherSimpleOvalSave");
        Game game2 = new Game("OtherSimpleOvalSave.json", new FakeGUI());
        assertArrayEquals(game1.getGameTrack(), game2.getGameTrack());
        assertEquals(game1.getPlayer()[0].isActive(), game2.getPlayer()[0].isActive());
        assertEquals(game1.getPlayer()[0].getName(), game2.getPlayer()[0].getName());
        assertEquals(game1.getPlayer()[0].isAi(), game2.getPlayer()[0].isAi());
        assertArrayEquals(game1.getPlayer()[0].getCurrent(), game2.getPlayer()[0].getCurrent());
        assertArrayEquals(game1.getPlayer()[0].getLast(), game2.getPlayer()[0].getLast());
        assertEquals(game1.getPlayer()[0].getLap(), game2.getPlayer()[0].getLap());

        assertEquals(game1.getPlayer()[1].isActive(), game2.getPlayer()[1].isActive());
        assertEquals(game1.getPlayer()[1].getName(), game2.getPlayer()[1].getName());
        assertEquals(game1.getPlayer()[1].isAi(), game2.getPlayer()[1].isAi());
        assertArrayEquals(game1.getPlayer()[1].getCurrent(), game2.getPlayer()[1].getCurrent());
        assertArrayEquals(game1.getPlayer()[1].getLast(), game2.getPlayer()[1].getLast());
        assertEquals(game1.getPlayer()[1].getLap(), game2.getPlayer()[1].getLap());

        assertEquals(game1.getPlayer()[2].isActive(), game2.getPlayer()[2].isActive());
        assertEquals(game1.getPlayer()[2].getName(), game2.getPlayer()[2].getName());
        assertEquals(game1.getPlayer()[2].isAi(), game2.getPlayer()[2].isAi());
        assertArrayEquals(game1.getPlayer()[2].getCurrent(), game2.getPlayer()[2].getCurrent());
        assertArrayEquals(game1.getPlayer()[2].getLast(), game2.getPlayer()[2].getLast());
        assertEquals(game1.getPlayer()[2].getLap(), game2.getPlayer()[2].getLap());

        assertEquals(game1.getPlayer()[3].isActive(), game2.getPlayer()[3].isActive());
        assertEquals(game1.getPlayer()[3].getName(), game2.getPlayer()[3].getName());
        assertEquals(game1.getPlayer()[3].isAi(), game2.getPlayer()[3].isAi());
        assertArrayEquals(game1.getPlayer()[3].getCurrent(), game2.getPlayer()[3].getCurrent());
        assertArrayEquals(game1.getPlayer()[3].getLast(), game2.getPlayer()[3].getLast());
        assertEquals(game1.getPlayer()[3].getLap(), game2.getPlayer()[3].getLap());

        assertEquals(game1.getGameDirection(), game2.getGameDirection());
        assertEquals(game1.getCurrentPlayer(), game2.getCurrentPlayer());
    }
}
