package Ov1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anders on 27.01.2016.
 */
public class Boid extends Entity {

    public static final int ground = 10;
    public static final int height = 20;
    public static final double comfortRadius = 20;
    public static final double scareRadius = 100;

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
     * Center of rotation is 20px above ground
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
        int[][] points = MainProgram.getPointsRotatedAround(xpoints, ypoints, xpos, ypos, theta);
        return new Polygon(points[0], points[1], 3);
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public void updateEntity(ArrayList<Entity> allObjects) {
        ArrayList<Boid> neighbours = getNearbyBoids(80, allObjects);
        ArrayList<Obstacle> obstacles = getNearbyObstacles(200, allObjects);
        ArrayList<Predator> predators = getNearbyPredators(scareRadius, allObjects);

        executeSeparation(neighbours, (double) MainProgram.cp.getSeparationSlider().getValue() / 100);
        executeAlignment(neighbours, (double) MainProgram.cp.getAlignmentSlider().getValue() / 100);
        executeCohesion(neighbours, (double) MainProgram.cp.getCohesionSlider().getValue() / 100);

        avoidPredators(predators);
        avoidObstacles(obstacles);

        scaleSpeed();

        move();
    }

    public void executeSeparation(ArrayList<Boid> neighbours, double separationLevel) {

        if (neighbours.isEmpty() || separationLevel == 0.0) {
            return;
        }

        double deltaX = 0;
        double deltaY = 0;

        for (Entity neighbour : neighbours) {
            double dist = getDistance(neighbour);

            deltaX -= getVectorXComponent(neighbour.getXpos()) * comfortRadius / Math.max(dist, 0.1);
            deltaY -= getVectorYComponent(neighbour.getYpos()) * comfortRadius / Math.max(dist, 0.1);
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

        setSpeed(speedX + (x-xpos) * cohesionLevel, speedY + (y-ypos) * cohesionLevel);
    }

    /**
     * react to obstacles only if it's within 400
     * @param obstacles
     */
    public void avoidObstacles(ArrayList<Obstacle> obstacles) {
        for (Obstacle o : obstacles) {
            int[][] intersections = o.getIntersectionByBoidDirection(this);

            if (intersections.length == 0) {
                return;
            } else if (intersections.length == 1) {
                double deltaX = intersections[0][0] - o.getXpos();
                double deltaY = intersections[0][1] - o.getYpos();

                setSpeed(speedX + deltaX, speedY + deltaY);
            } else {

                double x = (intersections[1][0] + intersections[0][0]) / 2.0;
                double y = (intersections[0][1] + intersections[1][1]) / 2.0;
                double dist = Math.cbrt(Math.pow(xpos - x, 2) + Math.pow(ypos - y, 2)) / 2;

                setSpeed(speedX + (x - o.xpos) * 30 / dist, speedY + (y - o.ypos) * 30 / dist);
            }

        }
    }

    public void avoidPredators(ArrayList<Predator> predators) {
        if (predators.isEmpty()) {
            return;
        }

        double deltaX = 0;
        double deltaY = 0;

        for (Entity neighbour : predators) {
            double dist = getDistance(neighbour);

            deltaX -= getVectorXComponent(neighbour.getXpos()) * scareRadius / Math.max(dist, 0.5);
            deltaY -= getVectorYComponent(neighbour.getYpos()) * scareRadius / Math.max(dist, 0.5);

        }

        setSpeed(speedX + deltaX, speedY + deltaY);
    }

}
