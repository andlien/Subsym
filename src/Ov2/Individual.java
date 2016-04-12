package Ov2;

import java.util.*;

/**
 * Created by Anders on 22.02.2016.
 */
public class Individual implements Comparable<Individual> {

    public static int GENOTYPE_BIT_SIZE;
    public static double CROSSOVER_RATE;
    public static double MUTATION_RATE;
//    public static double CROSSOVER_RATE;
//    public static double MUTATION_RATE;

    protected BitSet genotype;
    protected ArrayList<Integer> phenotype;
    protected double fitness;
    protected boolean isAdult;

    public BitSet getGenotype() {
        return genotype;
    }

    public Individual() {
        genotype = initializeRandomBitstring(new Random());
        isAdult = false;
    }
    public Individual(int GENOTYPE_BIT_SIZE) {
        Individual.GENOTYPE_BIT_SIZE = GENOTYPE_BIT_SIZE;
        genotype = initializeRandomBitstring(new Random());
        isAdult = false;
    }

    public Individual(BitSet genotype) {
        this.genotype = genotype;
        isAdult = false;
    }

    public static BitSet initializeRandomBitstring(Random random) {
        BitSet initString = new BitSet(GENOTYPE_BIT_SIZE);

        for (int i = 0; i < GENOTYPE_BIT_SIZE; i++) {
            if (random.nextBoolean()) {
                initString.set(i);
            }
        }

        return initString;
    }

    public void growBitPhenotype() {
        if (phenotype != null) {
            return;
        }

        phenotype = new ArrayList<>();
        for (int i = 0; i < GENOTYPE_BIT_SIZE; i++) {
            phenotype.add(genotype.get(i) ? 1 : 0);
        }
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Individual && genotype.equals(((Individual) obj).genotype);
    }

    @Override
    public int hashCode() {
        return genotype.hashCode();
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void mature() {
        isAdult = true;
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.fitness, o.fitness);
    }


    public Individual reproduce(Random random, Individual parent2) {

        Individual child = new Individual((BitSet) this.genotype.clone());
        if (random.nextDouble() < CROSSOVER_RATE) {
            child.crossover(random, (BitSet) parent2.genotype.clone());
        }
        if (random.nextDouble() < MUTATION_RATE) {
            child.mutate(random);
        }

        return child;
    }

    public void crossover(Random random, BitSet genocopy2) {
        int point = random.nextInt(GENOTYPE_BIT_SIZE);

        if (point < genotype.size()) {
            genotype.clear(point, genotype.size());
        }
        genocopy2.clear(0, point);
        genotype.or(genocopy2);
    }

    public void mutate(Random random) {
        for (int i = 0; i < 1 + random.nextInt(GENOTYPE_BIT_SIZE / 10); i++) {
            genotype.flip(random.nextInt(GENOTYPE_BIT_SIZE));
        }
    }
}
