package Ov1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainProgram {

    public static ControlPanel cp;
    public static Board board;

    public static Obstacle testObstacle;
    public static Boid testBoid;
    public static ArrayList<Entity> objects;

    public static boolean deleteObstacles = false;
    public static boolean deletePredators = false;


    /**
     * @param args
     */
    public static void main(String[] args) {


        objects = new ArrayList<>();
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

            for (Entity obj : (ArrayList<Entity>)objects.clone()) {
                obj.updateEntity((ArrayList<Entity>) objects.clone());
            }

            objects.retainAll(objects.stream().filter(entity -> !entity.dead).collect(Collectors.toList()));

            if (deleteObstacles) {
                objects.removeAll(objects.stream().filter(entity -> entity instanceof Obstacle).collect(Collectors.toList()));
                deleteObstacles = false;
            }

            if (deletePredators) {
                objects.removeAll(objects.stream().filter(entity -> entity instanceof Predator).collect(Collectors.toList()));
                deletePredators = false;
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

    public static int[][] getPointsRotatedAround(int[] xpoints, int[] ypoints, int centreX, int centreY, double theta) {
        if (theta == 0.0) {
            return new int[][]{xpoints, ypoints};
        }

        int[] newX = new int[xpoints.length];
        int[] newY = new int[ypoints.length];

        for (int i = 0; i < xpoints.length; i++) {
            newX[i] = (int) ((Math.cos(theta) * (xpoints[i] - centreX) - Math.sin(theta) * (ypoints[i] - centreY)) + centreX);
            newY[i] = (int) ((Math.sin(theta) * (xpoints[i] - centreX) + Math.cos(theta) * (ypoints[i] - centreY)) + centreY);
        }

        return new int[][]{newX, newY};
    }

}
