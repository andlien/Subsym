package Ov4;

/**
 * Created by simen on 04.04.2016.
 */
public class CatcherObject {
    int xPos = 10;
    final int length = 5;
    private boolean noWrap = false;

    public void setNoWrap(boolean noWrap) {
        this.noWrap = noWrap;
    }

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
        if(xPos < 0){
            if(!noWrap) xPos = 30;
            else xPos = 0;
        }

    }

    public boolean getLeftBumperSensor(){
        return xPos == 0;
    }

    public boolean getRightBumperSensor(){
        return xPos == 30-length;
    }

    public void moveRight(){
        xPos ++;
        if(noWrap && xPos > 30-length){
            xPos = 30-length;
        } else if (!noWrap && xPos > 30) {
            xPos = 30;
        }
//        if(xPos > 30-length) xPos = 30-length;
    }

    public int[] getBlockPositions(){
        int[] points = new int[5];
        for (int i = 0; i < length; i++) {
            points[i] = (xPos + i)%30;
        }

        return points;
    }
}
