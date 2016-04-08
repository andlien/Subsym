package Ov3;


import java.util.Arrays;
import java.util.Random;

import static Ov3.MainProgram3.slider1;

public class Scenario {

    public Tile[][] getTiles() {
        return tiles;
    }

    private Tile[][] tiles;
    private int x,y;
    private int startX,startY;
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

    public Scenario(int x, int y, float distFood, float distPoison, Tile[][] tiles) {
        this.x = x;
        this.y = y;

        this.distFood = distFood;
        this.distPoison = distPoison;
        this.tiles = tiles;
    }


    public void resetTile(int x, int y){
        tiles[x][y] = Tile.NONE;
    }

    public Scenario makeCopy(){
        Tile[][] tilesCopy = new Tile[x][y];
        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                Tile tile = tiles[ix][iy];
                if (tile == Tile.Food) tilesCopy[ix][iy] = Tile.Food;
                else if (tile == Tile.Poison) tilesCopy[ix][iy] = Tile.Poison;
                else tilesCopy[ix][iy] = Tile.NONE;
            }
        }
        Scenario scen = new Scenario(x,y,distFood,distPoison,tilesCopy);
        return scen;
    }

    //Bruk denne for å hente inputverdiene til nettet.
    // Den returnere en liste med lengde 6. De tre første element er 1 hvis det er mat i noen av posisjonene. De tre siste er tilsvarende bare med gift
    // Altså [0,1,0,1,0,0] -> Sensor 1: Gift Sensor 2: Mat Sensor 3: Tom
    public int[] getNetInputNodes(int[] sensorLocations){
        int[] inputNodes = new int[6];
        for (int i = 0; i < 3; i+=2) {
            Tile tile = tiles[sensorLocations[i]][sensorLocations[i+1]];
            if(tile == Tile.Food) inputNodes[i] = 1;
            else inputNodes[i] = 0;

            if(tile == Tile.Poison) inputNodes[i+3] = 1;
            else inputNodes[i+3] = 0;
        }
//        System.out.println("getNetInputNodes: " + Arrays.toString(inputNodes));
        return inputNodes;
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
                if (numberOfFoodTiles > 0 && rn.nextFloat() >= (1.0 - distFood)) {
//                    System.out.println("Food was added. Remaining foods: " + numberOfFoodTiles);
                    tiles[ix][iy] = Tile.Food;
                    numberOfFoodTiles--;

                }

            }
        }


        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                if (tiles[ix][iy] == Tile.NONE) {
                    if (numberOfPoisonTiles > 0 && rn.nextFloat() >= (1.0 - distPoison)) {
//                        System.out.println("Poison was added. Remaining poisons: " + numberOfFoodTiles);
                        tiles[ix][iy] = Tile.Poison;
                        numberOfPoisonTiles--;

                    }
                    else {
                        startX = ix;
                        startY = iy;
                    }
                }
            }
        }


        return tiles;
    }

    /**
     *
     * @param net
     * @param boardGraphics is null if board should not be updated
     * @return
     */
    public float simulateAgent(NeuralNet net, BoardGraphics boardGraphics){
        Scenario scenario = this.makeCopy();
        Agent agent = new Agent();
        if(boardGraphics != null) boardGraphics.updateBoardGraphics(scenario,agent);
        int ticks = 60;

        for (int i = 0; i < ticks; i++) {

            int[] inputValues = scenario.getNetInputNodes(agent.getSensorLocation());

            int outputValue = net.runNeuralNet(inputValues); //Output fra nettet


//            System.out.println("outputValue: " + outputValue);

            switch (outputValue) {
                case 0: agent.goForward(); break;
                case 1: agent.goLeft(); break;
                case 2: agent.goRight(); break;
            }



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
            if(boardGraphics != null){
                MainProgram3.labelTicks.setText("Ticks: " + i + "/" + ticks);
                MainProgram3.label1.setText("Speed:" + slider1.getValue() + " ms");
                MainProgram3.labelFoodScore.setText("Score food: " + agent.getFoodScore());
                MainProgram3.labelPoisonScore.setText("Score poison: " + agent.getPoisonScore());
                MainProgram3.labelScore.setText("Score: " + agent.getScore());
                MainProgram3.tick(slider1.getValue(), boardGraphics);
            }

        }

        return agent.getScore();
    }

}
