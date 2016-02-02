package Ov1;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Created by Anders on 27.01.2016.
 */
public class Obstacle extends Entity {

    public static final double radius = 20;

    public Obstacle(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;

        this.speedX = 0;
        this.speedY = 0;
    }

    /**
     * An obstacle is a circle with radius 20px
     *
     * @return the new Ellipsis
     */
    @Override
    public Shape getIcon() {
        return new Ellipse2D.Float(xpos - (int) radius, ypos - (int) radius, (int)radius * 2, (int) radius * 2);
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Polygon getRotatedPolygon(Polygon poly, double theta) {
        throw new RuntimeException("Unneccessary");
    }

    @Override
    public void updateEntity(ArrayList<Entity> others) {}

    @Override
    public void setSpeed(double speedX, double speedY) {}

    public int[][] getIntersectionByBoidDirection(Boid boid) {

        double speedRatio = boid.speedY / boid.speedX;
        double powSpeedRatio = Math.pow(speedRatio, 2.0);

//        double a = 1.0 + Math.pow(speedRatio, 2.0);
//        double b = -2.0*this.xpos - 2*Math.pow(speedRatio, 2.0)*boid.getXpos() + 2.0*speedRatio*boid.ypos - 2.0*speedRatio*this.ypos;
//        double c = Math.pow(speedRatio, 2.0) * Math.pow(boid.getXpos(), 2.0) - 2.0 * speedRatio * boid.getXpos() * boid.getYpos() + Math.pow(boid.getYpos(), 2.0) + 2.0 * speedRatio * this.ypos * boid.getXpos() - 2.0 * speedRatio * this.ypos * boid.getYpos() + Math.pow(this.ypos, 2.0) - Math.pow(radius, 2.0);

        double insideSqrt = powSpeedRatio * (- Math.pow(this.xpos, 2.0)) + 2.0 * powSpeedRatio * this.xpos * boid.xpos - powSpeedRatio * Math.pow(boid.xpos, 2.0) + powSpeedRatio * Math.pow(radius, 2.0) + 2.0* speedRatio * this.xpos * this.ypos - 2.0 * speedRatio * this.xpos * boid.ypos - 2.0 * speedRatio * this.ypos * boid.xpos + 2.0 * speedRatio * boid.xpos * boid.ypos - Math.pow(this.ypos, 2.0) + 2.0 * this.ypos * boid.ypos + Math.pow(radius, 2.0) - Math.pow(boid.ypos, 2.0);

        if (insideSqrt < 0 ) {
            return new int[][]{};
        }

        double xpart = powSpeedRatio * boid.xpos + speedRatio * this.ypos - speedRatio * boid.ypos + this.xpos;

        if (insideSqrt == 0) {
            double x = xpart / (powSpeedRatio + 1.0);
            double y = speedRatio * (xpart - boid.getXpos()) + boid.getYpos();
//
            return new int[][] {{(int) x, (int) y}};
        }
//
        double x1 = (xpart + Math.sqrt(insideSqrt)) / (powSpeedRatio + 1);
        double x2 = (xpart - Math.sqrt(insideSqrt)) / (powSpeedRatio + 1);

        double y1 = speedRatio * (x1 - boid.getXpos()) + boid.getYpos();
        double y2 = speedRatio * (x2 - boid.getXpos()) + boid.getYpos();

        return new int[][]{{(int) x1, (int) y1}, {(int) x2, (int) y2}};

    }
}
