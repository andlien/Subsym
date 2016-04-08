package Ov4;


import Ov3.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by simen on 04.04.2016.
 */
public class BoardOv4 extends JComponent {



    public int getRectangleSize() {
        return rectangleSize;
    }

    private final int rectangleSize = 30;
    private final int boardHeight = 15;
    private final int boardLength = 30;
    private final int pixelBetweenTiles = 2;



    public BoardOv4() {

    }

    //The board is repainted from the mainprogram class at a given intervall
    public void paint(Graphics g) {



        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardLength; x++) {

                g.setColor(Color.LIGHT_GRAY.brighter());
                if(y == boardHeight -1) g.setColor(Color.lightGray);
                g.fillRect((rectangleSize+pixelBetweenTiles)*x,(rectangleSize+pixelBetweenTiles)*y,rectangleSize,rectangleSize);

            }
        }
        FallingItem fi = MainProgram4.getFallingItem();
        int y = fi.getHeight();
        for (int x = 0; x < fi.getLength(); x++) {
            int xCord = x + fi.getStartX();
            g.setColor(fi.getColor());

            g.fillRect((rectangleSize+pixelBetweenTiles)*xCord,(rectangleSize+pixelBetweenTiles)*y,rectangleSize,rectangleSize);
        }

        CatcherObject co = MainProgram4.getCatcherObject();
        y = 14;
        int[] sensorPoints = MainProgram4.getSensorOutput();
        for (int x = 0; x < co.getLength(); x++) {
            int xCord = (x + co.getxPos())%30;
            if(sensorPoints[x] == 1) g.setColor(Color.BLUE.brighter());
            else g.setColor(Color.BLUE.darker().darker().darker());

            g.fillRect((rectangleSize+pixelBetweenTiles)*xCord,(rectangleSize+pixelBetweenTiles)*y,rectangleSize,rectangleSize);
        }




//        g.setColor(Color.BLUE);
//        int[] sensorPos = agent.getSensorLocation();
//        g.fillRect((rectangleSize+pixelBetweenTiles)*(agent.getxPos())+ rectangleSize/2 - agentSize/2,(rectangleSize+pixelBetweenTiles)*(agent.getyPos())+ rectangleSize/2 -agentSize/2,agentSize,agentSize);
//        g.setColor(Color.BLUE.darker());
//        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[0])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[1])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);
//        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[2])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[3])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);
//        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[4])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[5])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);

    }
}
