package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

/**
 * This class loads and saves the game-files in a json format
 *
 * @author Joshua-Scott Schöttke
 */
public class GameData {

    /**
     * Field for play
     */
    private int[][] track;

    /**
     * Race direction
     */
    private int direction;

    /**
     * Information of all players
     */
    private Player[] player;

    /**
     * Index of current Player
     */
    private int currentPlayer;

    /**
     * Constructor
     *
     * @param track         the field datas
     * @param direction     the direction for the race
     * @param player        the players
     * @param currentPlayer the current player
     */
    public GameData (int[][] track, int direction, Player[] player, int currentPlayer) {
        this.track         = track;
        this.direction     = direction;
        this.player        = player;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter
     **/
    public int[][] getTrack () {
        return track;
    }

    public int getDirection () {
        return direction;
    }

    public Player[] getPlayer () {
        return player;
    }

    public int getCurrentPlayer () {
        return currentPlayer;
    }

    /**
     * loads the Data of an json data
     *
     * @param filename the name of the file in the saves folder
     *
     * @return the basic data of the game
     */
    protected GameData getGameData (String filename) {
        Gson gson = new Gson();

        File currDir = null;
        try {
            currDir = new File(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            //oops... ¯\_(ツ)_/¯
            //guess we won't be opening the dialog in the right directory
        }
        //Step 2: Put it together
        FileChooser fileChooser = new FileChooser();
        if (currDir != null) {
            //ensure the dialog opens in the correct directory
            fileChooser.setInitialDirectory(currDir.getParentFile().getParentFile());
            fileChooser.setInitialDirectory(
                    new File(fileChooser.getInitialDirectory() + "/saves/" + filename));
        }

        String path = fileChooser.getInitialDirectory().getPath();

        GameData gameData;

        try (Reader reader = new FileReader(path)) {

            gameData = gson.fromJson(reader, GameData.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gameData;
    }

    /**
     * saves the current game in basic data in a format of json
     *
     * @param gameTrack the current GameField
     * @param gameDirection the current direction
     * @param player all players
     * @param currentPlayer the current-player
     * @param filename the filename for saving
     */
    protected void toGameData (Symbol[][] gameTrack, Direction gameDirection, Player[] player,
            int currentPlayer, String filename) {
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

        File currDir = null;
        try {
            currDir = new File(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            //oops... ¯\_(ツ)_/¯
            //guess we won't be opening the dialog in the right directory
        }
        //Step 2: Put it together
        FileChooser fileChooser = new FileChooser();
        if (currDir != null) {
            //ensure the dialog opens in the correct directory
            fileChooser.setInitialDirectory(currDir.getParentFile().getParentFile());
            fileChooser.setInitialDirectory(
                    new File(fileChooser.getInitialDirectory() + "/saves/" + filename + ".json"));
        }

        String directionFile = fileChooser.getInitialDirectory().getPath();

        int[][] track = new int[gameTrack.length][gameTrack[0].length];

        int direction = gameDirection.ordinal();

        for (int x = 0; x < gameTrack.length; x++) {
            for (int y = 0; y < gameTrack[x].length; y++) {
                if (gameTrack[x][y] == Symbol.GRAVEL) {
                    track[x][y] = 0;
                } else if (gameTrack[x][y] == Symbol.STREET) {
                    track[x][y] = 1;
                } else {
                    track[x][y] = 2;
                }
            }
        }

        try (FileWriter writer = new FileWriter(directionFile)) {

            GameData gameData = new GameData(track, direction, player, currentPlayer);
            gsonPretty.toJson(gameData, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
