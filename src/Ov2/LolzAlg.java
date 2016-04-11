package Ov2;

import java.util.Random;

/**
 * Created by Anders on 23.02.2016.
 */
public class LolzAlg extends EvolutionaryAlg {

    public static int z = 4;

    public LolzAlg() {
        super(Individual.class,0,0);
    }

    @Override
    protected void assignFitness() {
        population.forEach(individual -> individual.fitness = getFitness(individual));
    }

    private static double getFitness(Individual i) {
        return i.genotype.get(0)? i.genotype.nextClearBit(0) : Math.min(i.genotype.nextSetBit(0) == -1? Individual.GENOTYPE_BIT_SIZE : i.genotype.nextSetBit(0), z);
    }

//    @Override
//    protected void selectAdults() {
//        battle();
//    }
//
//    @Override
//    protected void selectParentsAndReproduce() {
//        sigmaScalingParentSelection(10);
//    }

    @Override
    public Individual getCurrentBestFitIndividual() {
        return population.stream().max(Individual::compareTo).get();
    }

    @Override
    public Individual findSolution() {
        return population.stream().filter(individual -> individual.isAdult() && individual.fitness == Individual.GENOTYPE_BIT_SIZE).findAny().orElse(null);
    }
}
