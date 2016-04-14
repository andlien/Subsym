package Ov4;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainProgram4 {

    public static BoardOv4 boardGraphics;
    public static JSlider slider1 = new JSlider(0, 500, 80);
    public static JLabel label1 = new JLabel();
    public static JLabel label2 = new JLabel();
    public static JLabel label3 = new JLabel();
    public static JLabel label4 = new JLabel();
    public static JLabel label5 = new JLabel();
    public static JLabel labelScore = new JLabel();
    public static JLabel labelTicks = new JLabel();

    public static int[] staticPositions;
    public static int[] staticLengths;

    public static void main(String[] args) {

        EvoAlg4 mainAlgorithm = new EvoAlg4();

        System.out.println("Starting generations");

        staticPositions = new int[100];
        staticLengths = new int[100];

        for (int i = 0; i < staticPositions.length; i++) {
            Random rn = new Random();
            int length = (int) (rn.nextFloat() * 6) + 1;
            int xPos = (int) (rn.nextFloat() * (31 - length));
            staticPositions[i] = xPos;
            staticLengths[i] = length;


        }

        // 60 gen standard and pull
        // 30 for nowrap

        for (int i = 0; i < 250; i++) {
            mainAlgorithm.runNextGeneration();
//            mainAlgorithm.setScenario(new Scenario(10,10,0.33f,0.33f));
        }
        mainAlgorithm.runBestWithGraphics();


    }

    public static void tick(int msec, BoardOv4 bg) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        bg.repaint();
    }


//
//    //Starter grafikken
    public static BoardOv4 createBoardGraphics(SimulateGame game){

        JFrame window = new JFrame();
        BoardOv4 bg = new BoardOv4(game);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 30 * 35, 15 * 35);
        window.getContentPane().add(bg);
        window.setVisible(true);

        JFrame controlPanelFrame = new JFrame();
        JPanel controlPanelPanel = new JPanel();
        controlPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanelFrame.setBounds(900, 30, 300, 800);

        label1.setText("Speed");


        controlPanelPanel.add(label2);
        controlPanelPanel.add(label3);
        controlPanelPanel.add(label4);
        controlPanelPanel.add(label5);
        controlPanelPanel.add(labelScore);

        controlPanelPanel.add(labelTicks);
        controlPanelPanel.add(label1);
        controlPanelPanel.add(slider1);
        controlPanelFrame.getContentPane().add(controlPanelPanel);
        controlPanelFrame.setVisible(true);

        return bg;
    }

}

