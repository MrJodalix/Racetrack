package logic;

/**
 * Fake GUI used for testing the logic of the game Tic Tac Toe. All methods do
 * nothing. To ensure that the logic calls the correct methods for the gui, it
 * could be possibly to add package private boolean attributes, that tell if a
 * certain method has been called.
 *
 * @author cei
 */
public class FakeGUI implements GUIConnector {

    @Override
    public void displaySymbol(int[] oldCoord, int[] newCoord, int currentPlayer,
            Symbol origSymbol, Direction direction) {
    }

    @Override
    public void setCurrentPlayer(String name, int currentPlayer) {
    }

    @Override
    public void setErrorString (ValidTrack Error){
    }

    @Override
    public void onGameEnd(String winnerName, int currentPlayer) {
    }
}
