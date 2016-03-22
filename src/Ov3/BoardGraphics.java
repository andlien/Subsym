package Ov3;

import javax.swing.*;
import java.awt.*;



public class BoardGraphics extends JComponent {

    Scenario scenario;

    public int getRectangleSize() {
        return rectangleSize;
    }

    private final int rectangleSize = 70; //Rectangel edges
    private final int sre = 10; //Small Rectangel edges
    private final int pixelBetweenTiles = 5;



    public BoardGraphics(Scenario scenario) {
        this.scenario = scenario;
    }

    //The board is repainted from the mainprogram class at a given intervall
    public void paint(Graphics g) {


//        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
//        g.drawString("Type of search: " + algorithm, 30, (gridTiles.length +1)* re);
//        g.drawString("Board: " + adresse, 30, (gridTiles.length +2)* re);
//        g.drawString("Iterations: " + iteration, 30, (gridTiles.length +3)* re);

        Tile[][] gridTiles = scenario.getTiles();

        for (int y = 0; y < gridTiles.length; y++) {
            for (int x = 0; x < gridTiles[0].length; x++) {
                Tile tile = gridTiles[y][x];

                g.setColor(tile.getColor());
                g.fillRect((rectangleSize+pixelBetweenTiles)*x,(rectangleSize+pixelBetweenTiles)*y,rectangleSize,rectangleSize);

            }
        }




    }

}
