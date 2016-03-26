package Ov3;

import java.awt.Color;

public enum Tile {

    NONE (new  Color(180,180,180,250), 0),
    Food (Color.RED, 1),
    Poison (Color.GREEN.darker(), -1);


    private final Color color;// Color of the tile

    private final int score;



    private Tile(Color color, int score) {
        this.color = color;
        this.score = score;
    }

    public Color getColor() {
        return this.color;
    }

    public int getScore() {
        return this.score;

    }

}

