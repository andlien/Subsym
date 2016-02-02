package Ov1;


import sun.applet.Main;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anders on 27.01.2016.
 */
public abstract class Entity {

    protected int xpos;
    protected int ypos;

    protected boolean canMove;
    protected double speedX;
    protected double speedY;
    protected double oldDir = 0;

    protected int MAX_SPEED;

    public abstract Shape getIcon();
    public abstract Color getColor();

    public Polygon getRotatedPolygon(Polygon poly, double theta) {

        if (theta == 0.0) {
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

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setSpeed(double speedX, double speedY) {
        double factor = 1.0;
        double absSpeed = getAbsSpeed();

        if (absSpeed > MAX_SPEED && absSpeed != 0.0) {
            factor = MAX_SPEED / absSpeed;
        }

        this.speedX = Math.round(factor * speedX);
        this.speedY = Math.round(factor * speedY);

        if (this.speedX != 0.0 || this.speedY != 0.0)
            this.oldDir = Math.atan2(this.speedY, this.speedX);
    }

    public double getAbsSpeed() {
        return Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2));
    }

    public void move() {
        xpos = ((int) (xpos + speedX)) % MainProgram.board.getWidth();
        ypos = ((int) (ypos + speedY)) % MainProgram.board.getHeight();

        if (xpos < 0) {
            xpos += MainProgram.board.getWidth();
        }
        if (ypos < 0) {
            ypos += MainProgram.board.getHeight();
        }
    }


    public abstract void updateEntity(ArrayList<Entity> objects);

    public double getDistance(Entity other) {

        int deltaX;
        int deltaY;

        if (this.getXpos() < other.getXpos()) {
            deltaX = Math.min(this.getXpos() - other.getXpos(), MainProgram.board.getWidth() + this.getXpos() - other.getXpos());
        } else {
            deltaX = Math.min(other.getXpos() - this.getXpos(), MainProgram.board.getWidth() + other.getXpos() - this.getXpos());
        }

        if (this.getYpos() < other.getYpos()) {
            deltaY = Math.min(this.getYpos() - other.getYpos(), MainProgram.board.getHeight() + this.getYpos() - other.getYpos());
        } else {
            deltaY = Math.min(other.getYpos() - this.getYpos(), MainProgram.board.getHeight() + other.getYpos() - this.getYpos());
        }

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

//        return Math.sqrt(Math.pow(getVectorXComponent(other.getXpos()), 2) + Math.pow(getVectorYComponent(other.getYpos()), 2));
    }

    public int getVectorXComponent(int x) {
        if (Math.abs((x - xpos)) < MainProgram.board.getWidth() / 2) {
            return x - xpos;
        }
        else if (x > xpos) {
            return (x - MainProgram.board.getWidth()) - xpos;
        }
        else {
            return x + MainProgram.board.getWidth() - xpos;
        }
    }

    public int getVectorYComponent(int y) {
        if (Math.abs((y - ypos)) < MainProgram.board.getHeight() / 2) {
            return y - ypos;
        }
        else if (y > ypos) {
            return (y - MainProgram.board.getHeight()) - ypos;
        }
        else {
            return y + MainProgram.board.getHeight() - ypos;
        }
    }
}
