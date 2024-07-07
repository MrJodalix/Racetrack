package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The logic behind the game. This class controls the movement of the players, checking for bounds
 * and connects with gui
 *
 * @author Joshua-Scott Schöttke
 */
public class Game {
    /** logic GameField */
    private final Symbol[][] gameTrack;
    /** logic gameDirection */
    private Direction gameDirection;
    /** All Players */
    private final Player[] player;
    /** current Player */
    private int currentPlayer;
    /** the coordinates of the line coord */
    private ArrayList<int[]> lineCoord = new ArrayList<>();
    /** the max field length */
    private final int MAXFIELDLENGTH = 40;
    /** the max field width */
    private final int MAXFIELDWIDTH = 20;
    /** the min field length/width */
    private final int MINFIELDWIDTHLENGTH = 10;

    /**
     * Connection to the gui.
     */
    private final GUIConnector gui;

    /** Getter/Setter */
    public Symbol[][] getGameTrack () {
        return gameTrack;
    }

    public Direction getGameDirection () {
        return gameDirection;
    }

    public Player[] getPlayer () {
        return player;
    }

    public int getCurrentPlayer () {
        return currentPlayer;
    }

    public ArrayList<int[]> getLineCoord () {
        return lineCoord;
    }

    public int getMAXFIELDLENGTH () {
        return MAXFIELDLENGTH;
    }

    public int getMAXFIELDWIDTH () {
        return MAXFIELDWIDTH;
    }

    public int getMINFIELDWIDTHLENGTH () {
        return MINFIELDWIDTHLENGTH;
    }

    public void setGameDirection (Direction gameDirection) {
        this.gameDirection = gameDirection;
    }

    public void setLineCoord (ArrayList<int[]> lineCoord) {
        this.lineCoord = lineCoord;
    }

    public void setCurrentPlayer (int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //    private ArrayList<int[]> rootOfPlayer1 = new ArrayList<>();
    //    private ArrayList<int[]> rootOfPlayer2 = new ArrayList<>();
    //    private ArrayList<int[]> rootOfPlayer3 = new ArrayList<>();
    //    private ArrayList<int[]> rootOfPlayer4 = new ArrayList<>();


    private boolean lastLap = false;

    /**
     * Test Symbols
     */
    public enum testSymbol {O, X, L}

    /**
     * Constructor for saving the game
     *
     * @param track         gamefiled
     * @param direction     drive direction
     * @param currentPlayer active player
     * @param player        players
     * @param gui           the connection to the Gui Connector
     */
    public Game (Symbol[][] track, Direction direction, int currentPlayer, Player[] player,
            GUIConnector gui) {
        this.gameTrack = track;

        this.gameDirection = direction;

        this.currentPlayer = currentPlayer;

        this.player = player;

        this.gui = gui;

        for (int x = 0; x < gameTrack.length; x++) {
            for (int y = 0; y < gameTrack[x].length; y++) {
                if (gameTrack[x][y] == Symbol.LINE) {
                    int[] line = new int[] { x, y };
                    lineCoord.add(line);
                }
            }
        }
    }

    /**
     * Test-Constructor
     *
     * @param testTrack     gamefiled
     * @param direction     drive direction
     * @param currentPlayer active player
     * @param player        players
     * @param gui           the connection to the Gui Connector
     */
    public Game (testSymbol[][] testTrack, Direction direction, int currentPlayer, Player[] player,
            GUIConnector gui) {
        Symbol[][] track = new Symbol[testTrack.length][testTrack[0].length];
        for (int x = 0; x < testTrack.length; x++) {
            for (int y = 0; y < testTrack[0].length; y++) {
                if (testTrack[x][y] == testSymbol.X) {
                    track[x][y] = Symbol.GRAVEL;
                } else if (testTrack[x][y] == testSymbol.O) {
                    track[x][y] = Symbol.STREET;
                } else {
                    track[x][y] = Symbol.LINE;
                }
            }
        }
        this.gameTrack = track;

        this.gameDirection = direction;

        this.currentPlayer = currentPlayer;

        this.player = player;

        this.gui = gui;

        for (int x = 0; x < gameTrack.length; x++) {
            for (int y = 0; y < gameTrack[x].length; y++) {
                if (gameTrack[x][y] == Symbol.LINE) {
                    int[] line = new int[] { x, y };
                    lineCoord.add(line);
                }
            }
        }
    }

    /**
     * Constructor for initialize and loading the game
     *
     * @param filename name of file
     * @param gui      UI
     */
    public Game (String filename, GUIConnector gui) {
        int[][] dataTrack = new int[][]{};
        int direction = 0;
        Player[] gamer = new Player[]{};
        int current = 0;
        GameData gameData = new GameData(dataTrack, direction, gamer, current);
        gameData = gameData.getGameData(filename);

        this.gameTrack = new Symbol[gameData.getTrack().length][gameData.getTrack()[0].length];

        for (int x = 0; x < gameData.getTrack().length; x++) {
            for (int y = 0; y < gameData.getTrack()[x].length; y++) {
                if (gameData.getTrack()[x][y] == 0) {
                    this.gameTrack[x][y] = Symbol.GRAVEL;
                } else if (gameData.getTrack()[x][y] == 1) {
                    this.gameTrack[x][y] = Symbol.STREET;
                } else if (gameData.getTrack()[x][y] == 2) {
                    this.gameTrack[x][y] = Symbol.LINE;
                    int[] line = new int[] { x, y };
                    lineCoord.add(line);
                }
            }
        }

        switch (gameData.getDirection()) {
            case 0 -> this.gameDirection = Direction.NORTH;
            case 1 -> this.gameDirection = Direction.EAST;
            case 2 -> this.gameDirection = Direction.SOUTH;
            case 3 -> this.gameDirection = Direction.WEST;
        }

        this.player = gameData.getPlayer();

        this.currentPlayer = gameData.getCurrentPlayer();

        this.gui = gui;
    }

    /**
     * checks if the track is valid
     *
     * @return true if valid
     */
    public int isTrackValid () {
        int valid = ValidTrack.OK.ordinal();

        //uberprueft ob es eine Ziellinie gibt
        if (lineCoord.size() <= 0) {
            valid = ValidTrack.LINE_NOT_VALID.ordinal();
        } else {
            //max length, max width, min width and min length
            if (gameTrack.length < MINFIELDWIDTHLENGTH) {
                valid = ValidTrack.TO_SHORT.ordinal();
            } else if (gameTrack.length > MAXFIELDLENGTH) {
                valid = ValidTrack.TO_LONG.ordinal();
            } else if (gameTrack[0].length < MINFIELDWIDTHLENGTH) {
                valid = ValidTrack.TO_LOW.ordinal();
            } else if (gameTrack[0].length > MAXFIELDWIDTH) {
                valid = ValidTrack.TO_HIGH.ordinal();
            } else {
                //ueberprueft auf unterschiedliche breiten
                for (int i = 0; i < gameTrack.length && valid == 0; i++) {
                    if (gameTrack[i] == null || gameTrack[i].length != gameTrack[0].length) {
                        valid = ValidTrack.TRACK_NOT_QUADRATIC.ordinal();
                    }
                }
                if (valid == 0) {//ueberprueft ob es eine zweite Ziellinie gibt
                    // (North or South) or  (West or East)
                    int directValue = (gameDirection.ordinal() + 1) % 2;
                    // the other 90° direction
                    int noDirectValue = gameDirection.ordinal() % 2;
                    int xOrY          = lineCoord.get(0).clone()[directValue];
                    int yOrX          = lineCoord.get(0).clone()[noDirectValue];
                    for (int i = 0; i < lineCoord.size() && valid == 0; i++) {
                        if (lineCoord.get(i)[directValue] != xOrY) {
                            valid = ValidTrack.SECOND_LINE.ordinal();
                        } else if (lineCoord.get(i)[noDirectValue] == yOrX) {
                            yOrX++;
                        } else {
                            valid = ValidTrack.SECOND_LINE.ordinal();
                        }
                    }

                    if (valid == 0) {
                        //uberprueft ob Ziellinie auf Spielfeldrand oder Kies trifft
                        int[] first = lineCoord.get(0).clone();
                        int[] last  = lineCoord.get(lineCoord.size() - 1).clone();
                        if (directValue == 0) {

                            if (!(((first[1] - 1 < 0) || (gameTrack[first[0]][first[1] - 1]
                                    == Symbol.GRAVEL)) && ((last[1] + 1 > MAXFIELDLENGTH) || (
                                    gameTrack[last[0]][last[1] + 1] == Symbol.GRAVEL)))) {
                                valid = ValidTrack.LINE_NOT_VALID.ordinal();
                            }
                        } else {
                            if (!(((first[0] - 1 < 0) || (gameTrack[first[0] - 1][first[1]]
                                    == Symbol.GRAVEL)) && ((last[0] + 1 > MAXFIELDLENGTH) || (
                                    gameTrack[last[0] + 1][last[1]] == Symbol.GRAVEL)))) {
                                valid = ValidTrack.LINE_NOT_VALID.ordinal();
                            }
                        }
                        if (valid == 0) {
                            //left-hand Algorithm

                            boolean   isCircle  = false;
                            int[]     copyFirst = first.clone();
                            Direction checkRun  = gameDirection;

                            while (!isCircle && valid == 0) {
                                if (gameTrack[copyFirst[0]][copyFirst[1] - 1] != Symbol.GRAVEL
                                        && checkRun == Direction.NORTH) {
                                    if (gameTrack[copyFirst[0] - 1][copyFirst[1]]
                                            == Symbol.GRAVEL) {
                                        copyFirst[1]--;
                                    } else {
                                        copyFirst[0]--;
                                        checkRun = Direction.WEST;
                                    }
                                } else if (
                                        gameTrack[copyFirst[0] + 1][copyFirst[1]] != Symbol.GRAVEL
                                                && checkRun == Direction.EAST) {
                                    if (gameTrack[copyFirst[0]][copyFirst[1] - 1]
                                            == Symbol.GRAVEL) {
                                        copyFirst[0]++;
                                    } else {
                                        copyFirst[1]--;
                                        checkRun = Direction.NORTH;
                                    }
                                } else if (
                                        gameTrack[copyFirst[0]][copyFirst[1] + 1] != Symbol.GRAVEL
                                                && checkRun == Direction.SOUTH) {
                                    if (gameTrack[copyFirst[0] + 1][copyFirst[1]]
                                            == Symbol.GRAVEL) {
                                        copyFirst[1]++;
                                    } else {
                                        copyFirst[0]++;
                                        checkRun = Direction.EAST;
                                    }
                                } else if (
                                        gameTrack[copyFirst[0] - 1][copyFirst[1]] != Symbol.GRAVEL
                                                && checkRun == Direction.WEST) {
                                    if (gameTrack[copyFirst[0]][copyFirst[1] + 1]
                                            == Symbol.GRAVEL) {
                                        copyFirst[0]--;
                                    } else {
                                        copyFirst[1]++;
                                        checkRun = Direction.SOUTH;
                                    }
                                } else {
                                    valid = ValidTrack.NOT_A_CIRCLE.ordinal();
                                }
                                if (isCoordValid(copyFirst)) {
                                    if (gameTrack[copyFirst[0]][copyFirst[1]] == Symbol.LINE) {
                                        if (Arrays.equals(copyFirst, first)) {
                                            isCircle = true;
                                        } else {
                                            valid = ValidTrack.NOT_A_CIRCLE.ordinal();
                                        }
                                    }
                                } else {
                                    valid = ValidTrack.NOT_A_CIRCLE.ordinal();
                                }
                            }

                            int[] copyLast   = last.clone();
                            int   validRight = 0;
                            // right-hand Algorithm
                            if (!isCircle) {
                                while (!isCircle && validRight == 0) {
                                    if (gameTrack[copyLast[0]][copyLast[1] - 1] != Symbol.GRAVEL
                                            && checkRun == Direction.NORTH) {
                                        if (gameTrack[copyLast[0] + 1][copyLast[1]]
                                                == Symbol.GRAVEL) {
                                            copyLast[1]--;
                                        } else {
                                            copyLast[0]++;
                                            checkRun = Direction.EAST;
                                        }
                                    } else if (
                                            gameTrack[copyLast[0] + 1][copyLast[1]] != Symbol.GRAVEL
                                                    && checkRun == Direction.EAST) {
                                        if (gameTrack[copyLast[0]][copyLast[1] + 1]
                                                == Symbol.GRAVEL) {
                                            copyLast[0]++;
                                        } else {
                                            copyLast[1]++;
                                            checkRun = Direction.SOUTH;
                                        }
                                    } else if (
                                            gameTrack[copyLast[0]][copyLast[1] + 1] != Symbol.GRAVEL
                                                    && checkRun == Direction.SOUTH) {
                                        if (gameTrack[copyLast[0] - 1][copyLast[1]]
                                                == Symbol.GRAVEL) {
                                            copyLast[1]++;
                                        } else {
                                            copyLast[0]--;
                                            checkRun = Direction.WEST;
                                        }
                                    } else if (
                                            gameTrack[copyLast[0] - 1][copyLast[1]] != Symbol.GRAVEL
                                                    && checkRun == Direction.WEST) {
                                        if (gameTrack[copyLast[0]][copyLast[1] - 1]
                                                == Symbol.GRAVEL) {
                                            copyLast[0]--;
                                        } else {
                                            copyLast[1]--;
                                            checkRun = Direction.NORTH;
                                        }
                                    } else {
                                        validRight = ValidTrack.NOT_A_CIRCLE.ordinal();
                                    }
                                    if (isCoordValid(copyLast)) {
                                        if (gameTrack[copyLast[0]][copyLast[1]] == Symbol.LINE) {
                                            if (Arrays.equals(copyLast, last)) {
                                                isCircle   = true;
                                                validRight = ValidTrack.OK.ordinal();
                                            } else {
                                                validRight = ValidTrack.NOT_A_CIRCLE.ordinal();
                                            }
                                        }
                                    } else {
                                        validRight = ValidTrack.NOT_A_CIRCLE.ordinal();
                                    }
                                }
                            }
                            if (validRight == 0) {
                                valid = ValidTrack.OK.ordinal();
                            }
                        }
                    }
                }
            }
        }
        ValidTrack error = ValidTrack.values()[valid];
        gui.setErrorString(error);
        return valid;
    }

    /**
     * save the game data to a json
     *
     * @param filename the name under saving
     */
    public void saveGame (String filename) {
        int[][] dataTrack = new int[][]{};
        int direction = 0;
        Player[] gamer = new Player[]{};
        int current = 0;
        GameData gameData = new GameData(dataTrack, direction, gamer, current);
        gameData.toGameData(gameTrack, gameDirection, player, currentPlayer, filename);
    }

    /**
     * used prepRaceSetImage for a loading game
     *
     * @param coord the current coord of a player
     */
    public void prepRace (int[] coord) {
        prepRaceSetImage(coord);
        nextPlayer();
        while (player[currentPlayer].isAi()
                && player[currentPlayer].getCurrent()[0] != lastPlayer().getCurrent()[0]
                && player[currentPlayer].getCurrent()[1] != lastPlayer().getCurrent()[1]) {
            prepRaceSetImage(coord);
            nextPlayer();
        }
    }

    /**
     * preparated the Race and placed all players of a loaded file
     * @param coord the coordinate of the current position of the current player
     */
    public void prepLoadGame (int playIdx, int[] coord) {
        gui.displaySymbol(player[playIdx].getCurrent(), coord, playIdx,
                gameTrack[player[playIdx].getLast()[0]][player[playIdx].getLast()[1]],
                gameDirection);
        gui.setCurrentPlayer(player[currentPlayer].getName(), currentPlayer);
    }

    /**
     * preparated the Race and placed the current Player on the field(street)
     *
     * @param coord the coordinate for setting the current Image
     */
    public void prepRaceSetImage (int[] coord) {
        if (player[currentPlayer].isActive() && !player[currentPlayer].isAi()) {
            int[] last    = player[currentPlayer].getLast();
            int[] current = player[currentPlayer].getCurrent();
            if (!isCoordValid(coord) || !(gameTrack[coord[0]][coord[1]] == Symbol.STREET)
                    || isCoordRival(coord)) {
                currentPlayer = (currentPlayer + 3) % 4;
            } else {
                player[currentPlayer].setLast(coord);
                player[currentPlayer].setCurrent(coord);
                gui.displaySymbol(current, coord, currentPlayer, gameTrack[last[0]][last[1]],
                        gameDirection);
            }
        } else if (player[currentPlayer].isActive()) {
            int   posX = lineCoord.get(0).clone()[0];
            int   posY = lineCoord.get(0).clone()[1];
            int[] pos  = player[currentPlayer].getCurrent().clone();

            while (Arrays.equals(pos, player[currentPlayer].getCurrent())) {
                switch (gameDirection) {
                    case NORTH -> posY++;
                    case EAST -> posX--;
                    case SOUTH -> posY--;
                    case WEST -> posX++;
                }
                if (gameTrack[posX][posY] == Symbol.STREET) {
                    boolean notSet = true;
                    for (int i = 0; i < player.length - 1; i++) {
                        if (i != currentPlayer && player[i].getCurrent()[0] == posX
                                && player[i].getCurrent()[1] == posY) {
                            notSet = false;
                        }
                    }
                    if (notSet) {
                        pos[0] = posX;
                        pos[1] = posY;
                    } else if (posX == 0 || posY == 0 || posX == gameTrack.length - 1
                            || posY == gameTrack[0].length - 1) {
                        switch (gameDirection) {
                            case NORTH -> {
                                posY = lineCoord.get(0)[1];
                                posX++;
                            }
                            case EAST -> {
                                posX = lineCoord.get(0)[0];
                                posY++;
                            }
                            case SOUTH -> {
                                posY = lineCoord.get(0)[1];
                                posX--;
                            }
                            case WEST -> {
                                posX = lineCoord.get(0)[0];
                                posY--;
                            }
                        }
                    }
                }
            }
            player[currentPlayer].setLast(pos);
            player[currentPlayer].setCurrent(pos);
            gui.displaySymbol(player[currentPlayer].getCurrent(), pos, currentPlayer,
                    gameTrack[player[currentPlayer].getLast()[0]][player[currentPlayer].getLast()[1]],
                    gameDirection);
        }
    }

    /**
     * Handles the turn of a player. It identified the player as a human or an AI and orders the
     * relevant turn handel. Then the current player is changed, so that it is the turn of the next
     * player. Finally, the method checks if through this turn a player has won the game.
     *
     * @param coord coordinate in the field at which the player wants to place his/her symbol
     */
    public boolean turn (int[] coord) {
        boolean finish = false;
        if (player[currentPlayer].isActive()) {
            if (player[currentPlayer].isAi()) {
                aiTurn();
            } else {
                playerTurn(coord);
            }
        }

        if (player[currentPlayer].getLap() == 2 || lastLap) {
            lastLap = true;
            if (player[currentPlayer] == lastPlayer()) {
                handelEndOfGame();
                finish = true;
            } else {
                nextPlayer();
            }
        } else {
            nextPlayer();
        }
        return finish;
    }

    /**
     * sets the lastPlayer
     *
     * @return the last Player
     */
    public Player firstPlayer () {
        Player firstPlayer = player[3];
        if (player[0].isActive()) {
            firstPlayer = player[0];
        } else if (player[1].isActive()) {
            firstPlayer = player[1];
        } else if (player[2].isActive()) {
            firstPlayer = player[2];
        }
        return firstPlayer;
    }

    /**
     * sets the lastPlayer
     *
     * @return the last Player
     */
    public Player lastPlayer () {
        Player lastPlayer = player[0];
        if (player[3].isActive()) {
            lastPlayer = player[3];
        } else if (player[2].isActive()) {
            lastPlayer = player[2];
        } else if (player[1].isActive()) {
            lastPlayer = player[1];
        }
        return lastPlayer;
    }

    /**
     * determine the next active player and calculated the next field-area initiate the update of
     * the gui for the current player Symbol
     */
    private void nextPlayer () {
        do {
            currentPlayer = (currentPlayer + 1) % 4;
        } while (!player[currentPlayer].isActive());
        gui.setCurrentPlayer(player[currentPlayer].getName(), currentPlayer);
    }

    /**
     * rules the human turn.
     *
     * @param coord the coordinate in the gamefield
     */
    private void playerTurn (int[] coord) {
        ArrayList<int[]> futureWay = calculateField();

        int xFuture = coord[0] - futureWay.get(futureWay.size() - 1)[0];
        int yFuture = coord[1] - futureWay.get(futureWay.size() - 1)[1];

        if (isCoordValid(coord)) {
            if ((xFuture <= 1) && (xFuture >= -1) && (yFuture <= 1) && (yFuture >= -1)) {
                setSymbol(coord);
            } else {
                ArrayList<int[]> way = usedWay(coord);
                boolean          set = false;
                for (int i = 0; i < way.size() && !set; i++) {
                    if ((gameTrack[way.get(i)[0]][way.get(i)[1]] == Symbol.GRAVEL) || (
                            way.get(i)[0] < 0 || way.get(i)[0] > gameTrack.length
                                    || way.get(i)[1] < 0 || way.get(i)[1] > gameTrack.length)) {
                        gui.displaySymbol(way.get(0), way.get(i - 1), currentPlayer,
                                gameTrack[way.get(0)[0]][way.get(0)[1]], gameDirection);
                        player[currentPlayer].setLast(way.get(i - 1));
                        player[currentPlayer].setCurrent(way.get(i - 1));
                        set = true;
                    }
                }
            }
        } else {
            currentPlayer = (currentPlayer + 3) % 4;
        }
    }

    /**
     * sets the symbol of the current player and connects with the gui
     *
     * @param coord the current position
     */
    private void setSymbol (int[] coord) {
        boolean          setSymbol = false;
        ArrayList<int[]> way       = usedWay(coord);

        for (int i = 0; i < way.size() && !setSymbol; i++) {
            if (gameTrack[way.get(i)[0]][way.get(i)[1]] == Symbol.GRAVEL) {
                coord[0] = way.get(i - 1)[0];
                coord[1] = way.get(i - 1)[1];
                for (int j = 0; j < player.length; j++) {
                    if (j != currentPlayer && !Arrays.equals(player[j].getCurrent(), coord)) {
                        if (i - 1 >= 0) {
                            coord[0] = way.get(i - 1)[0];
                            coord[1] = way.get(i - 1)[1];
                        }
                    }
                }
                gui.displaySymbol(way.get(0), coord, currentPlayer,
                        gameTrack[way.get(0)[0]][way.get(0)[1]], gameDirection);
                player[currentPlayer].setLast(coord);
                player[currentPlayer].setCurrent(coord);
                setSymbol = true;
            }
        }

        if (!setSymbol) {
            if (gameTrack[coord[0]][coord[1]] != Symbol.GRAVEL) {
                gui.displaySymbol(way.get(0), coord, currentPlayer,
                        gameTrack[way.get(0)[0]][way.get(0)[1]], gameDirection);
                for (int i = 0; i < way.size() - 1; i++) {
                    if (gameTrack[way.get(i)[0]][way.get(i)[1]] == Symbol.LINE) {
                        addLap();
                    }
                }
                player[currentPlayer].setLast(player[currentPlayer].getCurrent());
                player[currentPlayer].setCurrent(coord);
            } else {
                gui.displaySymbol(way.get(0), way.get(way.size() - 1), currentPlayer,
                        gameTrack[way.get(0)[0]][way.get(0)[1]], gameDirection);
                player[currentPlayer].setLast(way.get(way.size() - 1));
                player[currentPlayer].setCurrent(way.get(way.size() - 1));
            }
        }
    }

    /**
     * add or decries a lap to the current Player
     */
    private void addLap () {
        switch (gameDirection) {
            case NORTH -> {
                if (player[currentPlayer].getLast()[1] - player[currentPlayer].getCurrent()[1]
                        > 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() + 1);
                } else if (
                        player[currentPlayer].getLast()[1] - player[currentPlayer].getCurrent()[1]
                                < 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() - 1);
                }
            }
            case EAST -> {
                if (player[currentPlayer].getLast()[0] - player[currentPlayer].getCurrent()[0]
                        < 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() + 1);
                } else if (
                        player[currentPlayer].getLast()[0] - player[currentPlayer].getCurrent()[0]
                                > 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() - 1);
                }
            }
            case SOUTH -> {
                if (player[currentPlayer].getLast()[1] - player[currentPlayer].getCurrent()[1]
                        < 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() + 1);
                } else if (
                        player[currentPlayer].getLast()[1] - player[currentPlayer].getCurrent()[1]
                                > 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() - 1);
                }
            }
            case WEST -> {
                if (player[currentPlayer].getLast()[0] - player[currentPlayer].getCurrent()[0]
                        > 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() + 1);
                } else if (
                        player[currentPlayer].getLast()[0] - player[currentPlayer].getCurrent()[0]
                                < 0) {
                    player[currentPlayer].setLap(player[currentPlayer].getLap() - 1);
                }
            }
        }
    }

    /**
     * calculates the way was chosen
     *
     * @param goal the goal position of the turn
     *
     * @return the list of all positions of the way
     */
    private ArrayList<int[]> usedWay (int[] goal) {
        ArrayList<int[]> way   = new ArrayList<>();
        int              xCurr = player[currentPlayer].getCurrent()[0];
        int              yCurr = player[currentPlayer].getCurrent()[1];
        float            m;
        float            dx    = (xCurr - goal[0]);
        float            dy    = (yCurr - goal[1]);
        int              signX = 1;
        int              signY = 1;

        if (xCurr > goal[0]) {
            signX = -1;
        }
        if (yCurr > goal[1]) {
            signY = -1;
        }
        //gerade bewegung über das Feld
        if (dx == 0 || dy == 0) {
            if (dx == 0 && dy == 0) {
                way.add(player[currentPlayer].getCurrent());
            } else if (dx == 0) {
                way.addAll(straightPath(yCurr, goal[1], goal[0], signY, dx));
            } else {
                way.addAll(straightPath(xCurr, goal[0], goal[1], signX, dx));
            }
            //diagonale Bewegung über das Feld
        } else {
            m = Math.abs((dy) / (dx));
            if (m <= 1) {
                way.addAll(diaPath(dx, m, xCurr, yCurr, signX, signY));
            } else {
                way.addAll(diaPath(dy, m, xCurr, yCurr, signX, signY));
            }
        }

        return way;
    }

    /**
     * calculated the Field where the player can goto
     */
    public ArrayList<int[]> calculateField () {
        ArrayList<int[]> way       = new ArrayList<>();
        ArrayList<int[]> futureWay = new ArrayList<>();
        int              xCurr     = player[currentPlayer].getCurrent()[0];
        int              yCurr     = player[currentPlayer].getCurrent()[1];
        int              xLast     = player[currentPlayer].getLast()[0];
        int              yLast     = player[currentPlayer].getLast()[1];
        float            m;
        float            dx        = (xLast - xCurr);
        float            dy        = (yLast - yCurr);
        int              signX     = 1;
        int              signY     = 1;

        if (xLast > xCurr) {
            signX = -1;
        }
        if (yLast > yCurr) {
            signY = -1;
        }
        //gerade bewegung über das Feld
        if (dx == 0 || dy == 0) {
            if (dx == 0 && dy == 0) {
                futureWay.add(player[currentPlayer].getCurrent());
            } else if (dx == 0) {
                way.addAll(straightPath(yLast, yCurr, xCurr, signY, dx));
                yLast = yCurr;
                if (signY > 0) {
                    yCurr = way.get(way.size() - 1)[1] + Math.abs(
                            way.get(0)[1] - way.get(way.size() - 1)[1]);
                } else {
                    yCurr = way.get(way.size() - 1)[1] - Math.abs(
                            way.get(0)[1] - way.get(way.size() - 1)[1]);
                }
                futureWay.addAll(straightPath(yLast, yCurr, xCurr, signY, dx));
            } else {
                way.addAll(straightPath(xLast, xCurr, yCurr, signX, dx));
                xLast = xCurr;
                if (signX > 0) {
                    xCurr = way.get(way.size() - 1)[0] + Math.abs(
                            way.get(0)[0] - way.get(way.size() - 1)[0]);
                } else {
                    xCurr = way.get(way.size() - 1)[0] - Math.abs(
                            way.get(0)[0] - way.get(way.size() - 1)[0]);
                }
                futureWay.addAll(straightPath(xLast, xCurr, yCurr, signX, dx));
            }
            //diagonale Bewegung über das Feld
        } else {
            m = Math.abs((dy) / (dx));
            if (m <= 1) {
                way.addAll(diaPath(dx, m, xLast, yLast, signX, signY));
                xLast = way.get(way.size() - 1)[0];
                yLast = way.get(way.size() - 1)[1];
                futureWay.addAll(diaPath(dx, m, xLast, yLast, signX, signY));
            } else {
                way.addAll(diaPath(dy, m, xLast, yLast, signX, signY));
                xLast = way.get(way.size() - 1)[0];
                yLast = way.get(way.size() - 1)[1];
                futureWay.addAll(diaPath(dy, m, xLast, yLast, signX, signY));
            }
        }
        return futureWay;
    }

    /**
     * helping methode calculated the straight path of the player
     *
     * @param xyLast    the old coordinate
     * @param xyCurrent the current coordinate
     * @param xy        the other part of the coordinate
     * @param directXY  for the direction
     * @param XorY      declares the line for calculation
     *
     * @return the future path
     */
    private ArrayList<int[]> straightPath (int xyLast, int xyCurrent, int xy, int directXY,
            float XorY) {
        ArrayList<int[]> way = new ArrayList<>();
        if (XorY == 0.0) {
            if (directXY > 0) {
                for (int i = xyLast; i <= xyCurrent; i += directXY) {
                    int[] pieceOfWay = new int[] { xy, i };
                    way.add(pieceOfWay);
                }
            } else {
                for (int i = xyLast; i >= xyCurrent; i += directXY) {
                    int[] pieceOfWay = new int[] { xy, i };
                    way.add(pieceOfWay);
                }
            }
        } else {
            if (directXY > 0) {
                for (int i = xyLast; i <= xyCurrent; i += directXY) {
                    int[] pieceOfWay = new int[] { i, xy };
                    way.add(pieceOfWay);
                }
            } else {
                for (int i = xyLast; i >= xyCurrent; i += directXY) {
                    int[] pieceOfWay = new int[] { i, xy };
                    way.add(pieceOfWay);
                }
            }
        }
        return way;
    }

    /**
     * helping methode calculated the diagonal path of the player
     *
     * @param d     the delta of x or y
     * @param m     the division of delta y and delta x
     * @param x2    the old x-coordinate
     * @param y2    the old y-coordinate
     * @param signX the direction of the x-axis. 1 positiv (right). -1 negativ (left)
     * @param signY the direction of the y-axis. 1 positiv (down). -1 negativ (up)
     *
     * @return the future path
     */
    private ArrayList<int[]> diaPath (float d, float m, int x2, int y2, int signX, int signY) {
        ArrayList<int[]> way = new ArrayList<>();
        int              x, y;

        for (int i = 0; i <= Math.abs(d); i++) {
            if (m < 1) {
                x = Math.abs(x2 + i * signX);
                y = (int) Math.abs((float) y2 + (float) i * m * (float) signY);
            } else {
                x = (int) Math.abs((float) x2 + (float) i / m * (float) signX);
                y = Math.abs(y2 + i * signY);
            }
            int[] pieceOfWay = new int[] { x, y };
            way.add(pieceOfWay);
        }

        return way;
    }

    /**
     * rules the ai turn
     */
    private void aiTurn () {
        ArrayList<int[]> futureWay = calculateField();
        boolean          clockwise = clockwise();
        int              quadrant  = whichQuadrant();

        int[] coord = futureWay.get(futureWay.size() - 1).clone();

        int xFuture = coord[0] - futureWay.get(futureWay.size() - 1)[0];
        int yFuture = coord[1] - futureWay.get(futureWay.size() - 1)[1];

        if (isCoordValid(coord)) {
            if ((xFuture <= 1) && (xFuture >= -1) && (yFuture <= 1) && (yFuture >= -1)) {
                if (clockwise) {
                    turnClockwise(quadrant, coord);
                } else {
                    turnCounterClockwise(quadrant, coord);
                }
                setSymbol(coord);
            }
        } else {
            boolean set = false;
            for (int i = 0; i < futureWay.size() && !set; i++) {
                if (gameTrack[futureWay.get(i)[0]][futureWay.get(i)[1]] == Symbol.GRAVEL) {
                    gui.displaySymbol(futureWay.get(0), futureWay.get(i - 1), currentPlayer,
                            gameTrack[futureWay.get(0)[0]][futureWay.get(0)[1]], gameDirection);

                    player[currentPlayer].setLast(futureWay.get(i - 1));
                    player[currentPlayer].setCurrent(futureWay.get(i - 1));
                    set = true;
                }
            }
            if (!set) {
                if (isCoordValid(futureWay.get(futureWay.size() - 1)) || !isCoordRival(
                        futureWay.get(futureWay.size() - 1))) {
                    gui.displaySymbol(futureWay.get(0), futureWay.get(1), currentPlayer,
                            gameTrack[futureWay.get(0)[0]][futureWay.get(0)[1]], gameDirection);
                    player[currentPlayer].setLast(player[currentPlayer].getCurrent());
                    player[currentPlayer].setCurrent(coord);
                }
            }
        }
    }

    /**
     * Calculates the new Position of the AI in clockwise turning race
     *
     * @param quadrant the quadrant of the field
     * @param coord    the next possible position
     */
    private void turnClockwise (int quadrant, int[] coord) {
        int[] dividCoord = new int[] { gameTrack.length / 2, gameTrack[0].length / 2 };
        switch (quadrant) {
            case 0 -> {
                if (gameTrack[dividCoord[0]][coord[1]] != Symbol.GRAVEL) {
                    coord[0]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]--;
                    }
                } else {
                    coord[1]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]++;
                    }
                }
            }
            case 1 -> {
                if (gameTrack[coord[0]][dividCoord[1]] != Symbol.GRAVEL) {
                    coord[1]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]--;
                    }
                } else {
                    coord[0]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]++;
                    }
                }
            }
            case 2 -> {
                if (gameTrack[dividCoord[0]][coord[1]] != Symbol.GRAVEL) {
                    coord[0]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]++;
                    }
                } else {
                    coord[1]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]++;
                    }
                }
            }
            case 3 -> {
                if (gameTrack[coord[0]][dividCoord[1]] != Symbol.GRAVEL) {
                    coord[1]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]++;
                    }
                } else {
                    coord[0]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]++;
                    }
                }
            }
        }
    }

    /**
     * Calculates the new Position of the AI in counterclockwise turning race
     *
     * @param quadrant the quadrant of the field
     * @param coord    the next possible position
     */
    private void turnCounterClockwise (int quadrant, int[] coord) {
        int[] dividCoord = new int[] { gameTrack.length / 2, gameTrack[0].length / 2 };
        switch (quadrant) {
            case 0 -> {
                if (gameTrack[player[currentPlayer].getCurrent()[0]][dividCoord[1]]
                        != Symbol.GRAVEL) {
                    coord[1]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]--;
                    }
                } else {
                    coord[0]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]++;
                    }
                }
            }
            case 1 -> {
                if (gameTrack[dividCoord[0]][player[currentPlayer].getCurrent()[1]]
                        != Symbol.GRAVEL) {
                    coord[0]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]++;
                    }
                } else {
                    coord[1]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]++;
                    }
                }
            }
            case 2 -> {
                if (gameTrack[player[currentPlayer].getCurrent()[0]][dividCoord[1]]
                        != Symbol.GRAVEL) {
                    coord[1]--;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]++;
                    }
                } else {
                    coord[0]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]--;
                    }
                }
            }
            case 3 -> {
                if (gameTrack[dividCoord[0]][player[currentPlayer].getCurrent()[1]]
                        != Symbol.GRAVEL) {
                    coord[0]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[0]--;
                    }
                } else {
                    coord[1]++;
                    if (!isCoordValid(coord) || isCoordRival(coord)) {
                        coord[1]--;
                    }
                }
            }
        }
    }

    /**
     * The gamefield is divided in four quadrants. Above-left  is the first  (0) Above-right is the
     * second (1) Below-right is the third  (2) Below-left  is the forth  (3)
     *
     * @return the int value of the quadrant where the player is.
     */
    private int whichQuadrant () {
        int quadrant = 0;
        if (player[currentPlayer].getCurrent()[0] >= gameTrack.length / 2.0
                && player[currentPlayer].getCurrent()[1] < gameTrack[0].length / 2.0) {
            quadrant = 1;
        } else if (player[currentPlayer].getCurrent()[0] >= gameTrack.length / 2.0
                && player[currentPlayer].getCurrent()[1] >= gameTrack[0].length / 2.0) {
            quadrant = 2;
        } else if (player[currentPlayer].getCurrent()[0] < gameTrack.length / 2.0
                && player[currentPlayer].getCurrent()[1] >= gameTrack[0].length / 2.0) {
            quadrant = 3;
        }
        return quadrant;
    }

    /**
     * Determines whether the route must be driven clockwise
     *
     * @return the boolean value of direction
     */
    private boolean clockwise () {
        boolean clockwise = true;
        if (gameDirection == Direction.EAST) {
            if (lineCoord.get(0)[1] >= gameTrack[0].length / 2) {
                clockwise = false;
            }
        } else if (gameDirection == Direction.WEST) {
            if (lineCoord.get(0)[1] < gameTrack[0].length / 2) {
                clockwise = false;
            }
        } else if (gameDirection == Direction.NORTH) {
            if (lineCoord.get(0)[0] >= gameTrack.length / 2) {
                clockwise = false;
            }
        } else if (gameDirection == Direction.SOUTH) {
            if (lineCoord.get(0)[0] < gameTrack.length / 2) {
                clockwise = false;
            }
        }
        return clockwise;
    }

    /**
     * test the coordinated
     *
     * @param coord coordinate in the gamefield
     *
     * @return true if valid
     */
    public boolean isCoordValid (int[] coord) {
        return (coord[0] >= 0 && coord[0] < gameTrack.length) && (coord[1] >= 0
                && coord[1] < gameTrack[0].length);
    }

    /**
     * test the coordinated of other player
     *
     * @param coord coordinate in the gamefield
     *
     * @return true if there is a rival
     */
    public boolean isCoordRival (int[] coord) {
        boolean valid = false;
        for (int i = 0; i < player.length; i++) {
            if (i != currentPlayer && Arrays.equals(coord, player[i].getCurrent())) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * calculates the winner of the Race
     *
     * @return teh index of the winner
     */
    public int whichOneIsWinner () {
        int   winnerInt   = 0;
        int[] lastTurnLen = { 0, 0, 0, 0 };
        for (int i = 0; i < player.length; i++) {
            if (player[i].getLap() >= 2) {
                switch (gameDirection) {
                    case NORTH -> lastTurnLen[i] = lineCoord.get(0)[1] - player[i].getCurrent()[1];
                    case EAST -> lastTurnLen[i] = player[i].getCurrent()[0] - lineCoord.get(0)[0];
                    case SOUTH -> lastTurnLen[i] = player[i].getCurrent()[1] - lineCoord.get(0)[1];
                    case WEST -> lastTurnLen[i] = lineCoord.get(0)[0] - player[i].getCurrent()[0];
                }
            }
        }
        if (lastTurnLen[0] < lastTurnLen[1]) {
            winnerInt = 1;
        }
        if (lastTurnLen[1] < lastTurnLen[2]) {
            winnerInt = 2;
        }
        if (lastTurnLen[2] < lastTurnLen[3]) {
            winnerInt = 3;
        }
        return winnerInt;
    }

    /**
     * handle the end of game
     */
    private void handelEndOfGame () {
        StringBuilder winnerName     = new StringBuilder();
        int           howManyWinners = 0;
        for (Player value : player) {
            if (value.isActive()) {
                if (value.getLap() >= 2) {
                    if (howManyWinners > 0) {
                        winnerName.append(", ");
                    }
                    winnerName.append(value.getName());
                    howManyWinners++;
                }
            }
        }
        int winnerInt = whichOneIsWinner();
        if (howManyWinners <= 1) {
            gui.onGameEnd("Winner\nis\n" + winnerName, winnerInt);
        } else {
            gui.onGameEnd("Winners\nare\n" + winnerName, winnerInt);
        }
        lastLap = false;
    }
}
