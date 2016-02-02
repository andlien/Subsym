package Ov1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainProgram {

    public static ControlPanel cp;
    public static Board board;

    public static Obstacle testObstacle;
    public static Boid testBoid;


    /**
     * @param args
     */
    public static void main(String[] args) {

        ArrayList<Entity> objects = new ArrayList<>();
        Random random = new Random();

        createGUI();

        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));
        objects.add(new Boid(random));

        board.setObjects(objects);

        System.out.println("Running simulation with " + objects.size() + " boids");

        int tics = 0;

        while (true) {

            for (Entity obj : objects) {
                obj.updateEntity(objects);

                if (Double.isNaN(obj.speedX) || Double.isNaN(obj.speedY) || Double.isNaN(obj.oldDir)) {
                    // debugging
                    System.out.println();
                }
            }

            tick(50, board);
            tics++;

            if (tics > 500) {
                tics = 0;
            }
        }
    }

    private static void tick(int msec, Board bg) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        bg.repaint();
    }

    private static Board createGUI(){

        JFrame window = new JFrame();
        Container pane = window.getContentPane();

        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        cp = new ControlPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridy = 0;
        pane.add(cp, c);

        board = new Board();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.weighty = 1.0;
        pane.add(board, c);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 1300, 900);
        window.setVisible(true);

        return board;
    }

}
