package Ov1;

import java.awt.*;

/**
 * Created by Anders on 27.01.2016.
 */
public class Boid extends Entity {

    public static final int ground = 20;
    public static final int height = 40;

    public Boid(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;
    }

    /**
     * Boids are triangles with height 40px and 20px ground
     * Center of rotation is 10px above ground
     *
     * @return
     */
    @Override
    public Shape getIcon() {
        int[] xpoints = {xpos - ground / 2, xpos + ground / 2, xpos};
        int[] ypoints = {ypos + 10, ypos + 10, ypos + 10 - height};
        return getRotatedPolygon(new Polygon(xpoints, ypoints, 3), direction);
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }


}
