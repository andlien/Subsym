package Ov2;

import java.util.BitSet;
import java.util.Random;

/**
 * Created by Anders on 25.02.2016.
 */
public class OneMaxRandomBitstring extends OneMaxAlg {

    BitSet target = Individual.initializeRandomBitstring(new Random());

    @Override
    protected void assignFitness() {
        for (Individual i : population) {
            BitSet copy = (BitSet) i.genotype.clone();
            copy.xor(target);
            copy.flip(0, target.size());
            i.fitness = copy.cardinality();
        }
    }

    @Override
    public Individual findSolution() {
        return population.stream().filter(individual -> individual.genotype.equals(target)).findAny().orElse(null);
    }
}
