package Ov3;

import Ov2.EvolutionaryAlg;
import Ov2.Individual;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Anders on 26.03.2016.
 */
public class Evo_alg extends EvolutionaryAlg {

    private BoardGraphics bg;
    private Scenario scenario;

    public Evo_alg(BoardGraphics bg, Scenario scenario) {
        super(NeuralNet.class, 20000, 40);
        this.bg = bg;
        this.scenario = scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void runNextGeneration() {
        super.runNextGeneration();

//        scenario.simulateAgent((NeuralNet) currentBest, bg);
    }

    public void runBestWithGraphics(){
        scenario.simulateAgent((NeuralNet) currentBest, bg);
    }

    @Override
    protected void selectParentsAndReproduce() {
        sigmaScalingParentSelection(numOfChildren);
    }

    @Override
    protected void selectAdults() {
        battle();
    }

    @Override
    public Individual getCurrentBestFitIndividual() {
        return population.stream().max(Individual::compareTo).get();
    }

    @Override
    public Individual findSolution() {
        return null;
    }

    @Override
    protected void assignFitness() {

        population.forEach(individual -> individual.setFitness(scenario.simulateAgent((NeuralNet) individual, null)));
        population.forEach(Individual::mature);
    }

    @Override
    protected void developPhenotypes() {
        population.forEach(Individual::growBitPhenotype);
    }
}
