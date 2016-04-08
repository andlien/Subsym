package Ov4;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainProgram4 {

//    private static BoardGraphics boardGraphics;
//    private static JSlider slider1 = new JSlider(0, 1000, 80);
//    private static JLabel label1 = new JLabel();
//    private static JLabel labelFoodScore = new JLabel();
//    private static JLabel labelPoisonScore = new JLabel();
//    private static JLabel labelScore = new JLabel();
//    private static JLabel labelTicks = new JLabel();


    private static FallingItem fallingItem;
    private static CatcherObject catcherObject;

    public static FallingItem getFallingItem() {
        return fallingItem;
    }

    public static CatcherObject getCatcherObject() {
        return catcherObject;
    }

    public static void main(String[] args) {

        BoardOv4 bg = createBoardGraphics();
        fallingItem = createFallingItem();
        catcherObject = new CatcherObject();

        while(true){
            boolean hasSpaceLeftToFall = fallingItem.fallOneBlockDown();
            if(hasSpaceLeftToFall){
                catcherObject.moveRight();
            }
            else{
                fallingItem = createFallingItem();

            }
            tick(200, bg);
        }

    }

    private static FallingItem createFallingItem(){
        Random rn = new Random();
        int length = (int) (rn.nextFloat() * 6) + 1;
        int xPos = (int) (rn.nextFloat() * (31 - length));
        return new FallingItem(xPos,length);
    }

    public static int[] getSensorOutput(){
        int[] coPoints = getCatcherObject().getBlockPositions();
        int[] foPoints = getFallingItem().getBlockPositions();
        int[] output = new int[5];
        for (int i = 0; i < 5; i++) {
            int coPoint = coPoints[i];
            output[i] = 0;
            for (int i2 = 0; i2 < foPoints.length; i2++) {
                if(coPoint == foPoints[i2]){
                    output[i] = 1;
                    break;
                }
            }


        }
        return output;
    }





    private static void tick(int msec, BoardOv4 bg) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        bg.repaint();
    }


//
//    //Starter grafikken
    private static BoardOv4 createBoardGraphics(){

        JFrame window = new JFrame();
        BoardOv4 bg = new BoardOv4();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 30 * 35, 15 * 35);
        window.getContentPane().add(bg);
        window.setVisible(true);

        JFrame controlPanelFrame = new JFrame();
        JPanel controlPanelPanel = new JPanel();
        controlPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanelFrame.setBounds(900, 30, 300, 800);

//        label1.setText("Speed");
//
//
//        controlPanelPanel.add(labelPoisonScore);
//        controlPanelPanel.add(labelFoodScore);
//        controlPanelPanel.add(labelScore);
//
//        controlPanelPanel.add(labelTicks);
//        controlPanelPanel.add(label1);
//        controlPanelPanel.add(slider1);
//        controlPanelFrame.getContentPane().add(controlPanelPanel);
//        controlPanelFrame.setVisible(true);

        return bg;
    }

}

