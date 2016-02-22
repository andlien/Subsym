package Ov2;

import java.util.*;

/**
 * Created by Anders on 22.02.2016.
 */
public class Individual implements Comparable<Individual> {

    public static final int GENOTYPE_BIT_SIZE = 20;

    protected BitSet genotype;
    protected ArrayList<Integer> phenotype;
    protected int fitness;
    protected boolean isAdult;

    public Individual(Random random) {
        genotype = initializeRandomBitstring(random);
        isAdult = false;
    }

    public Individual(BitSet genotype) {
        this.genotype = genotype;
    }

    private BitSet initializeRandomBitstring(Random random) {
        BitSet initString = new BitSet(GENOTYPE_BIT_SIZE);

        for (int i = 0; i < GENOTYPE_BIT_SIZE; i++) {
            if (random.nextBoolean()) {
                initString.set(i);
            }
        }

        return initString;
    }

    public void growPhenotype() {
        phenotype = new ArrayList<>();
        for (int i = 0; i < GENOTYPE_BIT_SIZE; i++) {
            phenotype.add(genotype.get(i) ? 1 : 0);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Individual && genotype.equals(((Individual) obj).genotype);
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void mature() {
        isAdult = true;
    }

    @Override
    public int compareTo(Individual o) {
        return o.fitness - this.fitness;
    }

    public Individual reproduce(Random random) {
        Individual kid = new Individual((BitSet) genotype.clone());
        kid.genotype.flip(random.nextInt(GENOTYPE_BIT_SIZE));
        return kid;
    }
}
