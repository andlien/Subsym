package Ov3;

import javax.swing.JFrame;

/**
 * Created by simen on 22.03.2016.
 */
public class MainProgram3 {



    public static void main(String[] args) {

        Scenario scenario = new Scenario(10,10,0.33f,0.33f);
        BoardGraphics bg = createBoardGraphics(scenario);
        while(true) bg.repaint();
//        BoardGraphics(s);
    }



    private static BoardGraphics createBoardGraphics(Scenario scenario){

//        int rectangleSize = BoardGraphics.getRectangleSize();
        JFrame window = new JFrame();
        BoardGraphics bg = new BoardGraphics(scenario);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 80 * scenario.getX(), 80 * scenario.getY());
        window.getContentPane().add(bg);
        window.setVisible(true);

        return bg;
    }

}
