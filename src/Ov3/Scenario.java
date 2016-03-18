package Ov3;


import java.util.Random;

public class Scenario {

    Tile[][] tiles;

    public static void main(String[] args) {
        createScenario(10,10,0.33f,0.33f);
    }

    private static Tile[][] createScenario(int x, int y,float distFood, float distPoison){
        int numberOfTiles = x * y;
        int numberOfFoodTiles = (int) (distFood * numberOfTiles);
        int numberOfPoisonTiles = (int) (distPoison * numberOfTiles);

        Tile[][] tiles = new Tile[x][y];

        Random rn = new Random();
        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                tiles[ix][iy] = Tile.NONE;
                if(numberOfFoodTiles > 0 && rn.nextFloat() >= (1.0-distFood)) {
                    System.out.println("Food was added. Remaining foods: " + numberOfFoodTiles);
                    tiles[ix][iy] = Tile.Food;
                    numberOfFoodTiles --;

                } else{
                    System.out.println("Empty tile");
                }
            }

        }

        return tiles;
    }

}
