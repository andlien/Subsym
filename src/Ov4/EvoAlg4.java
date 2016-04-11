package Ov4;

import Ov2.EvolutionaryAlg;
import Ov2.Individual;


public class EvoAlg4 extends EvolutionaryAlg {

    private BoardOv4 bg;

    public EvoAlg4(BoardOv4 bg) {
//        super(AdvancedNeuralNet.class, 20000, 100,1000);
        super(AdvancedNeuralNet.class, 20000, 100);
        this.bg = bg;
    }


    @Override
    public void runNextGeneration() {
        super.runNextGeneration();

//        scenario.simulateAgent((NeuralNet) currentBest, bg);
    }

    public void runBestWithGraphics(){
        SimulateGame.simulateAgent((AdvancedNeuralNet) currentBest, bg);
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

        population.forEach(individual -> individual.setFitness(SimulateGame.simulateAgent((AdvancedNeuralNet) individual, null)));
        population.forEach(Individual::mature);
    }

    @Override
    protected void developPhenotypes() {
        population.forEach(Individual::growBitPhenotype);
    }
}
