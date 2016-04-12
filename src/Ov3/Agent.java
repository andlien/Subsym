package Ov3;

/**
 * Created by simen on 22.03.2016.
 */
public class Agent {

    Direction dir;
    private int xPos, yPos;
    float foodScore, poisonScore, moveScore;



    public Agent() {
        dir = Direction.UP;
        xPos = 5;
        yPos = 5;
        foodScore = 1;
        poisonScore = 0;
        moveScore = 1;

    }



    public void steppedOnFoodTile(){
        //TODO - Må justeres
        foodScore += 1;//2.5;
//        System.out.println("Ate food tile. Nom nom");
    }

    public void steppedOnPoisonTile(){
        //TODO - Må justeres
        poisonScore +=1;//
//        System.out.println("Ate poison. Ough....!");
    }

    public void steppedOnEmptyTile(){
        //TODO - Må justeres
        moveScore += 0.1;
//        System.out.println("Ate poison. Ough....!");
    }

    public void didNotStepOnFood(){
        //TODO - Må justeres
        moveScore += 0.5;
//        System.out.println("Ate poison. Ough....!");
    }

    public float getScore(){
        //TODO - Må justeres
//        float score = ( 3*foodScore - (poisonScore) - moveScore)/(3*foodScore);
        float score = ( foodScore - (2*poisonScore) - moveScore)/(foodScore);
//        float score = 10 + foodScore - poisonScore - moveScore;
        return Math.max(score,0.0f);
    }

    public float getScore(int[] tiles){
        float bestScore = tiles[0];
        float worstScore = tiles[1];
        float score = ( foodScore - (poisonScore) - moveScore)/(bestScore);
        return Math.max(score,0.0f);
    }









    public int getxPos() {
        return Math.floorMod(xPos, 10);
    }

    public int getyPos() {
        return Math.floorMod(yPos, 10);
    }

    public float getPoisonScore() {
        return poisonScore;
    }

    public float getFoodScore() {
        return foodScore;
    }



    public int[] getSensorLocation(){
        int[] sensorLocation = new int[6];
        int[] posDirs = dir.getPosAsList();
        for (int i = 0; i < 6; i+=2) {
            int posX = getxPos() + posDirs[i];
            posX = Math.floorMod(posX, 10);
            sensorLocation[i] = posX;
            int posY = getyPos() + posDirs[i+1];
            posY = Math.floorMod(posY, 10);
            sensorLocation[i+1] = posY;

        }

//        //Left
//        sensorLocation[0] = getxPos() + posDirs[0];
//        sensorLocation[1] = getyPos() + posDirs[1];
//
//        sensorLocation[2] = getxPos() + posDirs[2];
//        sensorLocation[3] = getyPos() + posDirs[3];
//
//        sensorLocation[4] = getxPos() + posDirs[4];
//        sensorLocation[5] = getyPos() + posDirs[5];

        return sensorLocation;
    }

    public void goForward(){
        if(dir == Direction.UP) yPos -= 1;
        else if(dir == Direction.DOWN) yPos += 1;
        else if(dir == Direction.LEFT) xPos += -1;
        else if(dir == Direction.RIGHT) xPos += 1;

//        System.out.println("Moving forward!!");
    }

    public void goLeft(){
        if(dir == Direction.UP) dir = Direction.LEFT;
        else if(dir == Direction.DOWN) dir = Direction.RIGHT;
        else if(dir == Direction.LEFT) dir = Direction.DOWN;
        else if(dir == Direction.RIGHT) dir = Direction.UP;

        goForward();
    }

    public void goRight(){
        if(dir == Direction.UP) dir = Direction.RIGHT;
        else if(dir == Direction.DOWN) dir = Direction.LEFT;
        else if(dir == Direction.LEFT) dir = Direction.UP;
        else if(dir == Direction.RIGHT) dir = Direction.DOWN;

        goForward();
    }
}
