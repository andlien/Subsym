package Ov3;

/**
 * Created by simen on 22.03.2016.
 */
public class Agent {

    Direction dir;
    private int xPos, yPos;
    float foodScore, poisonScore, moveScore;



    public Agent() {
        dir = Direction.LEFT;
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
        moveScore += 0.2;
//        System.out.println("Ate poison. Ough....!");
    }

    public float getScore(){
        //TODO - Må justeres
        return (1 + 2*foodScore - (poisonScore) - moveScore)/(foodScore*2);
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
