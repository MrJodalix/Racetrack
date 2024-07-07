package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import logic.Direction;
import logic.Game;
import logic.Player;
import logic.Symbol;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Main class for the user interface.
 *
 * @author mjo, cei, Joshua-Scott Schöttke
 */
public class UserInterfaceController implements Initializable {

    /**
     * Spielfeld
     */
    @FXML private GridPane grdPn;
    @FXML private HBox HBxLeft;
    @FXML private VBox VBxLeftPlayer;
    @FXML private VBox VBxLeftLoad;
    @FXML private VBox VBxLeftSave;
    @FXML private VBox VBxRight;
    @FXML private VBox VBxLeftGrid;
    @FXML private VBox VBxRightGrid;
    @FXML private HBox HBxTopGrid;
    @FXML private HBox HBxDownGrid;

    /**
     * Menu Items
     */
    @FXML private MenuItem miNG;
    @FXML private MenuItem miSG;
    @FXML private MenuItem miLG;
    @FXML private RadioMenuItem rmiSS;
    @FXML private RadioMenuItem rmiMS;
    @FXML private RadioMenuItem rmiHS;
    @FXML private MenuItem miSE;
    @FXML private MenuItem miNME;
    @FXML private MenuItem miLME;
    @FXML private MenuItem miSME;

    /**
     * Buttons
     */
    @FXML private Button btnSave;
    @FXML private Button btnCheck;
    @FXML private Button btnStartRace;
    @FXML private Button deleteRowAbove;
    @FXML private Button addRowAbove;
    @FXML private Button addColumnLeft;
    @FXML private Button deleteColumnLeft;
    @FXML private Button addColumnRight;
    @FXML private Button deleteColumnRight;
    @FXML private Button deleteRowBelow;
    @FXML private Button addRowBelow;

    /**
     * Textfelder
     */
    @FXML private TextField txtfldPlayer0;
    @FXML private TextField txtfldPlayer1;
    @FXML private TextField txtfldPlayer2;
    @FXML private TextField txtfldPlayer3;
    @FXML private TextField saveName;
    /**
     * Labels
     */
    @FXML private Label lblPickStartPlayer;
    @FXML private Label lblNextMovePlayer;
    @FXML private Label lblstcCurrPlayer;
    @FXML private Label lblCurrentPlayer;
    @FXML private Label lblWinner;
    @FXML private Label lblstcCheck;
    @FXML private Label lblTrackValid;
    @FXML private Label lblOneOption;
    @FXML private ImageView imgCurrPlayer;

    /**
     * Choice Box and choices as strings
     */
    @FXML private ChoiceBox<String> playerStatusBox0;
    @FXML private ChoiceBox<String> playerStatusBox1;
    @FXML private ChoiceBox<String> playerStatusBox2;
    @FXML private ChoiceBox<String> playerStatusBox3;
    /**
     * The choices of the status of the player
     */
    private final String[] active = { "Not active!", "Human", "Ai" };
    /**
     * the ListView for loading a save Game
     */
    @FXML private ListView<String> saveGames = new ListView<>();

    /**
     * Attribut vom Spielfeld zur Initialisierung des Feldes
     */
    private ImageView[][] field;

    private Game game;
    /**
     * distinguish between a race or the Editor
     */
    private boolean isRace = false;

    /**
     * This is where you need to add code that should happen during initialization and then change
     * the java doc comment.
     *
     * @param location  probably not used
     * @param resources probably not used
     */
    @Override public void initialize (URL location, ResourceBundle resources) {
        grdPn.setDisable(true);
        rmiSS.setDisable(true);
        rmiMS.setDisable(true);
        rmiHS.setDisable(true);
        //right Box visible
        orgVBxRight(false);
        lblCurrentPlayer.setVisible(false);
        lblstcCurrPlayer.setVisible(false);
        imgCurrPlayer.setVisible(false);
        lblWinner.setVisible(false);
        lblOneOption.setVisible(false);
        //left Box visible
        orgVBxLeftLoad(false);
        orgVBxLeftSave(false);
        //Button visible
        orgVBxRightGrid(false);
        orgVBxLeftGrid(false);
        orgHBxTopGrid(false);
        orgHBxDownGrid(false);
        //choiceBox initialize
        playerStatusBox0.getItems().addAll(active);
        playerStatusBox0.setValue(active[1]);
        playerStatusBox1.getItems().addAll(active);
        playerStatusBox1.setValue(active[2]);
        playerStatusBox2.getItems().addAll(active);
        playerStatusBox2.setValue(active[0]);
        playerStatusBox3.getItems().addAll(active);
        playerStatusBox3.setValue(active[0]);

        field = new ImageView[][] {};

        game = new Game("Empty_Play.json",
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn));

        resetGridPane(game.getGameTrack().length, game.getGameTrack()[0].length);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        miSG.setDisable(true);
        orgSetEditorMenu(true);
        VBxLeftPlayer.setDisable(true);
    }

    /**
     * Creates an array of imageviews corresponding to the gridPane. Each imageView becomes a child
     * of the gridPane and fills a cell. For proper resizing it is binded to the gridPanes width and
     * height. If the GridPane has a hgap or a vgap it is necessary to also consider these when
     * binding. A default image could be added by passing another parameter into this method.
     *
     * @param grdPn the GridPane to which ImageViews should be added
     *
     * @return an array of imageviews added to the gridPane
     */
    private ImageView[][] initImages (GridPane grdPn, Symbol[][] symbols, Direction direction) {
        Image Gravel = new Image("gui/img/Gravel.png");
        Image Street = new Image("gui/img/Street.png");
        Image Arrow  = new Image("gui/img/LineN.png");
        switch (direction) {
            case EAST -> Arrow = new Image("gui/img/LineE.png");
            case SOUTH -> Arrow = new Image("gui/img/LineS.png");
            case WEST -> Arrow = new Image("gui/img/LineW.png");
        }

        int colcount = symbols.length;
        int rowcount = symbols[0].length;
        field = new ImageView[colcount][rowcount];
        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                field[x][y] = new ImageView();
                //creates an empty imageview
                if (symbols[x][y] == Symbol.STREET) {
                    field[x][y].setImage(Street);
                } else if (symbols[x][y] == Symbol.GRAVEL) {
                    field[x][y].setImage(Gravel);
                } else {
                    field[x][y].setImage(Arrow);
                }

                //add the imageview to the cell and assign the correct indicees for
                //this imageview, so you later can use GridPane.getColumnIndex(Node)
                grdPn.add(field[x][y], x, y);

                //the image shall resize when the cell resizes
                field[x][y].fitWidthProperty()
                        .bind(grdPn.widthProperty().divide(colcount).subtract(grdPn.getHgap()));
                field[x][y].fitHeightProperty()
                        .bind(grdPn.heightProperty().divide(rowcount).subtract(grdPn.getVgap()));
            }
        }
        return field;
    }

    /**
     * initialise the preparation of the Race
     */
    @FXML private void initStartGame () {
        game = new Game("Empty_Play.json",
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn));

        resetGridPane(game.getGameTrack().length, game.getGameTrack()[0].length);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        grdPn.setDisable(true);
        orgHBxLeft(true);

        orgVBxLeftLoad(false);
        orgVBxLeftPlayer(true);
        orgVBxLeftSave(false);

        orgVBxLeftGrid(false);
        orgVBxRightGrid(false);
        orgHBxTopGrid(false);
        orgHBxDownGrid(false);

        orgSetEditorMenu(false);

        orgVBxRight(false);
    }

    /**
     * checks all playerStatus. When all not active, then the game cant start
     */
    @FXML private boolean checkOneActive () {
        boolean oneIsActive = !(playerStatusBox0.getValue().equals(active[0]));
        if (!oneIsActive && !(playerStatusBox1.getValue().equals(active[0]))) {
            oneIsActive = true;
        }
        if (!oneIsActive && !(playerStatusBox2.getValue().equals(active[0]))) {
            oneIsActive = true;
        }
        if (!oneIsActive && !(playerStatusBox3.getValue().equals(active[0]))) {
            oneIsActive = true;
        }
        btnStartRace.setDisable(!oneIsActive);
        return oneIsActive;
    }

    /**
     * sets up a new Game
     */
    @FXML private void startGame () {
        prepPlayerStats();
        boolean oneActive = false;
        for (int i = 0; i < game.getPlayer().length && !oneActive; i++) {
            if (game.getPlayer()[i].isActive()) {
                oneActive = true;
            }
        }
        if (oneActive) {
            game.isTrackValid();
            isRace = true;
            game   = new Game(game.getGameTrack(), game.getGameDirection(), game.getCurrentPlayer(),
                    game.getPlayer(),
                    new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                            grdPn));

            prepPlayerStats();
            Image currentImage = firstPlayerImage();
            grdPn.setDisable(false);
            orgVBxRight(true);
            orgHBxLeft(false);
            orgVBxLeftLoad(true);
            orgVBxLeftPlayer(false);
            orgVBxLeftSave(false);
            lblstcCurrPlayer.setVisible(true);
            lblCurrentPlayer.setText(firstPlayerName());
            lblCurrentPlayer.setVisible(true);
            orgLblPickStartPlayer(true);
            orgLblNextMovePlayer(false);
            orgLblOneOption(false);
            imgCurrPlayer.setImage(currentImage);
            imgCurrPlayer.setVisible(true);
            miNG.setDisable(true);
            orgSetEditorMenu(true);
            miSE.setDisable(true);
        }
    }

    /**
     * sets the Image of the first Player
     *
     * @return the image of the first player
     */
    private Image firstPlayerImage () {
        Image firstPlayer = new Image("gui/img/purple.png");
        if (game.getPlayer()[0].isActive()) {
            firstPlayer = new Image("gui/img/red.png");
        } else if (game.getPlayer()[1].isActive()) {
            firstPlayer = new Image("gui/img/blue.png");
        } else if (game.getPlayer()[2].isActive()) {
            firstPlayer = new Image("gui/img/green.png");
        }
        return firstPlayer;
    }

    /**
     * sets the Image of the first Player
     *
     * @return the image of the first player
     */
    @FXML private String firstPlayerName () {
        String firstPlayer = txtfldPlayer3.getText();
        if (game.getPlayer()[0].isActive()) {
            firstPlayer = txtfldPlayer0.getText();
        } else if (game.getPlayer()[1].isActive()) {
            firstPlayer = txtfldPlayer1.getText();
        } else if (game.getPlayer()[2].isActive()) {
            firstPlayer = txtfldPlayer2.getText();
        }
        return firstPlayer;
    }

    /**
     * resets the gridpane
     *
     * @param columnLength the length of the field
     * @param rowLength    the width of the field
     */
    private void resetGridPane (int columnLength, int rowLength) {
        grdPn.getChildren().clear();
        grdPn.getColumnConstraints().clear();
        grdPn.getRowConstraints().clear();

        for (int i = grdPn.getColumnCount(); i < columnLength; i++) {
            ColumnConstraints newColConstrains = new ColumnConstraints();
            newColConstrains.setMinWidth(10.0);
            grdPn.getColumnConstraints().add(newColConstrains);
        }
        for (int j = grdPn.getRowCount(); j < rowLength; j++) {
            RowConstraints newRowConstrains = new RowConstraints();
            newRowConstrains.setMinHeight(10.0);
            grdPn.getRowConstraints().add(newRowConstrains);
        }
    }

    /**
     * prepare all player stats from the Gamers If active, if AI, names if the first player is AI
     * then it will be prepared for the race
     */
    private void prepPlayerStats () {
        int     countAi      = 0;
        boolean playerActive = !playerStatusBox0.getValue().equals(active[0]);
        boolean playerAi     = playerActive && playerStatusBox0.getValue().equals(active[2]);

        game.getPlayer()[0] =
                new Player(playerActive, txtfldPlayer0.getText(), playerAi, new int[] { 0, 0 },
                        new int[] { 0, 0 }, 0);
        playerActive        = !playerStatusBox1.getValue().equals(active[0]);
        playerAi            = playerActive && playerStatusBox1.getValue().equals(active[2]);
        game.getPlayer()[1] =
                new Player(playerActive, txtfldPlayer1.getText(), playerAi, new int[] { 0, 0 },
                        new int[] { 0, 0 }, 0);
        playerActive        = !playerStatusBox2.getValue().equals(active[0]);
        playerAi            = playerActive && playerStatusBox2.getValue().equals(active[2]);
        game.getPlayer()[2] =
                new Player(playerActive, txtfldPlayer2.getText(), playerAi, new int[] { 0, 0 },
                        new int[] { 0, 0 }, 0);
        playerActive        = !playerStatusBox3.getValue().equals(active[0]);
        playerAi            = playerActive && playerStatusBox3.getValue().equals(active[2]);
        game.getPlayer()[3] =
                new Player(playerActive, txtfldPlayer3.getText(), playerAi, new int[] { 0, 0 },
                        new int[] { 0, 0 }, 0);

        if (game.getPlayer()[0].isActive() && game.getPlayer()[0].isAi()) {
            game.prepRaceSetImage(game.getPlayer()[0].getLast());
            countAi++;
        }

        if (game.getPlayer()[1].isActive() && game.getPlayer()[1].isAi() && countAi == 1) {
            game.prepRaceSetImage(game.getPlayer()[1].getLast());
            countAi++;
        }

        if (game.getPlayer()[2].isActive() && game.getPlayer()[2].isAi() && countAi == 2) {
            game.prepRaceSetImage(game.getPlayer()[2].getLast());
            countAi++;
        }

        if (game.getPlayer()[3].isActive() && game.getPlayer()[3].isAi() && countAi == 3) {
            game.prepRaceSetImage(game.getPlayer()[3].getLast());
        }
        for (int i = game.getPlayer().length - 1; i >= 0; i--) {
            if (game.getPlayer()[i].isActive()) {
                game.setCurrentPlayer(i);
            }
        }
    }

    /**
     * initialize the list of all loadable games and show it in a listview
     */
    @FXML private void initLoadGame () throws IOException {
        grdPn.setDisable(true);
        miSG.setDisable(true);
        miSE.setDisable(false);
        orgVBxRight(false);
        orgHBxLeft(true);
        orgVBxLeftLoad(true);
        orgVBxLeftPlayer(false);
        orgVBxLeftSave(false);
        lblTrackValid.setVisible(false);

        //Step 1: Figure out where we are currently are, so we can open the dialog in
        //the same directory the jar is located. See also
        //https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
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
                    new File(fileChooser.getInitialDirectory() + "/saves" + "/"));
        }
        File   selectedFile = fileChooser.getInitialDirectory();
        File[] folder       = selectedFile.listFiles();

        if (folder != null) {
            if (folder.length != saveGames.getItems().size()) {
                for (File file : folder) {
                    if (file.isFile()) {
                        String gamefile = file.getName();
                        saveGames.getItems().add(gamefile);
                    }
                }
            }
        }
    }

    /**
     * sets up a new Game that was loaded by the players
     */
    @FXML private void loadGame () {
        int    selectedID = saveGames.getSelectionModel().getSelectedIndex();
        String filename   = saveGames.getItems().get(selectedID);

        field = new ImageView[][] {};
        field = new ImageView[][] {};

        game = new Game(filename,
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn));

        if (game.isTrackValid() == 0) {
            resetGridPane(game.getGameTrack().length, game.getGameTrack()[0].length);

            field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

            game = new Game(filename,
                    new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                            grdPn));
            setStatusBox(game.getPlayer());
            orgVBxLeftSave(false);
            btnCheck.setDisable(false);
            if (checkOneActive()) {
                isRace = true;
                lblstcCurrPlayer.setVisible(true);
                lblCurrentPlayer.setText(firstPlayerName());
                lblCurrentPlayer.setVisible(true);
                imgCurrPlayer.setVisible(true);
                orgHBxLeft(false);
                orgVBxRight(true);
                orgVBxLeftPlayer(false);
                orgVBxLeftLoad(false);
                orgVBxLeftSave(false);
                orgLblPickStartPlayer(false);
                orgVBxLeftGrid(false);
                orgVBxRightGrid(false);
                orgHBxDownGrid(false);
                orgHBxTopGrid(false);
                setPlayers(game.getPlayer());
                setPossibleImage();
                grdPn.setDisable(false);
                miSG.setDisable(false);
            } else {
                orgHBxLeft(true);
                orgVBxRight(false);
                orgVBxLeftPlayer(true);
                orgVBxLeftLoad(false);
                orgVBxLeftSave(false);

                miSE.setDisable(false);
            }
        } else {
            orgVBxLeftSave(true);
            btnSave.setDisable(true);
            btnCheck.setDisable(true);
            lblTrackValid.setVisible(true);
        }
    }

    /**
     * Sets the status boxes for loading a game
     */
    @FXML void setStatusBox (Player[] players) {
        if (!players[0].isActive()) {
            playerStatusBox0.setValue(active[0]);
        } else {
            if (!players[0].isAi()) {
                playerStatusBox0.setValue(active[1]);
            } else {
                playerStatusBox0.setValue(active[2]);
            }
        }
        if (!players[1].isActive()) {
            playerStatusBox1.setValue(active[0]);
        } else {
            if (!players[1].isAi()) {
                playerStatusBox1.setValue(active[1]);
            } else {
                playerStatusBox1.setValue(active[2]);
            }
        }
        if (!players[2].isActive()) {
            playerStatusBox2.setValue(active[0]);
        } else {
            if (!players[2].isAi()) {
                playerStatusBox2.setValue(active[1]);
            } else {
                playerStatusBox2.setValue(active[2]);
            }
        }
        if (!players[3].isActive()) {
            playerStatusBox3.setValue(active[0]);
        } else {
            if (!players[3].isAi()) {
                playerStatusBox3.setValue(active[1]);
            } else {
                playerStatusBox3.setValue(active[2]);
            }
        }
    }

    /**
     * loads all player images, when the game was loading, and it is an active Race.
     *
     * @param players all players in game
     */
    @FXML void setPlayers (Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].isActive()) {
                game.prepLoadGame(i, players[i].getCurrent());
            }
        }
    }

    /**
     * initialise the save side of the Game
     */
    @FXML void initSaveGame () {
        orgHBxLeft(true);
        orgVBxRight(false);
        orgVBxLeftPlayer(false);
        orgVBxLeftLoad(false);
        orgVBxLeftSave(true);

        lblstcCheck.setManaged(true);
        lblTrackValid.setManaged(true);
        lblTrackValid.setVisible(false);
        btnCheck.setManaged(true);
        saveName.setDisable(true);
        btnSave.setDisable(true);
    }

    /**
     * check the Track. If the Track is valid, then it can be saved If the Track is not valid, then
     * it returns to editor
     */
    @FXML private void checkTrack () {
        lblTrackValid.setVisible(true);
        if (game.isTrackValid() == 0) {
            saveName.setDisable(false);
            btnSave.setDisable(false);

        } else {
            grdPn.setDisable(false);

            HBxDownGrid.setDisable(false);
            HBxTopGrid.setDisable(false);
            VBxRightGrid.setDisable(false);
            VBxLeftGrid.setDisable(false);
        }
    }

    /**
     * saves the current game
     */
    @FXML private void saveGame () {
        game.saveGame(saveName.getText());
        orgHBxLeft(false);
        orgVBxRight(true);
        orgVBxLeftPlayer(false);
        orgVBxLeftLoad(false);
        orgVBxLeftSave(false);
    }

    /**
     * initialise the Editor
     */
    @FXML private void initEditor () {
        orgSetEditorMenu(false);
        grdPn.setDisable(false);

        orgHBxLeft(false);
        orgVBxRight(false);
        orgVBxLeftPlayer(false);
        orgVBxLeftLoad(false);
        orgVBxLeftSave(false);

        orgVBxLeftGrid(true);
        orgVBxRightGrid(true);
        orgHBxTopGrid(true);
        orgHBxDownGrid(true);

        addRowBelow.setManaged(true);
        addRowAbove.setManaged(true);
        addColumnRight.setManaged(true);
        addColumnLeft.setManaged(true);
        deleteColumnRight.setManaged(true);
        deleteColumnLeft.setManaged(true);
        deleteRowBelow.setManaged(true);
        deleteRowAbove.setManaged(true);

        initEditButtons();
    }

    /**
     * initialise the buttons for adding and delete rows and cols
     */
    @FXML private void initEditButtons () {
        if (grdPn.getRowCount() < game.getMAXFIELDWIDTH()) {
            addRowBelow.setDisable(false);
            addRowAbove.setDisable(false);
        } else {
            addRowBelow.setDisable(true);
            addRowAbove.setDisable(true);
        }

        if (grdPn.getColumnCount() < game.getMAXFIELDLENGTH()) {
            addColumnRight.setDisable(false);
            addColumnLeft.setDisable(false);
        } else {
            addColumnRight.setDisable(true);
            addColumnLeft.setDisable(true);
        }

        if (grdPn.getColumnCount() > game.getMINFIELDWIDTHLENGTH()) {
            deleteColumnRight.setDisable(false);
            deleteColumnLeft.setDisable(false);
        } else {
            deleteColumnRight.setDisable(true);
            deleteColumnLeft.setDisable(true);
        }

        if (grdPn.getRowCount() > game.getMINFIELDWIDTHLENGTH()) {
            deleteRowBelow.setDisable(false);
            deleteRowAbove.setDisable(false);
        } else {
            deleteRowBelow.setDisable(true);
            deleteRowAbove.setDisable(true);
        }
    }

    /**
     * initialise a new Map for editor
     */
    @FXML private void initNewMapEditor () {
        initEditor();
        resetGridPane(game.getMINFIELDWIDTHLENGTH(), game.getMINFIELDWIDTHLENGTH());
        Symbol[][] track = new Symbol[game.getMINFIELDWIDTHLENGTH()][game.getMINFIELDWIDTHLENGTH()];
        for (int i = 0; i < track.length; i++) {
            for (int j = 0; j < track[0].length; j++) {
                track[i][j] = Symbol.STREET;
            }
        }
        Player[] player = new Player[4];
        player[0] = new Player(false, "", false, (new int[] { 0, 0 }), new int[] { 0, 0 }, 0);
        player[1] = new Player(false, "", false, (new int[] { 0, 0 }), new int[] { 0, 0 }, 0);
        player[2] = new Player(false, "", false, (new int[] { 0, 0 }), new int[] { 0, 0 }, 0);
        player[3] = new Player(false, "", false, (new int[] { 0, 0 }), new int[] { 0, 0 }, 0);

        JavaFXGUI gui =
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn);
        game = new Game(track, Direction.NORTH, 0, player, gui);

        initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        initEditButtons();

    }

    /**
     * deletes a row
     *
     * @param actionEvent distinguish between deleteRowAbove and deleteRowBelow
     */
    @FXML private void deleteRow (ActionEvent actionEvent) {
        grdPn.getChildren().clear();
        grdPn.getRowConstraints().clear();
        for (int i = grdPn.getRowCount(); i < field[0].length - 1; i++) {
            RowConstraints newRowConstrains = new RowConstraints();
            newRowConstrains.setMinHeight(10.0);
            grdPn.getRowConstraints().add(newRowConstrains);
        }

        Symbol[][] track = new Symbol[grdPn.getColumnCount()][grdPn.getRowCount()];

        field = new ImageView[grdPn.getColumnCount()][grdPn.getRowCount()];

        for (int x = 0; x < track.length; x++) {
            for (int y = 0; y < track[0].length; y++) {
                if (actionEvent.getSource() == deleteRowAbove) {
                    track[x][y] = game.getGameTrack()[x][y + 1];
                } else if (actionEvent.getSource() == deleteRowBelow) {
                    track[x][y] = game.getGameTrack()[x][y];
                }
            }
        }

        JavaFXGUI gui =
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn);
        game = new Game(track, game.getGameDirection(), game.getCurrentPlayer(), game.getPlayer(),
                gui);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        initEditButtons();
    }

    /**
     * deletes a coloum
     *
     * @param actionEvent distinguish between deleteColoumLeft and deleteColoumRight
     */
    @FXML private void deleteCol (ActionEvent actionEvent) {
        grdPn.getChildren().clear();
        grdPn.getColumnConstraints().clear();
        for (int i = grdPn.getColumnCount(); i < field.length - 1; i++) {
            ColumnConstraints newColumnConstrains = new ColumnConstraints();
            newColumnConstrains.setMinWidth(10.0);
            grdPn.getColumnConstraints().add(newColumnConstrains);
        }

        Symbol[][] track = new Symbol[grdPn.getColumnCount()][grdPn.getRowCount()];

        field = new ImageView[grdPn.getColumnCount()][grdPn.getRowCount()];

        for (int x = 0; x < track.length; x++) {
            for (int y = 0; y < track[0].length; y++) {
                if (actionEvent.getSource() == deleteColumnLeft) {
                    track[x][y] = game.getGameTrack()[x + 1][y];
                } else if (actionEvent.getSource() == deleteColumnRight) {
                    track[x][y] = game.getGameTrack()[x][y];
                }
            }
        }

        JavaFXGUI gui =
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn);
        game = new Game(track, game.getGameDirection(), game.getCurrentPlayer(), game.getPlayer(),
                gui);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        initEditButtons();
    }

    /**
     * add a row
     *
     * @param actionEvent distinguish between addRowAbove and addRowBelow
     */
    @FXML private void addRow (ActionEvent actionEvent) {
        grdPn.getChildren().clear();
        grdPn.getRowConstraints().clear();
        for (int i = grdPn.getRowCount(); i < field[0].length + 1; i++) {
            RowConstraints newRowConstrains = new RowConstraints();
            newRowConstrains.setMinHeight(10.0);
            grdPn.getRowConstraints().add(newRowConstrains);
        }

        Symbol[][] track = new Symbol[grdPn.getColumnCount()][grdPn.getRowCount()];

        field = new ImageView[grdPn.getColumnCount()][grdPn.getRowCount()];

        for (int x = 0; x < track.length; x++) {
            for (int y = 0; y < track[0].length; y++) {
                if (actionEvent.getSource() == addRowAbove) {
                    if (y == 0) {
                        track[x][y] = Symbol.GRAVEL;
                    } else {
                        track[x][y] = game.getGameTrack()[x][y - 1];
                    }
                } else if (actionEvent.getSource() == addRowBelow) {
                    if (y == track[0].length - 1) {
                        track[x][y] = Symbol.GRAVEL;
                    } else {
                        track[x][y] = game.getGameTrack()[x][y];
                    }
                }
            }
        }

        JavaFXGUI gui =
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn);
        game = new Game(track, game.getGameDirection(), game.getCurrentPlayer(), game.getPlayer(),
                gui);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        initEditButtons();
    }

    /**
     * add a coloum
     *
     * @param actionEvent distinguish between addColoumLeft and addColoumRight
     */
    @FXML private void addCol (ActionEvent actionEvent) {
        grdPn.getChildren().clear();
        grdPn.getColumnConstraints().clear();
        for (int i = grdPn.getColumnCount(); i < field.length + 1; i++) {
            ColumnConstraints newColumnConstrains = new ColumnConstraints();
            newColumnConstrains.setMinWidth(10.0);
            grdPn.getColumnConstraints().add(newColumnConstrains);
        }

        Symbol[][] track = new Symbol[grdPn.getColumnCount()][grdPn.getRowCount()];

        field = new ImageView[grdPn.getColumnCount()][grdPn.getRowCount()];

        for (int x = 0; x < track.length; x++) {
            for (int y = 0; y < track[0].length; y++) {
                if (actionEvent.getSource() == addColumnLeft) {
                    if (x == 0) {
                        track[x][y] = Symbol.GRAVEL;
                    } else {
                        track[x][y] = game.getGameTrack()[x - 1][y];
                    }
                } else if (actionEvent.getSource() == addColumnRight) {
                    if (x == track.length - 1) {
                        track[x][y] = Symbol.GRAVEL;
                    } else {
                        track[x][y] = game.getGameTrack()[x][y];
                    }
                }
            }
        }

        JavaFXGUI gui =
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn);
        game = new Game(track, game.getGameDirection(), game.getCurrentPlayer(), game.getPlayer(),
                gui);

        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());

        initEditButtons();
    }

    /**
     * Reaction to the mouse click event on the GridPane.
     *
     * @param event the mouse click event that is fired when the gridpane is clicked
     */
    @FXML private void onMouseClickGridPane (MouseEvent event) {
        int     x            = -1;
        int     y            = -1;
        boolean leftClicked  = event.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = event.getButton() == MouseButton.SECONDARY;
        boolean mouseWheel   = event.getButton() == MouseButton.MIDDLE;

        //determine the imageview of the grid that contains the coordinates of
        //the mouseclick to determine the board-coordinates
        for (Node node : grdPn.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }
        int[] clickedPos = new int[] { x, y };
        if (isRace) {
            setTurn(leftClicked, clickedPos);
        } else {
            setEdit(leftClicked, rightClicked, mouseWheel, clickedPos);

        }
    }

    /**
     * used the clicked Position and sets the new current position for the player
     *
     * @param leftClicked has the player clicked with the left mouse-button
     * @param clickedPos  the clicked Position on the gridPane
     */
    @FXML private void setTurn (boolean leftClicked, int[] clickedPos) {
        boolean finish = false;
        do {
            if (!(Arrays.equals(game.getPlayer()[game.getCurrentPlayer()].getCurrent(),
                    game.getPlayer()[game.getCurrentPlayer()].getLast()))
                    || game.getPlayer()[game.getCurrentPlayer()].getCurrent()[0] != 0
                    && game.getPlayer()[game.getCurrentPlayer()].getCurrent()[1] != 0) {
                orgLblNextMovePlayer(true);
                ArrayList<int[]> futureWay = game.calculateField();
                int              hitX      = futureWay.get(futureWay.size() - 1)[0];
                int              hitY      = futureWay.get(futureWay.size() - 1)[1];
                if (!game.getPlayer()[game.getCurrentPlayer()].isAi()) {
                    hitX -= clickedPos[0];
                    hitY -= clickedPos[1];
                } else {
                    hitX -= futureWay.get(futureWay.size() - 1).clone()[0];
                    hitY -= futureWay.get(futureWay.size() - 1).clone()[1];
                }
                if (game.getPlayer()[game.getCurrentPlayer()].isAi() || (
                        game.isCoordValid(clickedPos) && !game.isCoordRival(clickedPos))) {
                    if ((hitX >= -1 && hitX <= 1 && hitY >= -1 && hitY <= 1) || ((
                            futureWay.get(futureWay.size() - 1)[0] < 0
                                    || futureWay.get(futureWay.size() - 1)[0]
                                    >= game.getGameTrack().length
                                    || futureWay.get(futureWay.size() - 1)[1] < 0
                                    || futureWay.get(futureWay.size() - 1)[1]
                                    >= game.getGameTrack()[0].length))) {
                        resetImage(futureWay.get(futureWay.size() - 1).clone());

                        if (leftClicked) {
                            //react on leftclick
                            finish = game.turn(clickedPos);
                        }
                        if (!game.getPlayer()[game.getCurrentPlayer()].isAi() && !finish) {
                            setPossibleImage();
                        }
                        if (finish) {
                            miSG.setDisable(true);
                        }
                    }
                }

            } else {
                if (leftClicked) {
                    //react on leftclick
                    game.prepRace(clickedPos);
                    if (game.getPlayer()[game.getCurrentPlayer()] == game.firstPlayer()) {
                        setPossibleImage();
                        orgLblPickStartPlayer(false);
                        orgLblNextMovePlayer(true);
                        miNG.setDisable(false);
                        miLG.setDisable(false);
                        miSG.setDisable(false);
                    }
                }
            }
        } while (game.getPlayer()[game.getCurrentPlayer()].isActive()
                && game.getPlayer()[game.getCurrentPlayer()].isAi() && !finish);
    }

    /**
     * used the clicked Position and sets the new field type on the Symbol-array
     *
     * @param leftClicked  has the player clicked with the left mouse-button
     * @param rightClicked has the player clicked with the right mouse-button
     * @param mouseWheel   has the player clicked with the mouse-wheel button
     * @param clickedPos   the clicked Position on the gridPane
     */
    @FXML private void setEdit (boolean leftClicked, boolean rightClicked, boolean mouseWheel,
            int[] clickedPos) {
        int              x            = clickedPos[0];
        int              y            = clickedPos[1];
        Direction        direct       = game.getGameDirection();
        ArrayList<int[]> oldLineCoord = game.getLineCoord();
        Symbol[][]       track        = game.getGameTrack();
        field = new ImageView[game.getGameTrack().length][game.getGameTrack()[0].length];
        if (leftClicked) {
            game.getGameTrack()[clickedPos[0]][clickedPos[1]] = Symbol.STREET;
        } else if (rightClicked) {
            if (game.getGameTrack()[clickedPos[0]][clickedPos[1]] == Symbol.LINE) {
                switch (game.getGameDirection()) {
                    case NORTH -> game.setGameDirection(Direction.SOUTH);
                    case EAST -> game.setGameDirection(Direction.WEST);
                    case SOUTH -> game.setGameDirection(Direction.NORTH);
                    case WEST -> game.setGameDirection(Direction.EAST);
                }
            } else {
                game.getGameTrack()[clickedPos[0]][clickedPos[1]] = Symbol.GRAVEL;
            }
        } else if (mouseWheel) {
            int i = x, j = y;
            if (game.getGameTrack()[clickedPos[0]][clickedPos[1]] == Symbol.LINE) {
                for (int[] positions : oldLineCoord) {
                    track[positions[0]][positions[1]] = Symbol.STREET;
                }
                if (direct == Direction.NORTH || direct == Direction.SOUTH) {
                    do {
                        j++;
                    } while (j < game.getGameTrack()[0].length
                            && game.getGameTrack()[i][j] != Symbol.GRAVEL);
                    do {
                        j--;
                        track[i][j] = Symbol.LINE;
                    } while (j < game.getGameTrack()[0].length
                            && game.getGameTrack()[i][j - 1] != Symbol.GRAVEL);
                } else if (direct == Direction.EAST || direct == Direction.WEST) {
                    do {
                        i++;
                    } while (i < game.getGameTrack().length
                            && game.getGameTrack()[i][j] != Symbol.GRAVEL);
                    do {
                        i--;
                        track[i][j] = Symbol.LINE;
                    } while (i > 0 && i < game.getGameTrack().length
                            && game.getGameTrack()[i - 1][j] != Symbol.GRAVEL);
                }
                switch (game.getGameDirection()) {
                    case NORTH -> game.setGameDirection(Direction.EAST);
                    case EAST -> game.setGameDirection(Direction.SOUTH);
                    case SOUTH -> game.setGameDirection(Direction.WEST);
                    case WEST -> game.setGameDirection(Direction.NORTH);
                }

            } else {
                ArrayList<int[]> newLineCoordX = new ArrayList<>();
                ArrayList<int[]> newLineCoordY = new ArrayList<>();
                for (int[] positions : oldLineCoord) {
                    track[positions[0]][positions[1]] = Symbol.STREET;
                }
                do {
                    j++;
                } while (j < game.getGameTrack()[0].length
                        && game.getGameTrack()[x][j] != Symbol.GRAVEL);
                do {
                    j--;
                    newLineCoordY.add(new int[] { x, j });
                } while (j < game.getGameTrack()[0].length
                        && game.getGameTrack()[x][j - 1] != Symbol.GRAVEL);

                do {
                    i++;
                } while (i < game.getGameTrack().length
                        && game.getGameTrack()[i][y] != Symbol.GRAVEL);
                do {
                    i--;
                    newLineCoordX.add(new int[] { i, y });
                } while (i > 0 && i < game.getGameTrack().length
                        && game.getGameTrack()[i - 1][y] != Symbol.GRAVEL);

                if (newLineCoordX.size() < newLineCoordY.size()) {
                    for (int[] positions : newLineCoordX) {
                        track[positions[0]][positions[1]] = Symbol.LINE;
                    }
                    game.setGameDirection(Direction.NORTH);
                } else {
                    for (int[] positions : newLineCoordY) {
                        track[positions[0]][positions[1]] = Symbol.LINE;
                    }
                    game.setGameDirection(Direction.EAST);
                }
                game = new Game(track, game.getGameDirection(), game.getCurrentPlayer(),
                        game.getPlayer(),
                        new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid,
                                imgCurrPlayer, grdPn));

                if (newLineCoordX.size() < newLineCoordY.size()) {
                    game.setLineCoord(newLineCoordX);
                } else {
                    game.setLineCoord(newLineCoordY);
                }
            }
        }
        game  = new Game(track, game.getGameDirection(), game.getCurrentPlayer(), game.getPlayer(),
                new JavaFXGUI(field, lblCurrentPlayer, lblWinner, lblTrackValid, imgCurrPlayer,
                        grdPn));
        field = initImages(grdPn, game.getGameTrack(), game.getGameDirection());
    }

    /**
     * set the images for the possible places
     */
    @FXML private void setPossibleImage () {
        orgLblOneOption(false);
        if (!(game.getPlayer()[game.getCurrentPlayer()].getCurrent()[0] == 0
                && game.getPlayer()[game.getCurrentPlayer()].getCurrent()[1] == 0)) {
            ArrayList<int[]> futureWay = game.calculateField();
            boolean          set       = false;
            int              j;
            int              length    = 1;
            while (!set) {
                int[] nextPos = futureWay.get(futureWay.size() - length).clone();
                if (length == 1 && game.isCoordValid(nextPos)) {
                    nextPos[0]--;
                    nextPos[1]--;
                }
                for (int i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (length != 1) {
                            i = 2;
                            j = 2;
                        }
                        if (!Arrays.equals(nextPos,
                                game.getPlayer()[game.getCurrentPlayer()].getCurrent())
                                && !game.isCoordRival(nextPos) && (game.isCoordValid(nextPos))) {
                            switch (game.getGameTrack()[nextPos[0]][nextPos[1]]) {
                                case STREET -> field[nextPos[0]][nextPos[1]].setImage(
                                        new Image("gui/img/StreetPossible.png"));
                                case GRAVEL -> field[nextPos[0]][nextPos[1]].setImage(
                                        new Image("gui/img/GravelPossible.png"));
                                case LINE -> {
                                    switch (game.getGameDirection()) {
                                        case NORTH -> field[nextPos[0]][nextPos[1]].setImage(
                                                new Image("gui/img/LineNPossible.png"));
                                        case EAST -> field[nextPos[0]][nextPos[1]].setImage(
                                                new Image("gui/img/LineEPossible.png"));
                                        case SOUTH -> field[nextPos[0]][nextPos[1]].setImage(
                                                new Image("gui/img/LineSPossible.png"));
                                        case WEST -> field[nextPos[0]][nextPos[1]].setImage(
                                                new Image("gui/img/LineWPossible.png"));
                                    }
                                }
                            }
                            set = true;
                        }
                        nextPos[0]++;
                    }
                    nextPos[0] = nextPos[0] - j;
                    nextPos[1]++;
                }
                length++;
            }
            if (length > 2) {
                orgLblNextMovePlayer(false);
                orgLblOneOption(true);
            }
        }
    }

    /**
     * resets the images of the field
     *
     * @param currPos the current Position
     */
    @FXML private void resetImage (int[] currPos) {
        currPos[0]--;
        currPos[1]--;
        int j;
        for (int i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (game.isCoordValid(currPos)) {
                    if (!game.isCoordRival(currPos) && !Arrays.equals(currPos,
                            game.getPlayer()[game.getCurrentPlayer()].getCurrent())) {
                        switch (game.getGameTrack()[currPos[0]][currPos[1]]) {
                            case STREET -> field[currPos[0]][currPos[1]].setImage(
                                    new Image("gui/img/Street.png"));
                            case GRAVEL -> field[currPos[0]][currPos[1]].setImage(
                                    new Image("gui/img/Gravel.png"));
                            case LINE -> {
                                switch (game.getGameDirection()) {
                                    case NORTH -> field[currPos[0]][currPos[1]].setImage(
                                            new Image("gui/img/LineN.png"));
                                    case EAST -> field[currPos[0]][currPos[1]].setImage(
                                            new Image("gui/img/LineE.png"));
                                    case SOUTH -> field[currPos[0]][currPos[1]].setImage(
                                            new Image("gui/img/LineS.png"));
                                    case WEST -> field[currPos[0]][currPos[1]].setImage(
                                            new Image("gui/img/LineW.png"));
                                }
                            }
                        }
                    }
                }
                currPos[0]++;
            }
            currPos[0] = currPos[0] - j;
            currPos[1]++;
        }
    }

    /**
     * Reaction to the mouse click event on the GridPane.
     */
    @FXML private void closeGame () {
        System.exit(0);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgLblPickStartPlayer (boolean set) {
        lblPickStartPlayer.setManaged(set);
        lblPickStartPlayer.setVisible(set);
        lblPickStartPlayer.setDisable(!set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgLblNextMovePlayer (boolean set) {
        lblNextMovePlayer.setManaged(set);
        lblNextMovePlayer.setVisible(set);
        lblNextMovePlayer.setDisable(!set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgLblOneOption (boolean set) {
        lblOneOption.setManaged(set);
        lblOneOption.setVisible(set);
        lblOneOption.setDisable(!set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxRight (boolean set) {
        VBxRight.setManaged(set);
        VBxRight.setVisible(set);
        VBxRight.setDisable(!set);
    }

    /**
     * organisats the visibility of HBx
     *
     * @param set if true its visible and usable
     */
    private void orgHBxDownGrid (boolean set) {
        HBxDownGrid.setManaged(set);
        HBxDownGrid.setVisible(set);
        HBxDownGrid.setDisable(!set);
    }

    /**
     * organisats the visibility of HBx
     *
     * @param set if true its visible and usable
     */
    private void orgHBxTopGrid (boolean set) {
        HBxTopGrid.setManaged(set);
        HBxTopGrid.setVisible(set);
        HBxTopGrid.setDisable(!set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxLeftGrid (boolean set) {
        VBxLeftGrid.setDisable(!set);
        VBxLeftGrid.setManaged(set);
        VBxLeftGrid.setVisible(set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxRightGrid (boolean set) {
        VBxRightGrid.setDisable(!set);
        VBxRightGrid.setManaged(set);
        VBxRightGrid.setVisible(set);
    }

    /**
     * organisats the visibility of HBx
     *
     * @param set if true its visible and usable
     */
    private void orgHBxLeft (boolean set) {
        HBxLeft.setDisable(!set);
        HBxLeft.setManaged(set);
        HBxLeft.setVisible(set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxLeftLoad (boolean set) {
        VBxLeftLoad.setDisable(!set);
        VBxLeftLoad.setManaged(set);
        VBxLeftLoad.setVisible(set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxLeftSave (boolean set) {
        VBxLeftSave.setDisable(!set);
        VBxLeftSave.setManaged(set);
        VBxLeftSave.setVisible(set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgVBxLeftPlayer (boolean set) {
        VBxLeftPlayer.setDisable(!set);
        VBxLeftPlayer.setManaged(set);
        VBxLeftPlayer.setVisible(set);
    }

    /**
     * organisats the visibility of VBx
     *
     * @param set if true its visible and usable
     */
    private void orgSetEditorMenu (boolean set) {
        miSE.setDisable(!set);
        miLME.setDisable(set);
        miNME.setDisable(set);
        miSME.setDisable(set);
    }
}
