package Ov1;

import sun.awt.windows.ThemeReader;

import java.awt.*;

/**
 * Created by Anders on 27.01.2016.
 */
public abstract class Entity {

    protected int xpos;
    protected int ypos;

    protected boolean canMove;
    protected float direction;
    protected int speed;

    public abstract Shape getIcon();
    public abstract Color getColor();

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        if (canMove) {
            return speed;
        } else {
            return 0;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Polygon getRotatedPolygon(Polygon poly, float theta) {

        if (direction == 0.0) {
            return poly;
        }

        int[] newX = new int[poly.xpoints.length];
        int[] newY = new int[poly.ypoints.length];

        for (int i = 0; i < poly.xpoints.length; i++) {
            newX[i] = (int) ((Math.cos(theta) * (poly.xpoints[i] - xpos) - Math.sin(theta) * (poly.ypoints[i] - ypos)) + xpos);
            newY[i] = (int) ((Math.sin(theta) * (poly.xpoints[i] - xpos) + Math.cos(theta) * (poly.ypoints[i] - ypos)) + ypos);
        }

        poly.xpoints = newX;
        poly.ypoints = newY;

        return poly;
    }

}
