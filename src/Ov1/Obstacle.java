package Ov1;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Created by Anders on 27.01.2016.
 */
public class Obstacle extends Entity {

    public static final int radius = 25;

    public Obstacle(int xPos, int yPos) {
        canMove = false;

        this.xpos = xPos;
        this.ypos = yPos;
    }

    /**
     * An obstacle is a circle with radius 20px
     *
     * @return the new Ellipsis
     */
    @Override
    public Shape getIcon() {
        return new Ellipse2D.Float(xpos, ypos, radius, radius);
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
    public void updateEntity(ArrayList<Entity> others) {
        return;
    }
}
