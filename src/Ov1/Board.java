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

        setBackground(Color.LIGHT_GRAY);
    }

    public void setObjects(ArrayList<Entity> objects) {
        this.objects = objects;
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
