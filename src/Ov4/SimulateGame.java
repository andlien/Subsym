package Ov4;

import Ov1.MainProgram;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by simen on 11.04.2016.
 */
public class SimulateGame {

    private FallingItem fallingItem;
    private CatcherObject catcherObject;

    private static float catchedTiles = 0;
    private static float crashedTiles = 0;
    private static float crashedBigTiles = 0;
    private static float avoidedBigTiles = 0;
    private static float missedSmallTiles = 0;

    public  boolean pullEnabled;
    public boolean noWrapEnabled;
    private int staticPositionsIndex;

    public FallingItem getFallingItem() {
        return fallingItem;
    }

    public CatcherObject getCatcherObject() {
        return catcherObject;
    }

    public SimulateGame() {
        fallingItem = createFallingItem();
        catcherObject = new CatcherObject();
    }

    public SimulateGame(int[] staticPositions) {
        fallingItem = createFallingItem();
        catcherObject = new CatcherObject();
    }




    public float simulateAgent(AdvancedNeuralNet net, BoardOv4 boardGraphics){

        int ticks = 500;
        catchedTiles = 0;
        crashedTiles = 0;
        crashedBigTiles = 0;
        avoidedBigTiles = 0;
        missedSmallTiles = 0;
        staticPositionsIndex = 0;
        fallingItem = createFallingItem();

        noWrapEnabled = true;
        pullEnabled = false;

        catcherObject.setNoWrap(noWrapEnabled);

//        System.out.println(net.getGenotype().get);


        for (int i = 0; i < ticks; i++) {
            for (int j = 0; j < 4; j++) {


                int[] inputValues = getSensorOutput();
                if (boardGraphics != null) {
                    System.out.println(Arrays.toString(inputValues));
                }
                int outputValue = net.runNeuralNetTimeStep(inputValues);


                //TESTING
                Random rn = new Random();
//            outputValue = Math.abs(rn.nextInt(2));
//            System.out.println("outputValue: " + outputValue);
                // -
                switch (outputValue) {
                    case -1:
                        throw new RuntimeException();
                    case 0:
                        catcherObject.moveRight();
                        break;
                    case 1:
                        catcherObject.moveLeft();
                        break;
                    case 2:
                        fallingItem.pull();
                        break;

                }
                if(boardGraphics != null)  MainProgram4.tick(MainProgram4.slider1.getValue()/2, boardGraphics);
            }


            boolean hasSpaceLeftToFall = fallingItem.fallOneBlockDown();
            if(!hasSpaceLeftToFall){
                boolean tileReachedFloor = interactWithFallingObject();
                if(boardGraphics != null){
                    MainProgram4.tick(MainProgram4.slider1.getValue(), boardGraphics);
                    if(tileReachedFloor) fallingItem.fallOneBlockDown();
                    MainProgram4.tick(MainProgram4.slider1.getValue(), boardGraphics);
                }
                fallingItem = createFallingItem();


            }


            if(boardGraphics != null){
                MainProgram4.labelTicks.setText("Ticks: " + (1+i) + "/" + ticks);
                MainProgram4.label1.setText("Speed:" + MainProgram4.slider1.getValue() + " ms");
                MainProgram4.label2.setText("Catched Tiles: " + catchedTiles);
                MainProgram4.label3.setText("Crashed Tiles: " + crashedTiles);
                MainProgram4.label4.setText("Avoided Big Tiles: " + avoidedBigTiles);
                MainProgram4.label5.setText("Missed Small Tiles: " + missedSmallTiles);
                MainProgram4.labelScore.setText("Score: " + getFinalScore());
                MainProgram4.tick(MainProgram4.slider1.getValue(), boardGraphics);
            }

        }

        return Math.max(0,getFinalScore());
    }

    private float getFinalScore(){

        if (!noWrapEnabled && !pullEnabled)
            return (catchedTiles + 2*avoidedBigTiles + (-1)*crashedTiles + (-1)*missedSmallTiles );
        else if (pullEnabled)
            return (catchedTiles + 1.2f*avoidedBigTiles + (-1.6f)*crashedTiles + (-1)*missedSmallTiles );
        else
            return Math.max(catchedTiles/(5) ,(catchedTiles + 1.6f*avoidedBigTiles + (-1.3f)*crashedTiles + (-1)*missedSmallTiles ));
    }

    private FallingItem createFallingItem(){
        Random rn = new Random();
        int length = (int) (rn.nextFloat() * 6) + 1;
        int xPos = (int) (rn.nextFloat() * (31 - length));
        if(MainProgram4.staticPositions.length > 0){
            xPos = MainProgram4.staticPositions[staticPositionsIndex];
            length = MainProgram4.staticLengths[staticPositionsIndex];
//            System.out.println("STATIC: " + staticPositionsIndex);
            staticPositionsIndex ++;
        }
        return new FallingItem(xPos,length);
    }

    public int[] getSensorOutput(){
        int[] coPoints = getCatcherObject().getBlockPositions();
        int[] foPoints = getFallingItem().getBlockPositions();
        int outputLength = 5;
        if(noWrapEnabled || true) outputLength = 7;
        int[] output = new int[outputLength];
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
        if(noWrapEnabled|| true) {
            if (getCatcherObject().getLeftBumperSensor()){
                output[6] = 1;
            }
            else output[6] = 0;

            if (getCatcherObject().getRightBumperSensor()) output[5] = 1;
            else output[5] = 0;
        }


        return output;
    }

    private static int getNumberOfOnes(int[] collisionPoints){
        int ones = 0;
        for (int i = 0; i < collisionPoints.length; i++) {
            if(collisionPoints[i] == 1) ones ++;
        }

        return ones;
    }

    private boolean interactWithFallingObject(){
        int[] collisionPoints = getSensorOutput();
        int numberOfOnes = getNumberOfOnes(collisionPoints);
        if(getFallingItem().isTileBig()){
            if(numberOfOnes > 0){
                crashedTiles ++;
                return false;
            }
            else{
                avoidedBigTiles ++;
                return true;
            }
        }
        else{
            if(numberOfOnes == 0){
                missedSmallTiles ++;
                return true;
            }
            else if(numberOfOnes >= getFallingItem().getLength()){
                catchedTiles ++;
                return false;
            }
            else {
                crashedTiles ++;
                return false;
            }
        }
    }
}
