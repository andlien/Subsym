package Ov1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by Anders on 26.01.2016.
 */
public class Board extends JPanel {

    private ArrayList<Entity> objects;

    public Board() {
        super();

        objects = new ArrayList<>();

        objects.add(new Boid(100, 100));
        objects.add(new Boid(100, 200));
        objects.get(1).setDirection(Math.toRadians(80));
        objects.add(new Obstacle(200, 100));
        objects.add(new Predator(200, 200));
        objects.get(3).setDirection(Math.toRadians(-20));

        setBackground(Color.LIGHT_GRAY);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D n = (Graphics2D) g;

        for (Entity obj : objects) {
            n.setColor(obj.getColor());
            n.fill(obj.getIcon());
        }
    }

}
