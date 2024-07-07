package logic;

/**
 * Interface used for the logic of the game racetrack to
 * communicate with the gui.
 *
 * @author Joshua-Scott Schöttke
 */
public interface GUIConnector {

    /**
     * Displays a given symbol at a specified cell of the field.
     *
     * @param oldCoord the coordinates at which in the field the given symbol
     * @param newCoord the coordinates at which in the field the given symbol
     * should be displayed
     */
    void displaySymbol(int[] oldCoord, int[] newCoord, int currentPlayer, Symbol origSymbol
            , Direction direction);


    /**
     * Sets the name and symbol of the current player on the gui.
     *
     * @param name name of the current player
     */
    void setCurrentPlayer (String name, int currentPlayer);

    /**
     * Sets the text of the Errorcode
     * @param Error The Errorcode
     */
    void setErrorString (ValidTrack Error);

    /**
     * Called when the game is won by a player. Needs to display the name of the
     * winner on the gui and has to ensure that the user cannot continue playing
     * (e.g. by disabling components).
     *
     * @param winnerName name of the player than won
     */
    void onGameEnd(String winnerName, int currentPlayer);
}
