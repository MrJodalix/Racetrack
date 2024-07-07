package logic;

/**
 * The Player Class to create the players
 *
 * @author Joshua-Scott Schöttke
 */
public class Player {
    /**
     * is player active
     */
    private final boolean active;
    /**
     * the name of the player
     */
    private final String name;
    /**
     * is the player an ai
     */
    private final boolean ai;
    /**
     * the current position of the player
     */
    private int[] current;
    /**
     * the last position of the player
     */
    private int[] last;
    /**
     * the lap of the player 0,1 or 2  */
    private int lap;

    /**
     * Constructor
     *
     * @param active  is the player activ
     * @param name    the name of the player
     * @param ai      is the player an ai
     * @param posNew  the current or new position of the player
     * @param posLast the last position of the player
     * @param round   in which round is the player
     */
    public Player (boolean active, String name, boolean ai, int[] posNew, int[] posLast,
            int round) {
        this.active  = active;
        this.name    = name;
        this.ai      = ai;
        this.current = posNew;
        this.last    = posLast;
        this.lap     = round;
    }

    /**
     * get the name
     *
     * @return name of player
     */
    public String getName () {
        return name;
    }

    /**
     * get the current position
     *
     * @return current position of player
     */
    public int[] getCurrent () {
        return current;
    }

    /**
     * get the last position
     *
     * @return last position of player
     */
    public int[] getLast () {
        return last;
    }

    /**
     * get the lap
     *
     * @return lap of player
     */
    public int getLap () {
        return lap;
    }

    /**
     * set the current position
     *
     * @param current position of player
     */
    public void setCurrent (int[] current) {
        this.current = current;
    }

    /**
     * set the last position
     *
     * @param last position of player
     */
    public void setLast (int[] last) {
        this.last = last;
    }

    /**
     * set the lap
     *
     * @param lap of player
     */
    public void setLap (int lap) {
        this.lap = lap;
    }

    /**
     * is the player active
     *
     * @return active true/false
     */
    public boolean isActive () {
        return active;
    }

    /**
     * is player an ai
     *
     * @return ai true, human false
     */
    public boolean isAi () {
        return ai;
    }
}
