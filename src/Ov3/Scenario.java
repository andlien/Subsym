package Ov3;


import java.util.Random;

public class Scenario {

    public Tile[][] getTiles() {
        return tiles;
    }

    private Tile[][] tiles;
    private int x,y;
    private float distFood,distPoison;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Scenario(int x, int y, float distFood, float distPoison) {
        this.x = x;
        this.y = y;

        this.distFood = distFood;
        this.distPoison = distPoison;
        tiles = createScenario();
    }

    private  Tile[][] createScenario(){
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


        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                if (tiles[ix][iy] == Tile.NONE) {
                    if (numberOfPoisonTiles > 0 && rn.nextFloat() >= (1.0 - distPoison)) {
                        System.out.println("Poison was added. Remaining poisons: " + numberOfFoodTiles);
                        tiles[ix][iy] = Tile.Poison;
                        numberOfPoisonTiles--;

                    }
                }
            }
        }


        return tiles;
    }

}
