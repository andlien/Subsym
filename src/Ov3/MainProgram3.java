package Ov3;

import javax.swing.*;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainProgram3 {

    public static JSlider slider1 = new JSlider(0, 1000, 80);
    public static JLabel label1 = new JLabel();
    public static JLabel labelFoodScore = new JLabel();
    public static JLabel labelPoisonScore = new JLabel();
    public static JLabel labelScore = new JLabel();
    public static JLabel labelTicks = new JLabel();

    public static void main(String[] args) {

        Scenario scenario = new Scenario(10,10,0.33f,0.33f);
        Scenario scenario1 = scenario.makeCopy();
        Agent agent = new Agent();

        BoardGraphics bg = createBoardGraphics(scenario,agent);
//        agent.goLeft();


        Evo_alg mainAlgorithm = new Evo_alg(bg, scenario);

        System.out.println("Starting generations");
        for (int i = 0; i < 25; i++) {
            System.out.println("GEN: " + i);
            mainAlgorithm.runNextGeneration();
//            mainAlgorithm.setScenario(new Scenario(10,10,0.33f,0.33f));
//            break;
        }
        mainAlgorithm.runBestWithGraphics();

    }

    static void tick(int msec, BoardGraphics bg) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        bg.repaint();
    }



//Starter grafikken
    private static BoardGraphics createBoardGraphics(Scenario scenario, Agent agent){

        JFrame window = new JFrame();
        BoardGraphics bg = new BoardGraphics(scenario,agent);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 80 * scenario.getX(), 80 * scenario.getY());
        window.getContentPane().add(bg);
        window.setVisible(true);

        JFrame controlPanelFrame = new JFrame();
        JPanel controlPanelPanel = new JPanel();
        controlPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanelFrame.setBounds(900, 30, 300, 800);

        label1.setText("Speed");


        controlPanelPanel.add(labelPoisonScore);
        controlPanelPanel.add(labelFoodScore);
        controlPanelPanel.add(labelScore);

        controlPanelPanel.add(labelTicks);
        controlPanelPanel.add(label1);
        controlPanelPanel.add(slider1);
        controlPanelFrame.getContentPane().add(controlPanelPanel);
        controlPanelFrame.setVisible(true);

        return bg;
    }

}
