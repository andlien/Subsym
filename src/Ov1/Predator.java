package Ov1;

import java.awt.*;

/**
 * Created by Anders on 27.01.2016.
 */
public class Predator extends Entity {

    public static final int ground = 30;
    public static final int height = 60;

    public Predator(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;
    }

    /**
     * Predators are triangles with height 60px and 30px ground
     * Center of rotation is 15px above ground
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
        return Color.RED;
    }
}
