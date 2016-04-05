package Ov3;

/**
 * Created by simen on 22.03.2016.
 */
public enum Direction {

    UP(-1,0,0,-1,1,0),
    LEFT(0,1,-1,0,0,-1),
    RIGHT(0,-1,1,0,0,1),
    DOWN(1,0,0,1,-1,0);

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getX3() {
        return x3;
    }

    public int getY3() {
        return y3;
    }

    private final int x1, y1, x2, y2, x3, y3;

    Direction(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public int[] getPosAsList(){
        int[] theList = new int[6];
        theList[0] = x1;
        theList[1] = y1;
        theList[2] = x2;
        theList[3] = y2;
        theList[4] = x3;
        theList[5] = y3;

        return theList;
    }


}
