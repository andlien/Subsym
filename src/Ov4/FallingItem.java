package Ov4;

import java.awt.*;

/**
 * Created by simen on 04.04.2016.
 */
public class FallingItem {

    int height;
    int startX;
    int length;

    public FallingItem(int startX, int length) {
        this.startX = startX;
        this.length = length;
        height = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getStartX() {

        return startX;
    }

    public int getLength() {

        return length;
    }

    public boolean fallOneBlockDown(){
        height += 1;
        return height < 15;
    }

    public Color getColor(){
        if(length == 1) return Color.GREEN.darker();
        else if(length == 2) return Color.BLUE.darker();
        else if(length == 3) return Color.ORANGE.darker();
        else if(length == 4) return Color.YELLOW.darker();
        else  return Color.RED.darker();
    }

    public int[] getBlockPositions(){
        int[] points = new int[length];
        for (int i = 0; i < length; i++) {
            points[i] = startX + i;
        }

        return points;
    }
}
