package Ov3;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainProgram3 {

    private static BoardGraphics boardGraphics;
    private static JSlider slider1 = new JSlider(0, 1000, 80);
    private static JLabel label1 = new JLabel();
    private static JLabel labelFoodScore = new JLabel();
    private static JLabel labelPoisonScore = new JLabel();
    private static JLabel labelScore = new JLabel();
    private static JLabel labelTicks = new JLabel();

    public static void main(String[] args) {

        Scenario scenario = new Scenario(10,10,0.33f,0.33f);
        Scenario scenario1 = scenario.makeCopy();
        Agent agent = new Agent();
        BoardGraphics bg = createBoardGraphics(scenario,agent);
        agent.goLeft();


        simulateAgent(scenario,true);

    }

    //Må endres slik at den tar det neurale nettet
    public static float simulateAgent(Scenario scenarioTemplate, boolean withGraphics){
        Scenario  scenario = scenarioTemplate.makeCopy();
        Agent agent = new Agent();
        if(withGraphics) boardGraphics.updateBoardGraphics(scenario,agent);
        int ticks = 60;

        for (int i = 0; i < ticks; i++) {

            /* //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\\

                TODO - Her må det neurale nettet velge neste handling(Tall mellom 1 og 3))

             */ //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\\


            int number = 1; //Output fra nettet


            Random rn = new Random();//TODO - Kommenter vekk disse. Kun til testing
            number = (int) (rn.nextFloat() * 3);//TODO - Kommenter vekk disse. Kun til testing

            if(number == 1) agent.goForward();
            else if(number == 2) agent.goLeft();
            else if(number == 3) agent.goRight();

            //Update info based on the current tile
            Tile currentTile = scenario.getTiles()[agent.getyPos()][agent.getxPos()];
            if(currentTile == Tile.Food){
                scenario.resetTile(agent.getyPos(),agent.getxPos());
                agent.steppedOnFoodTile();
            }
            else if(currentTile == Tile.Poison){
                scenario.resetTile(agent.getyPos(),agent.getxPos());
                agent.steppedOnPoisonTile();
            }
            if(withGraphics){
                labelTicks.setText("Ticks: " + i + "/" + ticks);
                label1.setText("Speed:" + slider1.getValue() + " ms");
                labelFoodScore.setText("Score food: " + agent.getFoodScore());
                labelPoisonScore.setText("Score poison: " + agent.getPoisonScore());
                labelScore.setText("Score: " + agent.getScore());
                tick(slider1.getValue(), boardGraphics);
            }

        }


        return agent.getScore();
    }



    private static void tick(int msec, BoardGraphics bg) {
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
        boardGraphics = bg;
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
