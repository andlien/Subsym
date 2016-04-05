package Ov3;

import javax.swing.*;
import java.awt.*;



public class BoardGraphics extends JComponent {

    Scenario scenario;
    Agent agent;

    public int getRectangleSize() {
        return rectangleSize;
    }

    private final int rectangleSize = 70; //Rectangle edges
    private final int agentSize = 60; //Small Rectangle edges
    private final int sensorSize = 25; //Small Rectangle edges
    private final int pixelBetweenTiles = 5;



    public BoardGraphics(Scenario scenario,Agent agent) {

        this.scenario = scenario;
        this.agent = agent;
    }

    public void updateBoardGraphics(Scenario scenario,Agent agent) {
        this.scenario = scenario;
        this.agent = agent;
    }

    //The board is repainted from the mainprogram class at a given intervall
    @Override
    public void paint(Graphics g) {

        Tile[][] gridTiles = scenario.getTiles();

        for (int y = 0; y < gridTiles.length; y++) {
            for (int x = 0; x < gridTiles[0].length; x++) {
                Tile tile = gridTiles[y][x];

                g.setColor(tile.getColor());
                g.fillRect((rectangleSize+pixelBetweenTiles)*x,(rectangleSize+pixelBetweenTiles)*y,rectangleSize,rectangleSize);

            }
        }


        g.setColor(Color.BLUE);
        int[] sensorPos = agent.getSensorLocation();
        g.fillRect((rectangleSize+pixelBetweenTiles)*(agent.getxPos())+ rectangleSize/2 - agentSize/2,(rectangleSize+pixelBetweenTiles)*(agent.getyPos())+ rectangleSize/2 -agentSize/2,agentSize,agentSize);
        g.setColor(Color.BLUE.darker());
        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[0])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[1])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);
        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[2])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[3])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);
        g.fillRect((rectangleSize+pixelBetweenTiles)*(sensorPos[4])+ rectangleSize/2 - sensorSize/2,(rectangleSize+pixelBetweenTiles)*(sensorPos[5])+ rectangleSize/2 -sensorSize/2,sensorSize,sensorSize);

    }

}
