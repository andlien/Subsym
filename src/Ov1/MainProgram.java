package Ov1;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MainProgram {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Board graphics = createBoard();

        while (true) {

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

    private void updateBoid(Boid thisBoid) {

    }

    private static Board createBoard(){

        JFrame window = new JFrame();
        Container pane = window.getContentPane();

        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        ControlPanel cp = new ControlPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridy = 0;
        pane.add(cp, c);

        Board board = new Board();
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
