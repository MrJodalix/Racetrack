package gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.Direction;
import logic.GUIConnector;
import logic.Symbol;
import logic.ValidTrack;

public class JavaFXGUI implements GUIConnector {
    /**
     * Images used to represent the symbols of the player.
     */
    private final ImageView[][] imgsField;
    private final Label lblCurrentPlayer;
    private final Label lblWinner;
    private final Label lblTrackValid;
    private final ImageView imgCurrentPlayer;
    private final GridPane grdPn;

    /**
     * The constructor. Gets passed all components of the gui that may change due to actions in the
     * logic.
     *
     * @param imgs the Image array, for realisation the field
     * @param lblCurrentPlayer the Label of the current Player
     * @param lblWinner the label for naming the winner
     * @param lblTrackValid the label for displaying if the track is valid or not.
     * @param imgCurrentPlayer the image of the current player
     * @param grdPn the GridPane for anchor the imageView on the Game field
     */
    public JavaFXGUI (ImageView[][] imgs, Label lblCurrentPlayer,
            Label lblWinner, Label lblTrackValid, ImageView imgCurrentPlayer, GridPane grdPn) {
        this.imgsField           = imgs;
        this.lblCurrentPlayer    = lblCurrentPlayer;
        this.lblWinner           = lblWinner;
        this.lblTrackValid       = lblTrackValid;
        this.imgCurrentPlayer    = imgCurrentPlayer;
        this.grdPn               = grdPn;
    }

    @Override
    public void displaySymbol (int[] oldCoord, int[] newCoord, int currentPlayer, Symbol origSymbol,
            Direction direction) {

        Image fieldPiece = switch (origSymbol) {
            case GRAVEL -> new Image("gui/img/Gravel.png");
            case STREET -> new Image("gui/img/Street.png");
            case LINE -> switch (direction) {
                case NORTH -> new Image("gui/img/LineN.png");
                case EAST -> new Image("gui/img/LineE.png");
                case SOUTH -> new Image("gui/img/LineS.png");
                case WEST -> new Image("gui/img/LineW.png");
            };
        };

        switch (currentPlayer) {
            case 0 -> imgCurrentPlayer.setImage(new Image("gui/img/red.png"));
            case 1 -> imgCurrentPlayer.setImage(new Image("gui/img/blue.png"));
            case 2 -> imgCurrentPlayer.setImage(new Image("gui/img/green.png"));
            case 3 -> imgCurrentPlayer.setImage(new Image("gui/img/purple.png"));
        }

        if (imgsField.length != 0 && newCoord != null) {
            imgsField[oldCoord[0]][oldCoord[1]].setImage(fieldPiece);
            imgsField[newCoord[0]][newCoord[1]].setImage(imgCurrentPlayer.getImage());
        }
    }

    @Override public void setCurrentPlayer (String name, int currentPlayer) {
        lblCurrentPlayer.setText(name);
        switch (currentPlayer) {
            case 0 -> imgCurrentPlayer.setImage(new Image("gui/img/red.png"));
            case 1 -> imgCurrentPlayer.setImage(new Image("gui/img/blue.png"));
            case 2 -> imgCurrentPlayer.setImage(new Image("gui/img/green.png"));
            case 3 -> imgCurrentPlayer.setImage(new Image("gui/img/purple.png"));
        }
    }

    @Override public void setErrorString (ValidTrack Error) {
        lblTrackValid.setText(Error.getStatus());
    }

    @Override public void onGameEnd (String winnerName, int currentPlayer) {
        grdPn.setDisable(true);
        lblWinner.setVisible(true);
        lblWinner.setText(winnerName);
        switch (currentPlayer) {
            case 0 -> imgCurrentPlayer.setImage(new Image("gui/img/red.png"));
            case 1 -> imgCurrentPlayer.setImage(new Image("gui/img/blue.png"));
            case 2 -> imgCurrentPlayer.setImage(new Image("gui/img/green.png"));
            case 3 -> imgCurrentPlayer.setImage(new Image("gui/img/purple.png"));
        }
    }
}
