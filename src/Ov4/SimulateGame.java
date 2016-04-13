package Ov4;

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

    public  void setNoWrapEnabled(boolean noWrapEnabled) {
        noWrapEnabled = noWrapEnabled;
    }

    public  void setPullEnabled(boolean pullEnabled) {
        pullEnabled = pullEnabled;
    }

    public  boolean noWrapEnabled;

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

    public float simulateAgent(AdvancedNeuralNet net, BoardOv4 boardGraphics){

        int ticks = 500;
        catchedTiles = 0;
        crashedTiles = 0;
        crashedBigTiles = 0;
        avoidedBigTiles = 0;
        missedSmallTiles = 0;
        fallingItem = createFallingItem();

        setNoWrapEnabled(false);
        setPullEnabled(false);

//        System.out.println(net.getGenotype().get);


        for (int i = 0; i < ticks; i++) {
            for (int j = 0; j < 4; j++) {


                int[] inputValues = getSensorOutput();
//                if (boardGraphics != null) System.out.println(Arrays.toString(inputValues));

                int outputValue = net.runNeuralNetTimeStep(inputValues);

                //TESTING
                Random rn = new Random();
//            outputValue = Math.abs(rn.nextInt(2));
//            System.out.println("outputValue: " + outputValue);
                // -
                switch (outputValue) {
                    case 0:
                        catcherObject.moveRight();
                        break;
                    case 1:
                        catcherObject.moveLeft();
                        break;
                    //TODO - Det skal støttte flere handlinger i fremtiden
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

    private static float getFinalScore(){
        //TODO
        return (catchedTiles + 1.75f*avoidedBigTiles + (-1)*crashedTiles + (-1)*missedSmallTiles )/ 60.0f;
    }

    private FallingItem createFallingItem(){
        Random rn = new Random();
        int length = (int) (rn.nextFloat() * 6) + 1;
        int xPos = (int) (rn.nextFloat() * (31 - length));
        return new FallingItem(xPos,length);
    }

    public int[] getSensorOutput(){
        int[] coPoints = getCatcherObject().getBlockPositions();
        int[] foPoints = getFallingItem().getBlockPositions();
        int outputLength = 5;
        if(noWrapEnabled) outputLength = 7;
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
        if(noWrapEnabled) {
            if (getCatcherObject().getLeftBumperSensor()) output[5] = 1;
            else output[5] = 0;

            if (getCatcherObject().getRightBumperSensor()) output[5] = 1;
            else output[6] = 0;
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
