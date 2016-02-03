package Ov1;


import sun.applet.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Anders on 27.01.2016.
 */
public abstract class Entity {

    protected int xpos;
    protected int ypos;

    protected double speedX;
    protected double speedY;
    protected double oldDir = 0;

    protected int MAX_SPEED;

    public boolean dead = false;

    public abstract Shape getIcon();
    public abstract Color getColor();

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setSpeed(double speedX, double speedY) {

        this.speedX = speedX;
        this.speedY = speedY;

        if (this.speedX != 0.0 || this.speedY != 0.0)
            this.oldDir = Math.atan2(this.speedY, this.speedX);
    }

    public void scaleSpeed() {
        double factor = 1.0;
        double absSpeed = getAbsSpeed();

        if (absSpeed > MAX_SPEED && absSpeed != 0.0) {
            factor = MAX_SPEED / absSpeed;
        }

        this.speedX = Math.round(factor * speedX);
        this.speedY = Math.round(factor * speedY);
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

    protected ArrayList<Obstacle> getNearbyObstacles(double range, ArrayList<Entity> objects) {
        return objects.stream().filter(closeObstacle -> !this.equals(closeObstacle) &&
                this.getDistance(closeObstacle) <= range &&
                closeObstacle instanceof Obstacle
        ).map(closeObstacle -> (Obstacle) closeObstacle).collect(Collectors.toCollection(ArrayList::new));
    }

    protected ArrayList<Boid> getNearbyBoids(double range, ArrayList<Entity> objects) {
        return objects.stream().filter(possNeighbour -> !this.equals(possNeighbour) &&
                this.getDistance(possNeighbour) <= range &&
                possNeighbour instanceof Boid
        ).map(possNeighbour -> (Boid) possNeighbour).collect(Collectors.toCollection(ArrayList::new));
    }

    protected ArrayList<Predator> getNearbyPredators(double range, ArrayList<Entity> objects) {
        return objects.stream().filter(closePredator -> !this.equals(closePredator) &&
                this.getDistance(closePredator) <= range &&
                closePredator instanceof Predator
        ).map(closeObstacle -> (Predator) closeObstacle).collect(Collectors.toCollection(ArrayList::new));
    }

    public abstract void updateEntity(ArrayList<Entity> objects);

    public double getDistance(Entity other) {

//        int deltaX;
//        int deltaY;
//
//        if (this.getXpos() < other.getXpos()) {
//            deltaX = Math.min(this.getXpos() - other.getXpos(), MainProgram.board.getWidth() + this.getXpos() - other.getXpos());
//        } else {
//            deltaX = Math.min(other.getXpos() - this.getXpos(), MainProgram.board.getWidth() + other.getXpos() - this.getXpos());
//        }
//
//        if (this.getYpos() < other.getYpos()) {
//            deltaY = Math.min(this.getYpos() - other.getYpos(), MainProgram.board.getHeight() + this.getYpos() - other.getYpos());
//        } else {
//            deltaY = Math.min(other.getYpos() - this.getYpos(), MainProgram.board.getHeight() + other.getYpos() - this.getYpos());
//        }
//
//        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return Math.sqrt(Math.pow(other.xpos - xpos, 2) + Math.pow(other.ypos - ypos, 2));
    }

    public int getVectorXComponent(int x) {
        if (Math.abs((x - xpos)) < MainProgram.board.getWidth() / 2) {
            return x - xpos;
        }
        else if (x > xpos) {
            return x - (xpos + MainProgram.board.getWidth());
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
            return y - (ypos + MainProgram.board.getHeight());
        }
        else {
            return y + MainProgram.board.getHeight() - ypos;
        }
    }
}
