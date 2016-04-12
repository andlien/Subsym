package Ov4;

import Ov2.EvolutionaryAlg;
import Ov2.Individual;


public class EvoAlg4 extends EvolutionaryAlg {

    public EvoAlg4() {
        super(AdvancedNeuralNet.class, 100, 50);
    }

    public void runBestWithGraphics(){
        SimulateGame game = new SimulateGame();
        game.simulateAgent((AdvancedNeuralNet) currentBest, MainProgram4.createBoardGraphics(game));
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

        population.forEach(individual -> individual.setFitness(new SimulateGame().simulateAgent((AdvancedNeuralNet) individual, null)));
        population.forEach(Individual::mature);
    }

    @Override
    protected void developPhenotypes() {
        population.forEach(Individual::growBitPhenotype);
    }
}
