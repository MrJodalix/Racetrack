package logic;

public class Position {
    private int[] pos = {0,0};

    public Position(int[] current) {
        pos[0] = current[0];
        pos[1] = current[1];
    }

    public int[] getPos () {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }
}
