package Ov3;

import Ov2.EvolutionaryAlg;
import Ov2.Individual;


public class Evo_alg extends EvolutionaryAlg {

    private BoardGraphics bg;
    private Scenario scenario;
    private Scenario[] scenarios;

    public Evo_alg(BoardGraphics bg, Scenario[] scenarios) {
        super(NeuralNet.class, 400, 200);
        this.bg = bg;
        this.scenarios = scenarios;
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
        for (int i = 0; i < scenarios.length; i++) {
            scenarios[i].simulateAgent((NeuralNet) currentBest, bg);
        }
    }

    @Override
    protected void selectParentsAndReproduce() {
        sigmaScalingParentSelection(numOfChildren);
    }

    @Override
    protected void selectAdults() {
        battle();
//        overProduction();
//        fullGenerationReplacement();
    }

    @Override
    public Individual getCurrentBestFitIndividual() {
        return population.stream().max(Individual::compareTo).get();
    }

    @Override
    public Individual findSolution() {
        return null;
    }

    public void setScenarios(Scenario[] scenarios) {
        this.scenarios = scenarios;
    }

    public float getFitnessOverMultipleRuns(Individual individual){
        float sum = 0;
        for (int i = 0; i < scenarios.length; i++) {
            sum += scenarios[i].simulateAgent((NeuralNet) individual, null);

        }

        return sum / (float) scenarios.length;
    }

    @Override
    protected void assignFitness() {

        population.forEach(individual -> individual.setFitness(getFitnessOverMultipleRuns(individual)));
//        population.forEach(Individual::mature);
    }

    @Override
    protected void developPhenotypes() {
        population.forEach(Individual::growBitPhenotype);
    }
}
