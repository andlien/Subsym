package Ov1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by Anders on 27.01.2016.
 */
public class Boid extends Entity {

    public static final int ground = 20;
    public static final int height = 40;
    public static final int comfortRadius = 30;

    public Boid(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;

        this.speedX = 4.0;
        this.speedY = 4.0;
        this.oldDir = Math.atan2(speedY, speedX);

        MAX_SPEED = 10;
    }

    public Boid(Random random) {
        this.xpos = random.nextInt(MainProgram.board.getWidth());
        this.ypos = random.nextInt(MainProgram.board.getHeight());

        this.speedX = (random.nextDouble() - 0.5) * 10.0;
        this.speedY = (random.nextDouble() - 0.5) * 10.0;
        this.oldDir = Math.atan2(speedY, speedX);

        this.MAX_SPEED = 10;
    }

    /**
     * Boids are triangles with height 40px and 20px ground
     * Center of rotation is 10px above ground
     *
     * @return
     */
    @Override
    public Shape getIcon() {
        int[] xpoints = {xpos - ground / 2, xpos + ground / 2, xpos};
        int[] ypoints = {ypos + 10, ypos + 10, ypos + 10 - height};

        double theta = oldDir;
        if (speedX != 0.0 || speedY != 0.0) {
            theta = Math.atan2(speedY, speedX) + Math.toRadians(90);
        }
        return getRotatedPolygon(new Polygon(xpoints, ypoints, 3), theta);
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public void updateEntity(ArrayList<Entity> allObjects) {
        ArrayList<Boid> neighbours = getNeighbours(100, allObjects);

        executeSeparation(neighbours, (double) MainProgram.cp.getSeparationSlider().getValue() / 100);
        executeAlignment(neighbours, (double) MainProgram.cp.getAlignmentSlider().getValue() / 100);
        executeCohesion(neighbours, (double) MainProgram.cp.getCohesionSlider().getValue() / 100);

        move();
    }



    private ArrayList<Boid> getNeighbours(double range, ArrayList<Entity> objects) {
        return objects.stream().filter(possNeighbour -> !this.equals(possNeighbour) &&
                    this.getDistance(possNeighbour) <= range &&
                    possNeighbour instanceof Boid
        ).map(possNeighbour -> (Boid) possNeighbour).collect(Collectors.toCollection(ArrayList::new));
    }

    public void executeSeparation(ArrayList<Boid> neighbours, double separationLevel) {

        if (neighbours.isEmpty() || separationLevel == 0.0) {
            return;
        }

        double deltaX = 0;
        double deltaY = 0;

        for (Entity neighbour : neighbours) {
            double dist = getDistance(neighbour);

            deltaX -= (neighbour.getXpos() - xpos) * comfortRadius / Math.max(dist, 0.1);
            deltaY -= (neighbour.getYpos() - ypos) * comfortRadius / Math.max(dist, 0.1);

        }

        setSpeed(speedX + deltaX * separationLevel, speedY + deltaY * separationLevel);


    }
    public void executeAlignment(ArrayList<Boid> neighbours, double alignmentLevel) {

        if (neighbours.isEmpty() || alignmentLevel == 0.0) {
            return;
        }

        double speedXes = 0;
        double speedYes = 0;

        for (Entity neighbour : neighbours) {
            speedXes += neighbour.speedX;
            speedYes += neighbour.speedY;
        }

        double theta = Math.atan2(speedYes, speedXes);
        double absSpeed = getAbsSpeed();

        setSpeed(speedX + (absSpeed * Math.cos(theta) - speedX) * alignmentLevel,
                speedY + (absSpeed * Math.sin(theta) - speedY) * alignmentLevel);

    }
    public void executeCohesion(ArrayList<Boid> neighbours, double cohesionLevel) {

        if (neighbours.isEmpty() || cohesionLevel == 0.0) {
            return;
        }

        int x = 0;
        int y = 0;

        for (Boid b : neighbours) {
            x += b.getXpos();
            y += b.getYpos();
        }

        x /= neighbours.size();
        y /= neighbours.size();

        setSpeed(speedX + (x - xpos) * cohesionLevel, speedY + (y - ypos) * cohesionLevel);
    }

}
