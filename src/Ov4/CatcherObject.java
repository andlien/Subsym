package Ov4;

/**
 * Created by simen on 04.04.2016.
 */
public class CatcherObject {
    int xPos;
    final int length = 5;

    public CatcherObject() {
    }

    public int getxPos() {
        return xPos;
    }

    public int getLength() {
        return length;
    }

    public void moveLeft(){
        xPos --;
        if(xPos < 0) xPos = 0;

    }

    public void moveRight(){
        xPos ++;
        if(xPos > 30-length) xPos = 0;
//        if(xPos > 30-length) xPos = 30-length;
    }

    public int[] getBlockPositions(){
        int[] points = new int[5];
        for (int i = 0; i < length; i++) {
            points[i] = xPos + i;
        }

        return points;
    }
}
