package Ov2;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Anders on 22.02.2016.
 */
public class OneMaxAlg extends EvolutionaryAlg {

    public OneMaxAlg() {
        super(Individual.class);
    }

    @Override
    protected void assignFitness() {
        population.forEach(individual -> individual.fitness = Collections.frequency(individual.phenotype, 1));
    }

//    @Override
//    protected void selectAdults() {
//        fullGenerationReplacement();
//    }
//
//    @Override
//    protected void selectParentsAndReproduce() {
//        fitnessProportionaleParentSelection(MAX_POPULATION_SIZE);
//    }

    @Override
    public Individual getCurrentBestFitIndividual() {
        return population.stream().max(Individual::compareTo).get();
    }

    @Override
    public Individual findSolution() {
        Optional<Individual> stream = population.stream().filter(individual -> individual.isAdult() && individual.fitness == Individual.GENOTYPE_BIT_SIZE).findAny();

        return stream.isPresent()? stream.get() : null;
    }
}
