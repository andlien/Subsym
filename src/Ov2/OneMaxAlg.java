package Ov2;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by Anders on 22.02.2016.
 */
public class OneMaxAlg extends EvolutionaryAlg {

    public static final int MAX_POPULATION_SIZE = 15;

    public OneMaxAlg(Class<? extends Individual> individualClass) {
        super(individualClass);
    }

    @Override
    protected void assignFitness() {
        population.forEach(individual -> individual.fitness = Collections.frequency(individual.phenotype, 1));
    }

    @Override
    protected void selectAdults() {
        overProduction();
    }

    @Override
    protected void selectParentsAndReproduce() {
        sigmaScalingParentSelection(30);
    }

    @Override
    public Individual findSolution() {
        Optional<Individual> stream = population.stream().filter(individual -> individual.fitness == Individual.GENOTYPE_BIT_SIZE).findAny();

        return stream.isPresent()? stream.get() : null;
    }
}
