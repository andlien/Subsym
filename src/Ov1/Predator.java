package Ov1;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anders on 27.01.2016.
 */
public class Predator extends Entity {

    public static final int ground = 30;
    public static final int height = 60;

    public static final double eatRadius = 45;
    public static final double comfortRadius = 80;

    public Predator(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;

        this.speedX = 4.0;
        this.speedY = 4.0;

        MAX_SPEED = 12;
    }

    /**
     * Predators are triangles with height 60px and 30px ground
     * Center of rotation is 15px above ground
     *
     * @return
     */
    @Override
    public Shape getIcon() {
        int[] xpoints = {xpos - ground / 2, xpos + ground / 2, xpos};
        int[] ypoints = {ypos + 15, ypos + 15, ypos + 15 - height};

        double theta = oldDir;
        if (speedX != 0.0 || speedY != 0.0) {
            theta = Math.atan2(speedY, speedX) + Math.toRadians(90);
        }
        int[][] points = MainProgram.getPointsRotatedAround(xpoints, ypoints, xpos, ypos, theta);
        return new Polygon(points[0], points[1], 3);
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * chase the closest boid
     * @param others
     */
    @Override
    public void updateEntity(ArrayList<Entity> others) {

        Boid closestBoid = getClosestBoid(others);

        if (closestBoid != null) {
            setSpeed(speedX + getVectorXComponent(closestBoid.getXpos()), speedY + getVectorYComponent(closestBoid.getYpos()));
        }

        eatBoids(others);
        avoidOtherPredators(others);
        scaleSpeed();

        move();

    }

    public void scaleSpeed() {
        double factor = 1.0;
        double absSpeed = getAbsSpeed();

        if (absSpeed != 0.0) {
            factor = MAX_SPEED / absSpeed;
        }

        this.speedX = Math.round(factor * speedX);
        this.speedY = Math.round(factor * speedY);
    }

    @Nullable
    private Boid getClosestBoid(ArrayList<Entity> others) {
        ArrayList<Boid> boids;
        double searchDist = 0;
        double maxSearch = Math.sqrt(Math.pow(MainProgram.board.getWidth(), 2) + Math.pow(MainProgram.board.getHeight(), 2));

        while (true) {
            boids = getNearbyBoids(searchDist, others);

            if (boids.size() == 0) {
                searchDist += 10;
            } else {
                return boids.get(0);
            }

            if (searchDist > maxSearch) {
                return null;
            }
        }
    }

    public void eatBoids(ArrayList<Entity> others) {
        ArrayList<Boid> boidsThatAreSoooDead = getNearbyBoids(eatRadius, others);

        for (Boid b : boidsThatAreSoooDead) {
            b.dead = true;
        }
    }

    public void avoidOtherPredators(ArrayList<Entity> others) {
        ArrayList<Predator> otherPredators = getNearbyPredators(comfortRadius, others);

        if (otherPredators.isEmpty()) {
            return;
        }

        double deltaX = 0;
        double deltaY = 0;

        for (Entity neighbour : otherPredators) {
            double dist = getDistance(neighbour);

            deltaX -= getVectorXComponent(neighbour.getXpos()) * comfortRadius / Math.max(dist, 0.1);
            deltaY -= getVectorYComponent(neighbour.getYpos()) * comfortRadius / Math.max(dist, 0.1);
        }

        setSpeed(speedX + deltaX, speedY + deltaY);
    }
}
